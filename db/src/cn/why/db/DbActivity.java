package cn.why.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import cn.why.R;
import cn.why.domain.Person;
import cn.why.service.PersonService;

public class DbActivity extends Activity {
	private ListView listView;
	private PersonService personService;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        personService = new PersonService(this);
        listView = (ListView) this.findViewById(R.id.listView);
        show();
        //ΪListView�����б�����������
        listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					Toast.makeText(getApplicationContext(),
                            "��������ǵڣ�"+arg2+"��", Toast.LENGTH_SHORT).show();
				}
        });
    }
	/**
	 * ��ʾ�б�listView
	 */
	private void show() {
		List<Person> persons = personService.getScrollData(0, 20);
		List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
		for(Person person : persons){
			HashMap<String, Object> item = new HashMap<String, Object>();
			item.put("name", person.getName());
			item.put("phone", person.getPhone());
			item.put("amount", person.getAmount());
			item.put("id", person.getId());
			data.add(item);
		}
		SimpleAdapter adapter = new SimpleAdapter(this,data,R.layout.item,
				new String[]{"name","phone","amount"},new int[]{R.id.name,R.id.phone,R.id.amount});
		listView.setAdapter(adapter);
	}
}