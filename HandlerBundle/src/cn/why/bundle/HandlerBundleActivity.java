package cn.why.bundle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

public class HandlerBundleActivity extends Activity {
	Bundle bundle = new Bundle();
	//����һ��HandlerThread����ʵ����ʹ��Looper��������Ϣ���еĹ��ܣ��������AndroidӦ�ó������ṩ
	HandlerThread handlerThread = new HandlerThread("handler_thread");
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        System.out.println("Main----->"+Thread.currentThread().getId());
        System.out.println("Main----->"+Thread.currentThread().getName());
        handlerThread.start();
      //��ʹ��HandlerThread��getLooper()����֮ǰ�������ȵ��ø����start();
        MyHandler myHandler = new MyHandler(handlerThread.getLooper());
        Message msg = myHandler.obtainMessage();
//        myHandler.handleMessage(msg);
      //��msg���͵�Ŀ�������ν��Ŀ����󣬾������ɸ�msg�����handler����
        bundle.putInt("age", 1);
        bundle.putString("name", "www");
        msg.setData(bundle);
        msg.sendToTarget();
    }
    
    class MyHandler extends Handler{
    	public MyHandler() {	}
    	public MyHandler(Looper looper) {
    		super(looper);
		}
		public void handleMessage(Message msg) {
			System.out.println("MyHandler----->" + Thread.currentThread().getId());
			System.out.println("MyHandler----->" + Thread.currentThread().getName());
			System.out.println("handlerMessage");
			Bundle bundle = msg.getData();
			int age = bundle.getInt("age");
			String name = bundle.getString("name");
			System.out.println("age is " + age + ", name is " + name);
		}
    }
    
}