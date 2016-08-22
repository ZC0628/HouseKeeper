package com.zc.contacts.base.activity;

import android.os.Bundle;
import android.view.View.OnClickListener;

import com.zc.contacts.R;
import com.zc.contacts.view.ActionBar;

/**
 * �������ĸ��ࣺ	ÿ���õ��������Ķ����Լ̳�������������ĺ���
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
	
	//һ��������������Ͱ���������
	public void initActionBar(int left, String middle, int right,OnClickListener on){
		mbar = (ActionBar) findViewById(R.id.actionbar);
		mbar.initActionBar(left, middle, right,on);
	}
	
	
}
