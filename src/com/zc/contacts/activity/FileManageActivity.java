package com.zc.contacts.activity;

import java.io.File;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zc.contacts.R;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.base.utils.FiletypeUtil;
import com.zc.contacts.biz.FileManager;
import com.zc.contacts.view.ActionBar;
/**
 * �ļ��������
 * 
 * ʹ�ûص��������ﵽActivity����FileManager��֮��ͨѶ��Ч��
 * @author Administrator
 *
 */
public class FileManageActivity extends MyActionBarActivity {
	
	private TextView filemanagerAllSize;//�ܵ��ڴ�
	private TextView filemanagerSizeAll;//ȫ��  ���ܵ��ڴ棩
	private TextView filemanagerSizeText;//�ĵ�
	private TextView filemanagerSizeVideo;//��Ƶ
	private TextView filemanagerSizeAudio;//��Ƶ
	private TextView filemanagerSizeImage;//ͼ��
	private TextView filemanagerSizeZip;//ѹ����
	private TextView filemanagerSizeApk;//�����
	private FileManager filemanager;//�ļ�������
	private ProgressBar allPro,textPro,videoPro,audioPro,imagePro,zipPro,apkPro;//������
	private ImageView alllv,textlv,videolv,audiolv,imagelv,ziplv,apklv;//���ұߵļ�ͷ
	private LinearLayout filemanageLinear;//���Բ���
	private static final int ON_END = 1;//�ļ���������
	private static final int ON_SUCCESS = 0;
	private String msdpath = Environment.getExternalStorageDirectory().getPath();//sd��·��������ȡ�ⲿ�洢����·��
	//Ϊ��ֹ����ANR�쳣  (Handler) ���� ���׷��ô���
	private Handler handler = new Handler(){
		//������Ϣ
		public void handleMessage(android.os.Message msg) {
			
			//���ļ�ȫ�������꣬Activity����������ʾ�ڽ�����
			filemanagerAllSize.setText(filemanager.getAllsize()+"");
			filemanagerSizeAll.setText(filemanager.getAllsize()+"");
			filemanagerSizeText.setText(filemanager.getTextsize()+"");
			filemanagerSizeVideo.setText(filemanager.getVideosize()+"");
			filemanagerSizeAudio.setText(filemanager.getAudiosize()+"");
			filemanagerSizeImage.setText(filemanager.getImagesize()+"");
			filemanagerSizeZip.setText(filemanager.getZipsize()+"");
			filemanagerSizeApk.setText(filemanager.getApksize()+"");
			
		};
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_file_manage);
		initview();
		
		//�ļ�������
		filemanager = FileManager.getInstance();
		filemanager.setOnClickListener(listener);
		//���߳�
		new Thread(){
			public void run() {
				
				File sdfile = new File(msdpath);
				filemanager.searchSDfile(sdfile,FileManageActivity.this);
				
			};
		}.start();
	}
	
	//��ΪOnClickListener�Ƕ������ڲ�������ģ�����Ҫ���ⲿ��.�ڲ������
	FileManager.OnClickListener listener = new FileManager.OnClickListener() {

		@Override
		public void searchEnd() {
			//������Ϣ��Handler
			handler.sendEmptyMessage(ON_END);
			
			
		}
	};

	@Override
	protected void initview() {
		initActionBar(R.drawable.returned, getString(R.string.activity_main_filemanage), ActionBar.ID_NULL, on);
		filemanagerAllSize = (TextView) findViewById(R.id.filemanager_allsize);
		filemanagerSizeAll = (TextView) findViewById(R.id.filemanager_size1);
		filemanagerSizeText = (TextView) findViewById(R.id.filemanager_size2);
		filemanagerSizeVideo = (TextView) findViewById(R.id.filemanager_size3);
		filemanagerSizeAudio = (TextView) findViewById(R.id.filemanager_size4);
		filemanagerSizeImage = (TextView) findViewById(R.id.filemanager_size5);
		filemanagerSizeZip = (TextView) findViewById(R.id.filemanager_size6);
		filemanagerSizeApk = (TextView) findViewById(R.id.filemanager_size7);
		filemanageLinear = (LinearLayout) findViewById(R.id.filemanage_linearout);
		
		allPro = (ProgressBar) findViewById(R.id.filemanage_allProbar);
		
		alllv = (ImageView) findViewById(R.id.filemanage_bubiao1);
	}
	
	
	//���Listview��Ŀ��ת����һ������ 
	public void goNextActivity(View view){
		Intent intent = new Intent(this,FileManageShowActivity.class);
		switch (view.getId()) {
		case R.id.filemanage_linearout_all:
			intent.putExtra("data", FiletypeUtil.TYPE_ANY);
			break;

		case R.id.filemanage_linearout_text:
			intent.putExtra("data", FiletypeUtil.TYPE_TXT);
			break;
			
		case R.id.filemanage_linearout_video:
			intent.putExtra("data", FiletypeUtil.TYPE_VIDEO);
			break;	
			
		case R.id.filemanage_linearout_Audio:
			intent.putExtra("data", FiletypeUtil.TYPE_AUDIO);
			break;	
			
		case R.id.filemanage_linearout_Image:
			intent.putExtra("data", FiletypeUtil.TYPE_IMAGE);
			break;	
			
		case R.id.filemanage_linearout_Zip:
			intent.putExtra("data", FiletypeUtil.TYPE_ZIP);
			break;	
			
		case R.id.filemanage_linearout_Apk:
			intent.putExtra("data", FiletypeUtil.TYPE_APK);
			break;	
		}
		//�����ӽ�����ת������ʱ����Ҫ�޸�����
		startActivityForResult(intent, 0);
	}
	
	//��ת�����޸�����
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//�޸�ռ�ÿռ��С
		filemanagerAllSize.setText(filemanager.getAllsize()+"");
		filemanagerSizeAll.setText(filemanager.getAllsize()+"");
		filemanagerSizeText.setText(filemanager.getTextsize()+"");
		filemanagerSizeVideo.setText(filemanager.getVideosize()+"");
		filemanagerSizeAudio.setText(filemanager.getAudiosize()+"");
		filemanagerSizeImage.setText(filemanager.getImagesize()+"");
		filemanagerSizeZip.setText(filemanager.getZipsize()+"");
		filemanagerSizeApk.setText(filemanager.getApksize()+"");
	}
	
	
	
	//��������
	@Override
	protected void onDestroy() {
		super.onDestroy();//����ɾ��
		//ÿ���˻ص��������ʱ����Ҫ���������
		FileManager fileman = FileManager.getInstance();//���ʵ�� 
		fileman.resetData();
	}
	
	
	//���ؼ��ļ���
	protected OnClickListener on = new OnClickListener() {
		@Override
		public void onClick(View v) {
			FileManageActivity.this.finish();
		}
	};
	

}
