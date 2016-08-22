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
 * 读取commonnum.db数据库文件
 * @author zc
 */

public class TelDBReaderManager {
	
	public static File file = new File("data/data/com.zc.contacts/files/commonnum.db");
	private static final String DB_TABLE = "classlist";
	/**
	 * 判断commonnum.db文件是否存在
	 * @return    文件是否存在
	 */
	public static boolean isExits(){
		if(file.exists() && file.length() > 0){
			return true;
		}
			return false;
	}
	
	/**
	 *  获取Classlist数据库中的数据
	 * 用于在TelephoneActivity界面的Listview中显示的数据源
	 * @param context  上下文
	 * @return  返回主界面上的数据
	 */
	public static ArrayList<Telephonename> gettelephonename (Context context){
		ArrayList<Telephonename> telephonename = new ArrayList<Telephonename>();
		//安卓自带的小型数据库SQliteDatabase
		/*
		 * openOrCreateDatabase函数用于打开或创建一个数据库
		 * 参数1：要打开的数据库路径
		 * 参数2：游标工厂  一般不用直接 null
		 */
		if (isExits()) {
			//获取数据库对象
			SQLiteDatabase sq = SQLiteDatabase.openOrCreateDatabase(file, null);
			//读取数据	参数1： SQL语句  	 参数2：条件对应的值                 
			//select * from 表名 where id = 4;    Cursor cursor = sq.rawQuery("select * from "+DB_TABLE+"where id=?", new String[]{"2"});
			Cursor cursor = sq.rawQuery("select * from "+DB_TABLE, null);//cursor 相当于集合  返回的数据都放在Cursor
			
			//循环集合取出数据
			while(cursor.moveToNext()){//moveToNext 函数判断是否还有下一个数据，并且往下移动指针
				String name = cursor.getString(cursor.getColumnIndex("name"));//通过列名获取列的索引
				Telephonename names = new Telephonename(name);
				telephonename.add(names);
			}
			//关闭Cursor对象
			cursor.close();
			//关闭数据库资源
			sq.close();
			
			return telephonename;
		}else {
			ToastUtil.show(context, "数据库文件没有找到", Toast.LENGTH_SHORT);
			return null;
		}
	}
	
	/**
	 * 
	 * 查询table1....table8表中的数据
	 * @param	context 上下文
	 * @param	tablename  要查询的表名
	 * @return		查哪张表返回哪张      某张表中的数据
	 */
	public static ArrayList<Telephonename> gettelephonenameitem(Context context,String tablename){
		ArrayList<Telephonename> telephonename = new ArrayList<Telephonename>();
		if (isExits()) {
			SQLiteDatabase sq = SQLiteDatabase.openOrCreateDatabase(file, null);
//			Cursor cursor = sq.rawQuery(tablename, new String []{"_id","number","name"});//cursor 相当于集合  返回的数据都放在Cursor
			
			Cursor cursor = sq.query(tablename, new String []{"_id","number","name"}, null, null, null, null, "_id asc", null);
			
			//循环集合取出数据
			while(cursor.moveToNext()){//moveToNext 函数判断是否还有下一个数据，并且往下移动指针
				String name = cursor.getString(cursor.getColumnIndex("name"));
				String number = cursor.getString(cursor.getColumnIndex("number"));
				int id = cursor.getInt(cursor.getColumnIndex("_id"));
				
				Telephonename names = new Telephonename(id,number,name);//通过列名获取列的索引
				telephonename.add(names);
			}
			//关闭Cursor对象
			cursor.close();
			//关闭数据库资源
			sq.close();
			return telephonename;
			
		}else {
			ToastUtil.show(context, "数据库文件没有找到", Toast.LENGTH_SHORT);
			return null;
		}
		
		
	}
	
	
	
}
