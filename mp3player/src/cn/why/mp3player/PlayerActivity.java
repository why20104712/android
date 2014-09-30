package cn.why.mp3player;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.why.model.Mp3Info;
import cn.why.mp3player.service.PlayerService;

public class PlayerActivity extends Activity {
	ImageButton beginButton = null;
	ImageButton pauseButton = null;
	ImageButton stopButton = null;
	MediaPlayer mediaPlayer = null;

	private TextView lrcTextView = null;
	private Mp3Info mp3Info = null;
	private boolean isPlaying = false;
	private IntentFilter intentFilter=null;
	private BroadcastReceiver receiver=null;
	
	protected void onPause() {
		super.onPause();
		unregisterReceiver(receiver);
	}

	protected void onResume() {
		super.onResume();
		receiver=new LrcMessageBroadcastReceiver();
		registerReceiver(receiver,getIntentFilter());
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.player);
		Intent intent = getIntent();
		mp3Info = (Mp3Info) intent.getSerializableExtra("mp3Info");
		beginButton = (ImageButton) findViewById(R.id.begin);
		pauseButton = (ImageButton) findViewById(R.id.pause);
		stopButton = (ImageButton) findViewById(R.id.stop);
		beginButton.setOnClickListener(new BeginButtonListener());
		pauseButton.setOnClickListener(new PauseButtonListener());
		stopButton.setOnClickListener(new StopButtonListener());
		lrcTextView = (TextView)findViewById(R.id.lrcText);
	}


	class BeginButtonListener implements OnClickListener {
		public void onClick(View v) {
			//����һ��Intent��������ͬʱService��ʼ����MP3
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra("mp3Info", mp3Info);
			intent.putExtra("MSG", AppConstant.PlayerMsg.PLAY_MSG);
			startService(intent);
		}
	}

	class PauseButtonListener implements OnClickListener {
		public void onClick(View v) {
			//֪ͨService��ͣ����MP3
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra("MSG", AppConstant.PlayerMsg.PAUSE_MSG);
			startService(intent);
		}
	}

	class StopButtonListener implements OnClickListener {
		public void onClick(View v) {
			//֪ͨServiceֹͣ����MP3�ļ�
			Intent intent = new Intent();
			intent.setClass(PlayerActivity.this, PlayerService.class);
			intent.putExtra("MSG", AppConstant.PlayerMsg.STOP_MSG);
			startService(intent);
		}
	}
	
    private IntentFilter getIntentFilter(){
    	if(intentFilter==null){
    		intentFilter=new IntentFilter();
    		intentFilter.addAction(AppConstant.LRC_MESSAGE_ACTION);
    	}
    	return intentFilter;
    }
    /*
     * �㲥����������Ҫ�����ǽ���service�����͵Ĺ㲥�����Ҹ���ui��Ҳ���Ƿ��ø�ʵ�TextView
     */
    class LrcMessageBroadcastReceiver extends BroadcastReceiver{
		public void onReceive(Context context, Intent intent) {
    		String lrcMessage=intent.getStringExtra("lrcMessage");
    		lrcTextView.setText(lrcMessage);
		}
    }
}
