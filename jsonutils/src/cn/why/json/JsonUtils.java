package cn.why.json;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedList;

import cn.why.domain.User;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

public class JsonUtils {

	/**
	 * ʹ��gson����json����
	 * @param jsonData
	 */
	public void parseJsonArray(String jsonData) {
		JsonReader jsonReader = new JsonReader(new StringReader(jsonData));
		try {
			jsonReader.beginArray();//��ʼ����json����
			while (jsonReader.hasNext()) {
				jsonReader.beginObject();//��ʼ����json����
				while (jsonReader.hasNext()) {
					String tagName = jsonReader.nextName();
					if (tagName.equals("name")) {
						String name = jsonReader.nextString();
						System.out.println("name--->>>"+name);
					}
					if (tagName.equals("age")) {
						int age = jsonReader.nextInt();
						System.out.println("age--->>>"+age);
					}
				}
				jsonReader.endObject();//����json�������
			}
			jsonReader.endArray();//����json�������
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ʹ��gson����json������Userʵ������Ӧ
	 * @param jsonData
	 */
	public void parseJsonObjectToUser(String jsonData) {
		Gson gson = new Gson();
		User user = gson.fromJson(jsonData, User.class);//��json����ת��Ϊuser����
		System.out.println("name--->>>"+user.getName());
		System.out.println("age--->>>"+user.getAge());
	}

	/**
	 * ʹ��gson����json������Userʵ������Ӧ����ʽ����
	 * @param jsonData
	 */
	public void parseJsonArrayToUsers(String jsonData) {
		Type listType = new TypeToken<LinkedList<User>>() {}.getType();
		Gson gson = new Gson();
		LinkedList<User> users = gson.fromJson(jsonData, listType);//��json����ת��Ϊuser���󼯺�
		for (Iterator iterator = users.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			System.out.println("name--->>>"+user.getName());
			System.out.println("age--->>>"+user.getAge());
		}
	}
}
