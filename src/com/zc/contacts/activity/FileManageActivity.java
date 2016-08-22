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
 * 文件管理界面
 * 
 * 使用回调函数来达到Activity类与FileManager类之间通讯的效果
 * @author Administrator
 *
 */
public class FileManageActivity extends MyActionBarActivity {
	
	private TextView filemanagerAllSize;//总的内存
	private TextView filemanagerSizeAll;//全部  （总的内存）
	private TextView filemanagerSizeText;//文档
	private TextView filemanagerSizeVideo;//视频
	private TextView filemanagerSizeAudio;//音频
	private TextView filemanagerSizeImage;//图像
	private TextView filemanagerSizeZip;//压缩包
	private TextView filemanagerSizeApk;//程序包
	private FileManager filemanager;//文件管理器
	private ProgressBar allPro,textPro,videoPro,audioPro,imagePro,zipPro,apkPro;//进度条
	private ImageView alllv,textlv,videolv,audiolv,imagelv,ziplv,apklv;//最右边的箭头
	private LinearLayout filemanageLinear;//线性布局
	private static final int ON_END = 1;//文件搜索结束
	private static final int ON_SUCCESS = 0;
	private String msdpath = Environment.getExternalStorageDirectory().getPath();//sd卡路径——获取外部存储卡的路径
	//为防止出现ANR异常  (Handler) —— 容易犯得错误
	private Handler handler = new Handler(){
		//处理消息
		public void handleMessage(android.os.Message msg) {
			
			//当文件全部搜索完，Activity负责将数据显示在界面上
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
		
		//文件管理器
		filemanager = FileManager.getInstance();
		filemanager.setOnClickListener(listener);
		//子线程
		new Thread(){
			public void run() {
				
				File sdfile = new File(msdpath);
				filemanager.searchSDfile(sdfile,FileManageActivity.this);
				
			};
		}.start();
	}
	
	//因为OnClickListener是定义在内部类里面的，所以要用外部类.内部类调用
	FileManager.OnClickListener listener = new FileManager.OnClickListener() {

		@Override
		public void searchEnd() {
			//发送消息给Handler
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
	
	
	//点击Listview条目跳转到另一个界面 
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
		//当从子界面跳转回来的时候需要修改文字
		startActivityForResult(intent, 0);
	}
	
	//跳转回来修改文字
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//修改占用空间大小
		filemanagerAllSize.setText(filemanager.getAllsize()+"");
		filemanagerSizeAll.setText(filemanager.getAllsize()+"");
		filemanagerSizeText.setText(filemanager.getTextsize()+"");
		filemanagerSizeVideo.setText(filemanager.getVideosize()+"");
		filemanagerSizeAudio.setText(filemanager.getAudiosize()+"");
		filemanagerSizeImage.setText(filemanager.getImagesize()+"");
		filemanagerSizeZip.setText(filemanager.getZipsize()+"");
		filemanagerSizeApk.setText(filemanager.getApksize()+"");
	}
	
	
	
	//清理数据
	@Override
	protected void onDestroy() {
		super.onDestroy();//不能删除
		//每次退回到主界面的时候需要清空下数据
		FileManager fileman = FileManager.getInstance();//获得实例 
		fileman.resetData();
	}
	
	
	//返回键的监听
	protected OnClickListener on = new OnClickListener() {
		@Override
		public void onClick(View v) {
			FileManageActivity.this.finish();
		}
	};
	

}
