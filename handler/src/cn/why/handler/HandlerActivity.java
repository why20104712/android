package cn.why.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
/**
 * 1��ͬ�߳��ڲ�ͬ��������Ϣ����
 * Looper�����������ض��߳��ڶ���֮�����Ϣ����(Message Exchange)��
 * ���Ӧ�ó�����Բ��������̡߳���һ���߳̿����������������Щ���֮�䳣����Ҫ���ཻ��ѶϢ��
 * �����������Ҫ�����������̹߳���һ��Looper����������ѶϢ�����Ĺ�������
 * Looper����Ὠ��һ��MessageQueue���ݽṹ����Ÿ�����������Ϣ(����UI�¼���System�¼���)
 * @author Administrator
 */
public class HandlerActivity extends Activity implements Runnable{
	private static final int my_key=0x123;
    private int length=0;
    //ͼƬ����
    private int[] myImage={
            R.drawable.aa,
            R.drawable.bb,
            R.drawable.cc,
            R.drawable.dd
    };
    private Handler myHandler;
    private Thread myThread; 
    private ImageView myImageView;
    private Animation myAnimationAlpha;
	private Animation myAnimationScale;
	private Animation myAnimationTranslate;
	private Animation myAnimationRotate;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myImageView=(ImageView)findViewById(R.id.image); 
        myAnimationAlpha=new AlphaAnimation(0.1f, 1.0f);
        myAnimationAlpha.setDuration(3000);
        myAnimationScale=new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        myAnimationScale.setDuration(3000);
        
        myAnimationTranslate=new TranslateAnimation(30.0f, -80.0f, 30.0f, 300.0f);
        myAnimationTranslate.setDuration(3000);
        
       myAnimationRotate=new RotateAnimation(0.0f, +350.0f,
               Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
       myAnimationRotate.setDuration(3000);
       // myLinearLayout.setBackgroundColor(Color.WHITE);

    //������Ϣ����   
    myHandler=new Handler()
    {
        public void handleMessage(Message msg) {
            switch (msg.what) {
				case HandlerActivity.my_key:
					switch (length) {
						case 0:
							myImageView.startAnimation(myAnimationAlpha);
							break;
						case 1:
							myImageView.startAnimation(myAnimationScale);
							break;
						case 2:
							myImageView.startAnimation(myAnimationTranslate);
							break;
						case 3:
							myImageView.startAnimation(myAnimationRotate);
							break;
						default:
							break;
					}
					myImageView.setImageResource(myImage[length - 1]);
					myImageView.setScaleType(ImageView.ScaleType.FIT_XY);
					// myImageView.setLayoutParams(new LayoutParams(300, 200));

					if (length == myImage.length) {
						length = 0;
					}
					break;
            default:
                break;
            }
            super.handleMessage(msg);
        }
    };
    
    myThread=new Thread(this);
    myThread.start();
}
	public void run() {
		try {
            do
            {
                length++;
               Thread.sleep(4000);
                Message msg=new Message();
                msg.what=HandlerActivity.my_key;
                myHandler.sendMessage(msg);
            }
            while(Thread.interrupted()==false);
       } catch (Exception e) {
           e.printStackTrace();
       }
	}
}