package cn.why.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.Context;

public class FileService {
	private Context context;	//�����Ķ���
	
	public FileService(Context context) {
		super();
		this.context = context;
	}

	public void saveToSDCard(String fileName,String fileContent) throws Exception{
//		File file = new File("/mnt/sdcard",fileName);
		File file = new File(new File("/mnt/sdcard"),fileName);
		FileOutputStream outputStream = new FileOutputStream(file);
		outputStream.write(fileContent.getBytes());
		outputStream.close();
	}
	/**
	 * �����ļ�
	 * @param fileName �ļ�����
	 * @param fileContent �ļ�����
	 * @throws Exception 
	 */
	public void save(String fileName, String fileContent) throws Exception {
		//IOʵ���ļ�����
		//ֻ�����ļ����ƣ���֧��·��
		//mode:׷�ӡ�����
		//˽�в���ģʽ�������������ļ�ֻ�ܱ���Ӧ�÷��ʣ�����Ӧ���޷����ʸ��ļ����������˽��ģʽ�������ļ���д���ļ��е����ݻḲ��Դ�ļ�����
		//Ĭ�ϱ�����/data/data/cn.why/files/
		FileOutputStream outStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
		outStream.write(fileContent.getBytes());
		outStream.close();
	}
	/**
	 * ��ȡ�ļ�����
	 * @param fileName �ļ�����
	 * @return 
	 * @throws Exception
	 */
	public String read(String fileName) throws Exception{
		FileInputStream inStream = context.openFileInput(fileName);
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		//����������
		byte[] buffer = new byte[1024];
		//���귵��ֵΪ-1��Ϊ���귵�ض�ȡ�����ݸ���
		int length = 0;
		while((length = inStream.read(buffer)) != -1){
			outStream.write(buffer, 0, length);		//�ļ���ȡ���ڴ�
		}
		byte[] data = outStream.toByteArray();
		return new String(data);
	}

}
