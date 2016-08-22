package com.zc.contacts.biz;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.zc.contacts.activity.TelephoneActivity;
import com.zc.contacts.base.utils.ToastUtil;
import com.zc.contacts.entity.Telephonename;
/**
 * ��ȡcommonnum.db���ݿ��ļ�
 * @author zc
 */

public class TelDBReaderManager {
	
	public static File file = new File("data/data/com.zc.contacts/files/commonnum.db");
	private static final String DB_TABLE = "classlist";
	/**
	 * �ж�commonnum.db�ļ��Ƿ����
	 * @return    �ļ��Ƿ����
	 */
	public static boolean isExits(){
		if(file.exists() && file.length() > 0){
			return true;
		}
			return false;
	}
	
	/**
	 *  ��ȡClasslist���ݿ��е�����
	 * ������TelephoneActivity�����Listview����ʾ������Դ
	 * @param context  ������
	 * @return  �����������ϵ�����
	 */
	public static ArrayList<Telephonename> gettelephonename (Context context){
		ArrayList<Telephonename> telephonename = new ArrayList<Telephonename>();
		//��׿�Դ���С�����ݿ�SQliteDatabase
		/*
		 * openOrCreateDatabase�������ڴ򿪻򴴽�һ�����ݿ�
		 * ����1��Ҫ�򿪵����ݿ�·��
		 * ����2���α깤��  һ�㲻��ֱ�� null
		 */
		if (isExits()) {
			//��ȡ���ݿ����
			SQLiteDatabase sq = SQLiteDatabase.openOrCreateDatabase(file, null);
			//��ȡ����	����1�� SQL���  	 ����2��������Ӧ��ֵ                 
			//select * from ���� where id = 4;    Cursor cursor = sq.rawQuery("select * from "+DB_TABLE+"where id=?", new String[]{"2"});
			Cursor cursor = sq.rawQuery("select * from "+DB_TABLE, null);//cursor �൱�ڼ���  ���ص����ݶ�����Cursor
			
			//ѭ������ȡ������
			while(cursor.moveToNext()){//moveToNext �����ж��Ƿ�����һ�����ݣ����������ƶ�ָ��
				String name = cursor.getString(cursor.getColumnIndex("name"));//ͨ��������ȡ�е�����
				Telephonename names = new Telephonename(name);
				telephonename.add(names);
			}
			//�ر�Cursor����
			cursor.close();
			//�ر����ݿ���Դ
			sq.close();
			
			return telephonename;
		}else {
			ToastUtil.show(context, "���ݿ��ļ�û���ҵ�", Toast.LENGTH_SHORT);
			return null;
		}
	}
	
	/**
	 * 
	 * ��ѯtable1....table8���е�����
	 * @param	context ������
	 * @param	tablename  Ҫ��ѯ�ı���
	 * @return		�����ű�������      ĳ�ű��е�����
	 */
	public static ArrayList<Telephonename> gettelephonenameitem(Context context,String tablename){
		ArrayList<Telephonename> telephonename = new ArrayList<Telephonename>();
		if (isExits()) {
			SQLiteDatabase sq = SQLiteDatabase.openOrCreateDatabase(file, null);
//			Cursor cursor = sq.rawQuery(tablename, new String []{"_id","number","name"});//cursor �൱�ڼ���  ���ص����ݶ�����Cursor
			
			Cursor cursor = sq.query(tablename, new String []{"_id","number","name"}, null, null, null, null, "_id asc", null);
			
			//ѭ������ȡ������
			while(cursor.moveToNext()){//moveToNext �����ж��Ƿ�����һ�����ݣ����������ƶ�ָ��
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String number = cursor.getString(cursor.getColumnIndex("number"));
				int id = cursor.getInt(cursor.getColumnIndex("_id"));
				
				Telephonename names = new Telephonename(id,number,name);//ͨ��������ȡ�е�����
				telephonename.add(names);
			}
			//�ر�Cursor����
			cursor.close();
			//�ر����ݿ���Դ
			sq.close();
			return telephonename;
			
		}else {
			ToastUtil.show(context, "���ݿ��ļ�û���ҵ�", Toast.LENGTH_SHORT);
			return null;
		}
		
		
	}
	
	
	
}
