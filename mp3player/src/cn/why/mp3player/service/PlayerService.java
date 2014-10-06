package cn.why.mp3player.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Queue;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import cn.why.lrc.LrcProcessor;
import cn.why.model.Mp3Info;
import cn.why.mp3player.AppConstant;
import cn.why.mp3player.AppConstant.PlayerMsg;

public class PlayerService extends Service {

	private boolean isPlaying = false;
	private boolean isPause = false;
	private boolean isReleased = false;
	private MediaPlayer mediaPlayer = null;
	private Handler handler = new Handler();
	private UpdateTimeCallback updateTimeCallback = null;
	private long begin = 0;
	private long nextTimeMill = 0;
	private long currentTimeMill = 0;
	private String message = null;
	private long pauseTimeMills = 0;
	private ArrayList<Queue> queues = null;
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Mp3Info mp3Info = (Mp3Info) intent.getSerializableExtra("mp3Info");
		int MSG = intent.getIntExtra("MSG", 0);
		if (mp3Info != null) {
			if(MSG == AppConstant.PlayerMsg.PLAY_MSG){
				play(mp3Info);
			}
		} else {
			if(MSG == AppConstant.PlayerMsg.PAUSE_MSG){
				pause();
			}
			else if(MSG == AppConstant.PlayerMsg.STOP_MSG){
				stop();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	private void play(Mp3Info mp3Info) {
            if(!isPlaying){
				String path = getMp3Path(mp3Info);
				// �����ּ���MediaPlayer
				mediaPlayer = MediaPlayer.create(this, Uri.parse("file://" + path));
				// ���ò�����ѭ��
				mediaPlayer.setLooping(false);
				// ��ȡ��ʵ�������
				System.out.println(mp3Info.getLrcName());
				prepareLrc(mp3Info.getLrcName());
				// ��ʼ����
				mediaPlayer.start();
				handler.postDelayed(updateTimeCallback, 5);
				begin = System.currentTimeMillis();
				isPlaying = true;
				isReleased = false;
            }
	}

	private void pause() {
		if(isPlaying){
			mediaPlayer.pause();
			handler.removeCallbacks(updateTimeCallback);
			pauseTimeMills=System.currentTimeMillis();
		}else {
			mediaPlayer.start();
			handler.postDelayed(updateTimeCallback,5);
			begin=System.currentTimeMillis()-pauseTimeMills+begin;
		}
		isPlaying=isPlaying?false:true;
	}

	private void stop() {
		if (mediaPlayer != null) {
			if (isPlaying) {
				if (!isReleased) {
					handler.removeCallbacks(updateTimeCallback);
					mediaPlayer.stop();
					mediaPlayer.release();
					isReleased = true;
				}
				isPlaying = false;
			}
		}
	}

	private String getMp3Path(Mp3Info mp3Info) {
		String SDCardRoot = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		String path = SDCardRoot + File.separator + "mp3" + File.separator
				+ mp3Info.getMp3Name();
		return path;
	}
	/**
	 * ���ݸ���ļ������֣�����ȡ����ļ����е���Ϣ
	 * @param lrcName
	 */
	private void prepareLrc(String lrcName){
	try {
		InputStream inputStream = new FileInputStream(Environment.getExternalStorageDirectory().getAbsoluteFile() +File.separator + "mp3/" + lrcName);
		LrcProcessor lrcProcessor = new LrcProcessor();
		queues = lrcProcessor.process(inputStream);
		//����һ��UpdateTimeCallback����
		updateTimeCallback = new UpdateTimeCallback(queues);
		begin = 0 ;
		currentTimeMill = 0 ;
		nextTimeMill = 0 ;
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
}

	class UpdateTimeCallback implements Runnable {
		Queue times = null;
		Queue messages = null;

		public UpdateTimeCallback(ArrayList<Queue> queues) {
			// ��ArrayList����ȡ����Ӧ�Ķ������
			times = queues.get(0);
			messages = queues.get(1);
		}

		public void run() {
			// ����ƫ������Ҳ����˵�ӿ�ʼ����MP3������Ϊֹ���������˶���ʱ�䣬�Ժ���Ϊ��λ
			long offset = System.currentTimeMillis() - begin;
			if (currentTimeMill == 0) {
				// ���ʱ�����
				nextTimeMill = (Long) times.poll();
				// ������ݶ���
				message = (String) messages.poll();
			}
			if (offset >= nextTimeMill) {
				Intent intent = new Intent();
				intent.setAction(AppConstant.LRC_MESSAGE_ACTION);
				intent.putExtra("lrcMessage", message);
				// ���͹㲥
				sendBroadcast(intent);
				nextTimeMill = (Long) times.poll();
				message = (String) messages.poll();
			}
			currentTimeMill = currentTimeMill + 10;
			handler.postDelayed(updateTimeCallback, 10);
		}
	}
}
