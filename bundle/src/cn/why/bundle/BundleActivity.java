package cn.why.bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

public class BundleActivity extends Activity {

	private OnClickListener listener;
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button button = (Button) this.findViewById(R.id.forward);
        Button alphaButton = (Button) this.findViewById(R.id.alpha);
        Button scaleButton = (Button) this.findViewById(R.id.scale);
        Button rotateButton = (Button) this.findViewById(R.id.rotate);
        Button translateButton = (Button) this.findViewById(R.id.translate);
        
      //ʹ��xml�ļ��Ķ���
		Animation alpha = AnimationUtils.loadAnimation(
				BundleActivity.this, R.anim.alpha);
		Animation scale = AnimationUtils.loadAnimation(
				BundleActivity.this, R.anim.scale);
		Animation rotate = AnimationUtils.loadAnimation(
				BundleActivity.this, R.anim.rotate);
		Animation translate = AnimationUtils.loadAnimation(
				BundleActivity.this, R.anim.translate);
		//��������
		alphaButton.startAnimation(alpha);
		scaleButton.startAnimation(scale);
		rotateButton.startAnimation(rotate);
		translateButton.startAnimation(translate);
		
        listener = new OnClickListener() {
			public void onClick(View v) {
				//ʵ��ҳ����ת
				Intent intent=new Intent();
				intent.setClass(BundleActivity.this, ResultActivity.class);
				Bundle bun=new Bundle();
				bun.putBoolean("boolean", true);
				bun.putInt("int", 1);
				bun.putChar("char", 'c');
				bun.putString("Str", "abcd");
				intent.putExtras(bun);//���ò��������ڴ��ݸ�����ҳ��
//				intent.putExtra("bun", bun);//���ò��������ڴ��ݸ�����ҳ��
//				intent.putExtra("param", "why");//���ò��������ڴ��ݸ�����ҳ��
				startActivity(intent);
				BundleActivity.this.finish();
			}
		};
		button.setOnClickListener(listener);
    }
}