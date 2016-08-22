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
 * ͨѶ¼������
 * @author zc
 */
public class TelephoneActivity extends BaseActivity implements OnItemClickListener{
	private static final String TAG = "TelephoneActivity";  //�ĸ����ӡ��־ ��ǩ����������ʾ
	private ListView mlit;
	private TelephoneAdapter madapter; 
	public static final String ASSET_DBFILE = "db/commonnum.db"; 
//	public static final String ToFILEPATH = "data/data/com.zc.contacts/files/commonnum.db";
	public static String path;
	private long waittime = 1000;//˫���ȴ�ʱ��  1����
	private long time = 0;//��¼ʱ��
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_telephone);
		//��ʼ�����ݿ���Ϣ
		initDBInfo();
		
		//��ʼ���ؼ�
		initview();
		
	}
	
	/*//�����豸��������������������������back��
	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}*/
	
	
	//�����豸����������һ��
	/* * onKeyDown(int keyCode, KeyEvent event)
	 * ����1��������   ÿ���������ж�Ӧ��һ������
	 * ����2����������Ϣ*/
	 
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		//���������back��
		case KeyEvent.KEYCODE_BACK:
			//˫���˳�Ӧ�ó���     1����˫��
			//��ȡ��ǰʱ�����ֵ
			long curtime = System.currentTimeMillis();
			if (curtime - time <= waittime) {
				//˫��
				TelephoneActivity.this.finish();
			}else {
				//��ʾ
				ToastUtil.show(this, "�ٰ�һ���˳�",0);
			}
			time = curtime;//��¼
			return true;
		}
		//super.onKeyDown(keyCode, event);��ʾ���ø�����Ĭ�ϵ��豸��������ʽ
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void initview() {
		mlit = (ListView) findViewById(R.id.lst_telephone);
		
		madapter = new TelephoneAdapter(this);
		madapter.addDatatoadapter(TelDBReaderManager.gettelephonename(this));//�������е����ݴ��ݸ�������    MVCģʽ 
		//�ֶ����һ�����ݵ�����0��λ��
		madapter.addDatatoadapter(new Telephonename("���ص绰"), 0);
		
		//���õ���¼�	setOnItemClickListener  ������Ŀ�ĵ������
		mlit.setOnItemClickListener(this);
		mlit.setAdapter(madapter);
	}


	private void initDBInfo() {
		path = getFilesDir()+File.separator+"commonnum.db";
		if(!new File(path).exists()){
			//�������ݿ��ļ����ֻ���
			AssetsDBManager.copyAssetsDBFileToPhoneSD(this, 
					ASSET_DBFILE, 
					path);}

		
		//��ȡ����
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
	 * �������ListView�е�ĳ��item��ʱ�����
	 * ����1��parent�ǵ�ǰ�����item�ĸ��ؼ�       ���ؼ�  ListView  
	 * ����2��view��item����ͼ����
	 * ����3����Ŀ������
	 * ����4��ListView�Զ����id
	 */
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		LogUtil.printlog("onItemClick", parent+"��"+mlit+","+position);
		//�жϵ�������Ƿ�Ϊ0
		if(position == 0){//���벦�Ž���
			startActivity(Intent.ACTION_DIAL);
		} else {
			startActivity(TelephonelistActivity.class, position);
		}
		
	}
	
	

}
