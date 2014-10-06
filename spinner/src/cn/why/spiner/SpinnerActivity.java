package cn.why.spiner;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SpinnerActivity extends Activity {
	private Spinner spinner;
	private TextView textview;
	private Button addButton;
	private Button removeButton;
	private EditText editText;
	private ArrayAdapter<String> adapter; 
	ArrayList<String> list=new ArrayList<String>();
	private SoundPool pool;
	private int click;
	private static final String bloodGroup[]={"A��","B��","O��","AB��","����"};
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        textview = (TextView)findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        addButton = (Button) findViewById(R.id.addButton);
        removeButton = (Button) findViewById(R.id.removeButton);
        spinner=(Spinner)findViewById(R.id.spinner);
        
        pool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        //������Ч
    	click = pool.load(this, R.raw.about, 1);
        //���������������Դ
//        adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bloodGroup);
      //��ȡ��Ӧ����
        String[] ls=getResources().getStringArray(R.array.cities);
        //��ȡXML�ж��������
        for(int i=0;i<ls.length;i++){
        	list.add(ls[i]);
        }
        //�����鵼�뵽ArrayList��
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        //���������򵯳���ʽ��ʹ�ð�׿ϵͳ�������ʽ
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
		//���ñ�����
		spinner.setPrompt("������");
		//�������������¼�����
		spinner.setOnItemSelectedListener(new OnItemSelectedListener()
		{
			public void onItemSelected(AdapterView<?> arg0, View arg1,	int arg2, long arg3) {
				textview.setText("����ѡ����ǣ�"+list.get(arg2));
			}

			public void onNothingSelected(AdapterView<?> parent) {
				
			}
		});
		
		spinner.setOnTouchListener(new OnTouchListener() {
			Animation scale = AnimationUtils.loadAnimation(SpinnerActivity.this, R.anim.scale);
            public boolean onTouch(View v, MotionEvent event) {
                v.startAnimation(scale);
                return false;
            }
        });
		
		//�������ѡ�ť����
		addButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	//��ȡ�ı�����ֵ
                String item=editText.getText().toString();
				for (int i = 0; i < adapter.getCount(); i++) {
					if (item.equals(adapter.getItem(i))) {
						Toast.makeText(getApplicationContext(), "������ͬ��ֵ", 1)
								.show();
						return;
					}
				}
				if (item.length() > 0) {
					Toast.makeText(getApplicationContext(), "��������ͬ��ֵ", 1).show();
					adapter.add(item);
					int position = adapter.getPosition(item);
					spinner.setSelection(position);
					//�ڱ��������Ӻ�list�Ĵ�С
					setTitle(String.valueOf(list.size()));
				}
				//������Ч
				playSound();
            }
        });
		//�Ƴ�����ѡ�ť����
		removeButton.setOnClickListener(new OnClickListener(){//ɾ����ť������
			public void onClick(View v) {
						 if(spinner.getSelectedItem().toString()!=adapter.getItem(0))
		                 {
							new AlertDialog.Builder(SpinnerActivity.this)
									.setTitle("��ʾ")
									.setMessage("ȷ��ɾ����")
									.setPositiveButton(
											"ȷ��",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,int which) {
													adapter.remove(spinner.getSelectedItem()	.toString());
												}
											}).setNeutralButton("ȡ��", null)
									.show();
		                 }
		                 if(adapter.getCount()==1)
		                 {
		                	 Toast.makeText(getApplicationContext(), "�Ѿ�û�п���ɾ����������", 1)
								.show();
		                 }
		                 // ɾ����ǰѡ����,remove���Զ�����notifyDataSetChanged()
//						adapter.remove(spinner.getSelectedItem().toString());
						// �ڱ��������Ӻ�list�Ĵ�С
						setTitle(String.valueOf(list.size()));
					}
			});
    }

    /**
     * ������Ч
     */
    private void playSound(){
    	pool.play(click, 1, 1, 0, 0, 1);
    }
    /**
     * �����˳�����
     */
	@Override
	public void onBackPressed() {
		showDialog();
	}
    /**
     * ��ʾdialog��ʾ��
     */
	public void showDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("��ܰ��ʾ");
		builder.setMessage("ȷ���˳���");
		builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				SpinnerActivity.this.finish();
			}
		});
		
		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		
	}
}