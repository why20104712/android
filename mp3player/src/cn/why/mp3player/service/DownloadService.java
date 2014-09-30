package cn.why.mp3player.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import cn.why.download.HttpDownloader;
import cn.why.model.Mp3Info;
import cn.why.mp3player.AppConstant;
import cn.why.mp3player.AppConstant.URL;

public class DownloadService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	//ÿ���û����ListActivity���е�һ����Ŀʱ���ͻ���ø÷���
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		//��Intent�����н�Mp3Info����ȡ��
		Mp3Info mp3Info = (Mp3Info)intent.getSerializableExtra("mp3Info");
		System.out.println("service----->"+mp3Info);
		//����һ�������̣߳�����Mp3Info������Ϊ�������ݵ��̶߳�����
		DownloadThread downloadThread = new DownloadThread(mp3Info);
		//�������߳�
		Thread thread = new Thread(downloadThread);
		thread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	/**
	 * ����һ���µ��̣߳�������������
	 * @author Administrator
	 */
	class DownloadThread implements Runnable{
		private Mp3Info mp3Info = null;
		public DownloadThread(Mp3Info mp3Info){
			this.mp3Info = mp3Info;
		}
		public void run() {
			//���ص�ַhttp://169.254.68.73:8080/mp3/a1.mp3
			//����MP3�ļ������֣��������ص�ַ
			String mp3Url = AppConstant.URL.BASE_URL + mp3Info.getMp3Name();
			String lrcUrl = AppConstant.URL.BASE_URL + mp3Info.getLrcName();
			//���������ļ����õĶ���
			HttpDownloader httpDownloader = new HttpDownloader();
			//���ļ��������������洢��SDCard����
			int mp3Result = httpDownloader.downFile(mp3Url, "mp3/", mp3Info.getMp3Name());
			int lrcResult = httpDownloader.downFile(lrcUrl, "mp3/", mp3Info.getLrcName());
			/*String resultMessage = null;
			if(result == -1){
				resultMessage = "����ʧ��";
			}
			else if(result == 0){
				resultMessage = "�ļ��Ѿ����ڣ�����Ҫ�ظ�����";
			}
			else if(result == 1){
				resultMessage = "�ļ����سɹ�";
			}*/
			//ʹ��Notification��ʾ�ͻ����ؽ��
		}
		
	}

}
