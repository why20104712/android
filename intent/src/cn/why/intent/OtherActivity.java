package cn.why.intent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class OtherActivity extends Activity{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other);
		Intent intent = getIntent();//��ȡ��ͼ����
		//ȡ�ò����ĵ�һ�ַ���
//		String param = intent.getStringExtra("param");//��ȡ�ַ������Ͳ���
//		int a = intent.getIntExtra("int", 0);//��ȡ�������Ͳ���
		//ȡ�ò����ĵڶ��ַ���
		Bundle bundle = intent.getExtras();
		String company = bundle.getString("company");
		int age = bundle.getInt("age");
		
		Toast.makeText(getApplicationContext(), company+age, 1).show();
	}
	/**
	 * �ر�activity���������ݸ�IntentActivity
	 */
	public void closeActivity(View v) {
		Intent intent = new Intent();
		intent.putExtra("result", "�Ϸ��������Ĺ��£�����ʡ��2000��");
		setResult(20, intent);//���÷�������
		finish();//�ر�Activity(�رմ���)
	}
}
