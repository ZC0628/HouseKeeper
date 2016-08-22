package com.zc.contacts.activity;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.view.GifView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
/**
 *   Gif¶¯Ì¬Í¼
 * @author Administrator
 *
 */
public class CleanerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(new GifView(this, R.drawable.clean));
		
		
	}
}
