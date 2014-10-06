package cn.why.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import cn.why.service.DBOpenHelper;

public class PersonProvider extends ContentProvider {
	private DBOpenHelper dbOpenHelper;
	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	private static final int PERSONS = 1;	//ƥ����
	private static final int PERSON = 2;
	/**
	 * ��̬�����
	 */
	static{
		MATCHER.addURI("cn.why.providers.personprovider", "person", PERSONS);
		MATCHER.addURI("cn.why.providers.personprovider", "person/#", PERSON);	//#��������
	}
	/**
	 * ֻ�ᱻ����һ�Σ��������ṩ�߳�ʼ������ʱ����
	 */
	@Override
	public boolean onCreate() {
		dbOpenHelper = new DBOpenHelper(this.getContext());
		return true;
	}

	/**
	 * ���������ⲿӦ�ò�ѯ�����ṩ���в�ѯ����
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		switch (MATCHER.match(uri)) {
		case PERSONS:
			return db.query("person", projection, selection, selectionArgs, null, null, sortOrder);
		case PERSON:
			long rowid = ContentUris.parseId(uri);
			String where = "personid="+ rowid;
			if(selection!=null && !"".equals(selection.trim())){
				where += " and "+ selection;
			}
			return db.query("person", projection, where, selectionArgs, null, null, sortOrder);
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
	}

	/**
	 * ������������
	 */
	@Override
	public String getType(Uri uri) {
		switch (MATCHER.match(uri)) {
		case PERSONS:
			return "vnd.android.cursor.dir/person";
		case PERSON:
			return "vnd.android.cursor.item/person";
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
	}

	/**
	 * ���������ⲿӦ�ò������ݵ������ṩ����
	 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		switch (MATCHER.match(uri)) {
		case PERSONS:
			long rowid = db.insert("person", "name", values);//����ֵ
			//  content://cn.why.providers.personprovider/person/10
//			Uri insertUri = Uri.parse(" content://cn.why.providers.personprovider/person/"+rowid);
			Uri insertUri = ContentUris.withAppendedId(uri, rowid);
			this.getContext().getContentResolver().notifyChange(uri, null);//�������ݱ仯֪ͨ
			return insertUri;
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
	}

	/**
	 * ���������ⲿӦ��ɾ�������ṩ��������
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		int num = 0;
		switch (MATCHER.match(uri)) {
		case PERSONS:
			num = db.delete("person", selection, selectionArgs);
			break;
		case PERSON:
			long rowid = ContentUris.parseId(uri);
			String where = "personid="+ rowid;
			if(selection!=null && !"".equals(selection.trim())){
				where += " and "+ selection;
			}
			num = db.delete("person", where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
		return num;
	}

	/**
	 * ���������ⲿӦ�ø��������ṩ��������
	 */
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		int num = 0;
		switch (MATCHER.match(uri)) {
		case PERSONS:
			num = db.update("person", values, selection, selectionArgs);
			break;
		case PERSON:
			long rowid = ContentUris.parseId(uri);
			String where = "personid="+ rowid;
			if(selection!=null && !"".equals(selection.trim())){
				where += " and "+ selection;
			}
			num = db.update("person", values, where, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("this is Unknown Uri:"+ uri);
		}
		return num;
	}

}
