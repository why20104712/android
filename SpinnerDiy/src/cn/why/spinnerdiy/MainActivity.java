package cn.why.spinnerdiy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * �Զ��������б�ķ��
 * @author Administrator
 *
 */
public class MainActivity extends Activity implements OnItemSelectedListener{

	private TextView textView;
	private Spinner spinner;
	private ArrayAdapter<String> arrayAdapter;
	private SimpleAdapter simpleAdapter;
	private List<Map<String, Object>> dataList;
	private List<String> list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     // ��һ�������һ�������б����list��������ӵ�����������б�Ĳ˵���
        textView = (TextView) findViewById(R.id.textView);
        spinner = (Spinner) findViewById(R.id.spinner);
        dataList = new ArrayList<Map<String,Object>>();
        list = new ArrayList<String>();
        list.add("����");
		list.add("�Ϻ�");
		list.add("����");
		list.add("����");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);//����������
        
        
     // �ڶ�����Ϊ�����б���һ����������������õ���ǰ�涨���list
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.item_layout, new String[]{"image", "text"}, new int[]{R.id.image, R.id.text});
//     // ��������Ϊ���������������б�����ʱ�Ĳ˵���ʽ��
//        simpleAdapter.setDropDownViewResource(R.layout.item_layout);
//     // ���Ĳ�������������ӵ������б���
//        spinner.setAdapter(simpleAdapter);//����������
//     // ���岽��Ϊ�����б����ø����¼�����Ӧ���������Ӧ�˵���ѡ��
        spinner.setOnItemSelectedListener(this);//��Ӽ���
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
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
//		textView.setText("��ѡ����ǣ�"+simpleAdapter.getItem(arg2));
		textView.setText("��ѡ����ǣ�"+arrayAdapter.getItem(arg2));
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
	}
}
