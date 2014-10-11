package cn.why.listviewpage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import cn.why.adapter.ApkAdapter;
import cn.why.domian.ApkEntity;
import cn.why.listviewpage.LoadListView.ILoadListener;

public class MainActivity extends Activity implements ILoadListener {

	private LoadListView listView;
	private List<ApkEntity> apkList = new ArrayList<ApkEntity>();

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (LoadListView) findViewById(R.id.listview);
        listView.setInterface(this);
        
        apkList = getData();
        ApkAdapter apkAdapter = new ApkAdapter(this, apkList);
        listView.setAdapter(apkAdapter);
    }

	private List<ApkEntity> getData() {
		for (int i = 0; i < 10; i++) {
			 ApkEntity entity = new ApkEntity();
			 entity.setName("���Գ���");
			 entity.setInfo("50w�û�");
			 entity.setDes("����һ�������Ӧ�ã�");
			 apkList.add(entity);
		}
		return apkList;
	}
	
	private List<ApkEntity> getLoadData() {
		for (int i = 0; i < 2; i++) {
			 ApkEntity entity = new ApkEntity();
			 entity.setName("�������");
			 entity.setInfo("50w�û�");
			 entity.setDes("����һ�������Ӧ�ã�");
			 apkList.add(entity);
		}
		return apkList;
	}
	private List<ApkEntity> getReflashData() {
		for (int i = 0; i < 2; i++) {
			 ApkEntity entity = new ApkEntity();
			 entity.setName("ˢ�³���");
			 entity.setInfo("50w�û�");
			 entity.setDes("����һ�������Ӧ�ã�");
//			 apkList.add(entity);
			 apkList.add(0,entity);
		}
		return apkList;
	}

	/**
	 * �ϻ����ظ���
	 */
	public void onLoad() {

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//��ȡ��������
				apkList = getLoadData();
				//֪ͨ����listview����
		        ApkAdapter apkAdapter = new ApkAdapter(MainActivity.this, apkList);
		        listView.setAdapter(apkAdapter);
		        apkAdapter.notifyDataSetChanged();
		        listView.loadComplete();
			}
		}, 2000);
	}

	/**
	 * ����ˢ��
	 */
	public void onReflash() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				//��ȡ��������
				//��ȡ��������
				apkList = getReflashData();
				//֪ͨ����listview����
		        ApkAdapter apkAdapter = new ApkAdapter(MainActivity.this, apkList);
		        listView.setAdapter(apkAdapter);
		        apkAdapter.notifyDataSetChanged();
				//֪ͨlistview ˢ��������ϣ�
		        listView.reflashComplete();
			}
		}, 2000);
	}
	
	
}
