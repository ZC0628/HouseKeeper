package com.zc.contacts.activity;

import com.zc.contacts.R;
import com.zc.contacts.R.layout;
import com.zc.contacts.R.menu;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.base.utils.MemoryUtil;
import com.zc.contacts.biz.MemoryManager;
import com.zc.contacts.view.ActionBar;
import com.zc.contacts.view.CircleMemory;

import android.os.Bundle;
import android.app.Activity;
import android.text.format.Formatter;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * ����������		
 * @author zc
 */
public class SoftwareManageActivity extends MyActionBarActivity {
	private TextView mphonememory,msdmemory;//��ȡ�ֻ������ڴ��SD���Ŀ����ڴ�
	private ProgressBar mphonePB,msdPB;//�ֻ��ڴ�Ľ�������SD���Ľ�����
	private CircleMemory mcirclememory;//����������ı�ͼ�Ի�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_software_manage);
		
		initview();
		
		//��ȡ�ֻ��ڴ��SD���ڴ�
		long phoneAll = MemoryManager.getphonememory();
		long phoneFree = MemoryManager.getphoneFreememory();
		long SDAll = MemoryManager.getSDmemory();
		long SDFree = MemoryManager.getSDFreememory();
		
		//�û������ֽ�ת������M		formatFileSize(context, number)�ļ��Ĵ�С
		String phoneAllMB = Formatter.formatFileSize(this, phoneAll);
		String phoneFreeMB = Formatter.formatFileSize(this, phoneFree);
		String SDAllMB = Formatter.formatFileSize(this, SDAll);
		String SDFreeMB = Formatter.formatFileSize(this, SDFree);
		
		LogUtil.printlog("�ڴ���Ϣ", phoneAllMB+"����"+phoneFreeMB+"����"+SDAllMB+"����"+SDFreeMB);
		
		//�����ֻ������ڴ�ռ�õĽǶ�
		float phonesweep = (phoneAll - phoneFree) / (float)(phoneAll+ SDAll) * 360;
		float sdsweep = (SDAll - SDFree) / (float)(phoneAll+ SDAll) * 360;
		//����SD�������ڴ�ռ�õĽǶ�
		mcirclememory.setsweep(phonesweep, sdsweep);
		
		//�������� 
		mphonememory.setText("���ã�"+phoneFreeMB+"/"+phoneAllMB);
		msdmemory.setText("���ã�"+SDFreeMB+"/"+SDAllMB);
		//���ý����� 	
		 /*�ֻ����ڴ��ȥʣ���ڴ���ǽ���������ʾ���ڴ�
		*�ó�һ���ٷֱ�  Ȼ����������*/
		 
		mphonePB.setProgress((int)Math.round((phoneAll - phoneFree) / (double)phoneAll*100));
		msdPB.setProgress((int)Math.round((SDAll - SDFree) / (double)SDAll*100));
		
		
	}
	
	

	@Override
	protected void initview() {
		initActionBar(R.drawable.returned, getString(R.string.activity_main_softwaremanage), ActionBar.ID_NULL, on);
		mphonememory = (TextView) findViewById(R.id.phonememory);
		msdmemory = (TextView) findViewById(R.id.sdmemory);
		mphonePB = (ProgressBar) findViewById(R.id.softwaremanage_PhoneprogressBar);
		msdPB = (ProgressBar) findViewById(R.id.softwaremanage_SDprogressBar);
		
		mcirclememory = (CircleMemory) findViewById(R.id.softwaremanage_circle);
	}

	//���ؼ��ļ���
	protected OnClickListener on = new OnClickListener() {
		@Override
		public void onClick(View v) {
			SoftwareManageActivity.this.finish();
		}
	};
	
	
	//��������İ�������
	public void allsoftware(View view){
		Bundle bundle = new Bundle();
		bundle.putString("ActionBarTitle", "�������");
		openActivityExtra(SoftwaremanageListActivity.class, bundle);
	}
	
	
	//ϵͳ����İ�������
	public void systemsoftware(View view){
		Bundle bundle = new Bundle();
		bundle.putString("ActionBarTitle", "ϵͳ���");
		openActivityExtra(SoftwaremanageListActivity.class, bundle);
	}
	
	
	//�û�����İ�������
	public void usersoftware(View view){
		Bundle bundle = new Bundle();
		bundle.putString("ActionBarTitle", "�û����");
		openActivityExtra(SoftwaremanageListActivity.class, bundle);
		}
	
	
}
