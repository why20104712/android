package cn.why.mp3player;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import cn.why.download.HttpDownloader;
import cn.why.model.Mp3Info;
import cn.why.mp3player.service.DownloadService;
import cn.why.xml.Mp3ListContentHandler;

public class Mp3ListActivity extends ListActivity {
	private static final int UPDATE = 1;
	private static final int ABOUT = 2;
	private List<Mp3Info> mp3Infos = null;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remote_mp3_list);
        updateListView();
    }
    /**
     * ���û����menu��ť֮����ø÷��������ǿ����ڸ÷����м����Լ��İ�ť�ؼ�
     */
	public boolean onCreateOptionsMenu(Menu menu) {
		//��Ӳ˵�ѡ�ť
		menu.add(0, UPDATE, 1, R.string.mpelist_update);
		menu.add(0, ABOUT, 1, R.string.mp3list_about);
		
		return super.onCreateOptionsMenu(menu);
	}
	/**
	 * �˵�ѡ�ť����¼�
	 */
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("item-id--->"+item.getItemId());
		if (item.getItemId() == UPDATE) {
			//�û�����˸���ѡ�ť
			updateListView();
		} else if (item.getItemId() == ABOUT) {
			// �û�����˹���ѡ�ť
			Toast.makeText(getApplicationContext(), "WHY", 1).show();
		}
		return super.onOptionsItemSelected(item);
	}
	/**
	 * ����XML�ļ�
	 * @param urlStr
	 * @return String xml�ļ�
	 */
	private String downloadXML(String urlStr) {
		HttpDownloader httpDownloader = new HttpDownloader();
		String xml = httpDownloader.download(urlStr);
		return xml;
	}
    /**
     * ���������б�
     */
	private void updateListView() {
		// �û�����˸����б�ť
		// ���ذ�������Mp3������Ϣ��xml�ļ�
		String xml = downloadXML("http://169.254.68.73:8080/mp3/resources.xml");
		// ��xml�ļ����н��������������Ľ�����õ�Mp3Info�����У������ЩMp3Info������õ�List����
		mp3Infos = parse(xml);
		SimpleAdapter simpleAdapter = buildSimpleAdapter(mp3Infos);
		setListAdapter(simpleAdapter);
	}
	/**
	 * ����xml�ļ�
	 * @param xmlStr
	 * @return
	 */
	private List<Mp3Info> parse(String xmlStr) {
		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		List<Mp3Info> infos = new ArrayList<Mp3Info>();
		try {
			XMLReader xmlReader = saxParserFactory.newSAXParser()
					.getXMLReader();
			Mp3ListContentHandler mp3ListContentHandler = new Mp3ListContentHandler(
					infos);
			xmlReader.setContentHandler(mp3ListContentHandler);
			xmlReader.parse(new InputSource(new StringReader(xmlStr)));
			for (Iterator iterator = infos.iterator(); iterator.hasNext();) {
				Mp3Info mp3Info = (Mp3Info) iterator.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infos;
	}
	private SimpleAdapter buildSimpleAdapter(List<Mp3Info> mp3Infos) {
		// ����һ��List���󣬲�����SimpleAdapter�ı�׼����mp3Infos���е�������ӵ�List����ȥ
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (Iterator iterator = mp3Infos.iterator(); iterator.hasNext();) {
			Mp3Info mp3Info = (Mp3Info) iterator.next();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("mp3_name", mp3Info.getMp3Name());
			map.put("mp3_size", mp3Info.getMp3Size());
			list.add(map);
		}
		// ����һ��SimpleAdapter����
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, list,
				R.layout.mp3info_item, new String[] { "mp3_name", "mp3_size" },
				new int[] { R.id.mp3_name, R.id.mp3_size });
		// �����SimpleAdapter�������õ�ListActivity����
		return simpleAdapter;
	}
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Mp3Info mp3Info = mp3Infos.get(position);
		// ����Intent����
		Intent intent = new Intent();
		// ��Mp3Info������뵽Intent������
		intent.putExtra("mp3Info", mp3Info);
		intent.setClass(this, DownloadService.class);
		// ����Service
		startService(intent);
		super.onListItemClick(l, v, position, id);
	}
	
}