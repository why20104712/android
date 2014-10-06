package cn.why.bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Toast;

public class ResultActivity extends Activity {

	private OnClickListener listener;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		Button button = (Button) this.findViewById(R.id.back);
		//���մ�BundleActivity���ݵĲ���param
		Bundle bun=this.getIntent().getExtras();
//		Bundle bun=this.getIntent().getBundleExtra("bun");
//		String param = this.getIntent().getStringExtra("param");
		String s = bun.getString("Str");
		Toast.makeText(getApplicationContext(), s, 1).show();
		/**
		 * Tweened Animations�ķ���
��������Alpha�����뵭��Ч��
��������Scale������Ч��
��������Rotate����תЧ��
��������Translate���ƶ�Ч��
		 */
		//ʹ��xml�ļ��Ķ���
		Animation animation = AnimationUtils.loadAnimation(
				ResultActivity.this, R.anim.alpha);
//		Animation animation = AnimationUtils.loadAnimation(
//				ResultActivity.this, R.anim.rotate);
//		Animation animation = AnimationUtils.loadAnimation(
//				ResultActivity.this, R.anim.ascale);
//		Animation animation = AnimationUtils.loadAnimation(
//				ResultActivity.this, R.anim.translate);
         // ��������
		button.startAnimation(animation);
		
//		//����һ��AnimationSet���󣬲���ΪBoolean�ͣ�
//        //true��ʾʹ��Animation��interpolator��false����ʹ���Լ���
//        AnimationSet animationSet = new AnimationSet(true);
//        //����һ��AlphaAnimation���󣬲�������ȫ��͸���ȣ�����ȫ�Ĳ�͸��
//        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
//        //���ö���ִ�е�ʱ��
//        alphaAnimation.setDuration(5000);
//        //��alphaAnimation������ӵ�AnimationSet����
//        animationSet.addAnimation(alphaAnimation);
//        //ʹ��ImageView��startAnimation����ִ�ж���
//        button.startAnimation(animationSet);
//        
////        AnimationSet animationSet = new AnimationSet(true);
//        //����1�����ĸ���ת�Ƕȿ�ʼ
//        //����2��ת��ʲô�Ƕ�
//        //��4��������������Χ������ת��Բ��Բ��������
//        //����3��ȷ��x����������ͣ���ABSOLUT�������ꡢRELATIVE_TO_SELF������������ꡢRELATIVE_TO_PARENT����ڸ��ؼ�������
//        //����4��x���ֵ��0.5f����������������ؼ���һ�볤��Ϊx��
//        //����5��ȷ��y�����������
//        //����6��y���ֵ��0.5f����������������ؼ���һ�볤��Ϊx��
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
//               Animation.RELATIVE_TO_SELF,0.5f,
//               Animation.RELATIVE_TO_SELF,0.5f);
//        rotateAnimation.setDuration(1000);
//        animationSet.addAnimation(rotateAnimation);
//        button.startAnimation(animationSet);
//        
//        
////        AnimationSet animationSet = new AnimationSet(true);
//        //����1��x��ĳ�ʼֵ
//        //����2��x���������ֵ
//        //����3��y��ĳ�ʼֵ
//        //����4��y���������ֵ
//        //����5��ȷ��x�����������
//        //����6��x���ֵ��0.5f����������������ؼ���һ�볤��Ϊx��
//        //����7��ȷ��y�����������
//        //����8��y���ֵ��0.5f����������������ؼ���һ�볤��Ϊx��
//        ScaleAnimation scaleAnimation = new ScaleAnimation(
//               0, 0.1f,0,0.1f,
//               Animation.RELATIVE_TO_SELF,0.5f,
//               Animation.RELATIVE_TO_SELF,0.5f);
//        scaleAnimation.setDuration(1000);
//        animationSet.addAnimation(scaleAnimation);
//        button.startAnimation(animationSet);
//        
////        AnimationSet animationSet = new AnimationSet(true);
//        //����1��2��x��Ŀ�ʼλ��
//        //����3��4��y��Ŀ�ʼλ��
//        //����5��6��x��Ľ���λ��
//        //����7��8��x��Ľ���λ��
//        TranslateAnimation translateAnimation =
//           new TranslateAnimation(
//               Animation.RELATIVE_TO_SELF,0f,
//               Animation.RELATIVE_TO_SELF,0.5f,
//               Animation.RELATIVE_TO_SELF,0f,
//               Animation.RELATIVE_TO_SELF,0.5f);
//        translateAnimation.setDuration(1000);
//        animationSet.addAnimation(translateAnimation);
//        button.startAnimation(animationSet);
        
        listener = new OnClickListener() {
			public void onClick(View v) {
				//ʵ��ҳ����ת
				Intent intent=new Intent();
				intent.setClass(ResultActivity.this, BundleActivity.class);
				startActivity(intent);
				ResultActivity.this.finish();
			}
		};
		button.setOnClickListener(listener);
	}
	
}
