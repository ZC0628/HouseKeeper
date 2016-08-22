package com.zc.contacts.activity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.adapter.TelephoneAdapter;
import com.zc.contacts.base.activity.BaseActivity;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.base.utils.ToastUtil;
import com.zc.contacts.biz.AssetsDBManager;
import com.zc.contacts.biz.TelDBReaderManager;
import com.zc.contacts.entity.Telephonename;

import android.R.color;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 通讯录主界面
 * @author zc
 */
public class TelephoneActivity extends BaseActivity implements OnItemClickListener{
	private static final String TAG = "TelephoneActivity";  //哪个类打印日志 标签就用类名表示
	private ListView mlit;
	private TelephoneAdapter madapter; 
	public static final String ASSET_DBFILE = "db/commonnum.db"; 
//	public static final String ToFILEPATH = "data/data/com.zc.contacts/files/commonnum.db";
	public static String path;
	private long waittime = 1000;//双击等待时间  1秒内
	private long time = 0;//记录时间
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telephone);
		//初始化数据库信息
		initDBInfo();
		
		//初始化控件
		initview();
		
	}
	
	/*//监听设备按键（方法二）——单独监听back键
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}*/
	
	
	//监听设备按键（方法一）
	/* * onKeyDown(int keyCode, KeyEvent event)
	 * 参数1：按键码   每个按键都有对应的一个整数
	 * 参数2：按键的信息*/
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		//如果按的是back键
		case KeyEvent.KEYCODE_BACK:
			//双击退出应用程序     1秒内双击
			//获取当前时间毫秒值
			long curtime = System.currentTimeMillis();
			if (curtime - time <= waittime) {
				//双击
				TelephoneActivity.this.finish();
			}else {
				//提示
				ToastUtil.show(this, "再按一次退出",0);
			}
			time = curtime;//记录
			return true;
		}
		//super.onKeyDown(keyCode, event);表示调用父类中默认的设备按键处理方式
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void initview() {
		mlit = (ListView) findViewById(R.id.lst_telephone);
		
		madapter = new TelephoneAdapter(this);
		madapter.addDatatoadapter(TelDBReaderManager.gettelephonename(this));//将集合中的数据传递给适配器    MVC模式 
		//手动添加一个数据到索引0的位置
		madapter.addDatatoadapter(new Telephonename("本地电话"), 0);
		
		//设置点击事件	setOnItemClickListener  设置条目的点击监听
		mlit.setOnItemClickListener(this);
		mlit.setAdapter(madapter);
	}


	private void initDBInfo() {
		path = getFilesDir()+File.separator+"commonnum.db";
		if(!new File(path).exists()){
			//拷贝数据库文件到手机中
			AssetsDBManager.copyAssetsDBFileToPhoneSD(this, 
					ASSET_DBFILE, 
					path);}

		
		//读取数据
		ArrayList<Telephonename> telnames = TelDBReaderManager.gettelephonename(this);
		for (Telephonename telephonename : telnames) {
			LogUtil.printlog(TAG, telephonename+"");
		}
		
		/*ArrayList<Telephonename> telnames = DBReader.gettelephonenameitem(this, "table1");
		for (Telephonename telephonename : telnames) {
			LogUtil.printlog(TAG, telephonename+"");
		}*/
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	/**
	 * 当点击了ListView中的某个item的时候调用
	 * 参数1：parent是当前点击的item的父控件       父控件  ListView  
	 * 参数2：view是item的视图对象
	 * 参数3：条目的索引
	 * 参数4：ListView自定义的id
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		LogUtil.printlog("onItemClick", parent+"，"+mlit+","+position);
		//判断点击索引是否为0
		if(position == 0){//进入拨号界面
			startActivity(Intent.ACTION_DIAL);
		} else {
			startActivity(TelephonelistActivity.class, position);
		}
		
	}
	
	

}
