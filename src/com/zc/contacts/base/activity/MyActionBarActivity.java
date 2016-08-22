package com.zc.contacts.base.activity;

import android.os.Bundle;
import android.view.View.OnClickListener;

import com.zc.contacts.R;
import com.zc.contacts.view.ActionBar;

/**
 * 导航条的父类：	每次用到导航条的都可以继承这个类调用里面的函数
 * @author Administrator
 *
 */
public abstract class MyActionBarActivity extends BaseActivity {
	private ActionBar mbar;

	@Override
	protected abstract void initview();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	//一旦调用这个函数就把数据设置
	public void initActionBar(int left, String middle, int right,OnClickListener on){
		mbar = (ActionBar) findViewById(R.id.actionbar);
		mbar.initActionBar(left, middle, right,on);
	}
	
	
}
