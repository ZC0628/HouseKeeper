package com.zc.contacts.activity;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.view.ActionBar;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class AboutActivity extends MyActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		
		initview();//初始化
	}

	@Override
	protected void initview() {
		initActionBar(R.drawable.returned, getString(R.string.about_title),ActionBar.ID_NULL, on);
	}
	
	
	//返回的监听
	protected OnClickListener on = new OnClickListener() {
		@Override
		public void onClick(View v) {
			AboutActivity.this.finish();//结束界面
		}
	};
}
