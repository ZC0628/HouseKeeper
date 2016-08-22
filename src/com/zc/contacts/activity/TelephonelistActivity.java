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
 * �����TelephoneActivity�е�itemʱ����ת���ý��棬������ֻ��һ��ȫ����Listview
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
		
		initview();//��ʼ���ؼ�
		
		//��ʼ��ѯ�������
		if (TelDBReaderManager.isExits()) {
			//���ݱ���������ȡ
			ArrayList<Telephonename> telelistname = TelDBReaderManager.gettelephonenameitem(this, "table"+mpostion);
			//�������е�������ӵ�������
			mtelephonelistadapter.addDatatoadapter(telelistname);
		}else {
			ToastUtil.show(this, "���ݿ��ļ� ������", Toast.LENGTH_SHORT);
		}
		
		mlit.setAdapter(mtelephonelistadapter);
		
		
		//���б������Ŀ�ļ����������Ŀ�����Ի���
		mlit.setOnItemClickListener(new OnItemClickListener() {

			@Override//parent������Ŀ�ĸ��ؼ�  Listview  view������Ŀ    postion������Ŀ������      id������Ŀ��id
			public void onItemClick(AdapterView<?> parent, View view, final int postion,
					long id) {
				
				//�����Ի���
				DialogUtil.show("����", "�Ƿ�ʼ����"+mtelephonelistadapter.getitemInfos().get(postion).name+
				"�绰��\n\nTEL:"+mtelephonelistadapter.getitemInfos().get(postion).number, TelephonelistActivity.this,
				"����", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent it = new Intent(Intent.ACTION_CALL);
				it.setData(Uri.parse("tel:"+mtelephonelistadapter.getitemInfos().get(postion).number));//�绰����ת����Uri ���ֲ����final��
				startActivity(it);				
				}
				}, "ȡ��", null);
		}
				
		});
		
	}

	@Override
	protected void initview() {
		mlit = (ListView) findViewById(R.id.telephone_list);
		mtelephonelistadapter = new TelephonelistAdapter(this);
		//��TelephoneActivity�д��ݹ���������
		 mpostion = getIntent().getIntExtra("index", 0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.telephonelist, menu);
		return true;
	}

}
