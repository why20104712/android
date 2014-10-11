package cn.why.listviewdemo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity implements OnScrollListener, OnItemClickListener {

	private ListView listView;
	private ArrayAdapter<String> arrayAdapter;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> dataList = null;
	int i = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        listView = (ListView) findViewById(R.id.listView);
        //��������������Ϊ֮�������Դ
//        String[] array = new String[]{"why1","why2","why3","why4","why5","why6"};
        //��һ����������˼�������ģ��ڶ�����������˼��ÿһ��listview���Ӧ�Ĳ����ļ�������������������Դ���ַ������飩
//        arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, array);
        //ΪlistView��ͼ�����������
//        listView.setAdapter(arrayAdapter);
        dataList = new ArrayList<Map<String,Object>>();
        /**
         * context��������
         * data������Դ List<? extends Map<String, ?>> data��map��������ɵ�list����
         * ÿ��map����ȥ��ӦListView�б��е�һ��
         * ÿ��map�еļ��������������from��ָ���ļ�
         * resource���б����Ӧ�Ĳ����ļ���ID
         * from��map�еļ���
         * to����������ͼ��Id
         */
        simpleAdapter = new SimpleAdapter(MainActivity.this, getData(), R.layout.list_item, 
        		new String[]{"image", "text"}, new int[]{R.id.image, R.id.text});
        listView.setAdapter(simpleAdapter);
        
        listView.setOnItemClickListener(this);//����listView��������
        listView.setOnScrollListener(this);//����listview��������
    }
    /**
     * simpleAdapter������Դ
     * @return
     */
    private List<Map<String, Object>> getData(){
    	for (int i = 0; i < 20; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", R.drawable.ic_launcher);
			map.put("text", "why"+i);
			dataList.add(map);
		}
    	return dataList;
    }
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		
	}
	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {

		switch (scrollState) {
		case SCROLL_STATE_FLING:
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("text", "������� "+i++);
			map.put("image", R.drawable.ic_launcher);
			dataList.add(map);
			listView.setAdapter(simpleAdapter);
			simpleAdapter.notifyDataSetChanged();
			break;

		case SCROLL_STATE_IDLE:
			break;
		case SCROLL_STATE_TOUCH_SCROLL:
			break;
		}
		// ��ָ�뿪��Ļǰ����������һ��
//		if (scrollState == SCROLL_STATE_FLING) {
//			Toast.makeText(MainActivity.this, "������һ��", 0).show();
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("text", "������� " + i++);
//			map.put("image", R.drawable.ic_launcher);
//			dataList.add(map);
//			listView.setAdapter(simpleAdapter);
//			simpleAdapter.notifyDataSetChanged();
//		} else
//		// ֹͣ����
//		if (scrollState == SCROLL_STATE_IDLE) {
//
//		} else
//		// ���ڹ���
//		if (scrollState == SCROLL_STATE_TOUCH_SCROLL) {
//
//		}
	}
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		// ��ȡ���ListView item�е�������Ϣ
				String text = listView.getItemAtPosition(position) + "";
				// ����Toast��Ϣ��ʾ���λ�ú�����
				Toast.makeText(MainActivity.this,
						"position=" + position + " content=" + text, 0).show();
	}
}
