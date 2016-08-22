package com.zc.contacts.activity;

import java.util.ArrayList;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.adapter.TelephoneAdapter;
import com.zc.contacts.adapter.TelephonelistAdapter;
import com.zc.contacts.base.activity.BaseActivity;
import com.zc.contacts.base.utils.DialogUtil;
import com.zc.contacts.base.utils.ToastUtil;
import com.zc.contacts.biz.TelDBReaderManager;
import com.zc.contacts.entity.Telephonename;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;
/**
 *
 * 当点击TelephoneActivity中的item时，跳转到该界面，界面中只有一个全屏的Listview
 * @author Administrator
 *
 */
public class TelephonelistActivity extends BaseActivity {
	private ListView mlit;
	private TelephonelistAdapter mtelephonelistadapter;
	private int mpostion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telephonelist);
		
		initview();//初始化控件
		
		//开始查询表格数据
		if (TelDBReaderManager.isExits()) {
			//根据表格的索引获取
			ArrayList<Telephonename> telelistname = TelDBReaderManager.gettelephonenameitem(this, "table"+mpostion);
			//将集合中的数据添加到适配器
			mtelephonelistadapter.addDatatoadapter(telelistname);
		}else {
			ToastUtil.show(this, "数据库文件 不存在", Toast.LENGTH_SHORT);
		}
		
		mlit.setAdapter(mtelephonelistadapter);
		
		
		//给列表添加条目的监听，点击条目弹出对话框
		mlit.setOnItemClickListener(new OnItemClickListener() {

			@Override//parent——条目的父控件  Listview  view——条目    postion——条目的索引      id——条目的id
			public void onItemClick(AdapterView<?> parent, View view, final int postion,
					long id) {
				
				//弹出对话框
				DialogUtil.show("警告", "是否开始拨打"+mtelephonelistadapter.getitemInfos().get(postion).name+
				"电话？\n\nTEL:"+mtelephonelistadapter.getitemInfos().get(postion).number, TelephonelistActivity.this,
				"拨号", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent it = new Intent(Intent.ACTION_CALL);
				it.setData(Uri.parse("tel:"+mtelephonelistadapter.getitemInfos().get(postion).number));//电话号码转换成Uri （局部类加final）
				startActivity(it);				
				}
				}, "取消", null);
		}
				
		});
		
	}

	@Override
	protected void initview() {
		mlit = (ListView) findViewById(R.id.telephone_list);
		mtelephonelistadapter = new TelephonelistAdapter(this);
		//从TelephoneActivity中传递过来的索引
		 mpostion = getIntent().getIntExtra("index", 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.telephonelist, menu);
		return true;
	}

}
