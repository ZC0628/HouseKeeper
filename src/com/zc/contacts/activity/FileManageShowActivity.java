package com.zc.contacts.activity;

import java.io.File;
import java.util.ArrayList;

import com.zc.contacts.R;
import com.zc.contacts.adapter.FileManageshowAdapter;
import com.zc.contacts.application.Myapplication;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.base.utils.FiletypeUtil;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.biz.FileManager;
import com.zc.contacts.entity.FileInfors;
import com.zc.contacts.view.ActionBar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 文件管理界面跳转过来的界面
 * @author Administrator
 *
 */
public class FileManageShowActivity extends MyActionBarActivity {
	private ArrayList<FileInfors> files;//文件管理信息
	private String title,size;//标题都不一样的,占用空间也是不一样的
	private TextView filenumber,filesizes;//文件个数，占用空间
	private FileManager filemanager;//文件管理器
	private ListView filemanageListview;//子界面的条目
	private FileManageshowAdapter fileadapter;//子界面条目的适配器
	private String value;//Intent传递过来的数据
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filemanageshow);
		
		getIntentData();
		
		initview();
	}
	
	
	//获取文件管理界面传递过来的数据
	private void getIntentData() {
		//获取application对象
		Myapplication my = (Myapplication) getApplication();
		 value = getIntent().getStringExtra("data");
		//获取文件管理器
		filemanager = FileManager.getInstance();
		if (value.equals(FiletypeUtil.TYPE_ANY)) {
			//从application中获取数据
			files = my.getAllfiles();
			title = "全部";
			size = filemanager.getAllsize();
		}else if (value.equals(FiletypeUtil.TYPE_TXT)){
			files = my.getTextfiles();
			title = "文档";
			size = filemanager.getTextsize();
		}else if (value.equals(FiletypeUtil.TYPE_VIDEO)){
			files = my.getVideofiles();
			title = "视频";
			size = filemanager.getVideosize();
		}else if (value.equals(FiletypeUtil.TYPE_AUDIO)){
			files = my.getAudiofiles();
			title = "音频";
			size = filemanager.getAudiosize();
		}else if (value.equals(FiletypeUtil.TYPE_IMAGE)){
			files = my.getImagefiles();
			title = "图像";
			size = filemanager.getImagesize();
		}else if (value.equals(FiletypeUtil.TYPE_ZIP)){
			files = my.getZipfiles();
			title = "压缩包";
			size = filemanager.getZipsize();
		}else{
			files = my.getApkfiles();
			title = "程序包";
			size = filemanager.getApksize();
		}
		LogUtil.printlog("data", files.toString());
		
	}



	@Override
	protected void initview() {
		initActionBar(R.drawable.returned, title, ActionBar.ID_NULL, on);
		filenumber = (TextView) findViewById(R.id.filemanageshow_filenumber);
		filesizes = (TextView) findViewById(R.id.filemanageshow_filesizes);
		filemanageListview = (ListView) findViewById(R.id.filemanageshow_Listview);
		
		//设置Listview 中item的点击，根据不同的文件类型选择相应的打开方式  
		filemanageListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String mime = null;
				//获取点击的item所对应的数据
				FileInfors infor = fileadapter.getitemInfos().get(position);
				String name = infor.name;
				//获取后缀名
				int index = name.indexOf(".");
				if (index == -1) {//没有后缀名
					return;
				}
				String end = name.substring(index+1,name.length());
				//从数组中查找属于那种MIMETYPE类型  	索引0：后缀名        索引1： 类型
				for (int x = 0; x < FiletypeUtil.MIME_Table.length; x++) {
					if (end.equals(FiletypeUtil.MIME_Table[x][0])) {
						//判断第0列索引：后缀名，	获取类型
						mime = FiletypeUtil.MIME_Table[x][1];
						break;
					}
				}
				//如果类型没有找到，就结束函数
				if (mime == null) {
					mime = "text/plain";//用记事本打开
				}
				
				String path = infor.path;
				//有了数据类型，使用对应打开方式打开文件   Intent.ACTION_VIEW——给用户显示数据的
				Intent it = new Intent(Intent.ACTION_VIEW);
				 //由于显示数据的类型种类很多，所以要设置数据的类型
				//Uri.parse("file://"+path 需要加协议头     ——file//mnt/sdard...
				it.setDataAndType(Uri.parse("file://"+path), mime);//mime——用什么方式打开
				startActivity(it);
			}
		});
		fileadapter = new FileManageshowAdapter(this);	
		filemanageListview.setAdapter(fileadapter);
		
		//设置文件个数
		filenumber.setText("文件个数："+files.size());
		//设置占用空间
		filesizes.setText("占用空间："+size);
		//将数据添加到适配器中
		fileadapter.addDatatoadapter(files);
		fileadapter.notifyDataSetChanged();
	}
	
	
	//返回键的监听
		protected OnClickListener on = new OnClickListener() {
			@Override
			public void onClick(View v) {
				FileManageShowActivity.this.finish();
			}
		};
		
	//点击删除所选文件	
	public void delefile(View view){
		//需要删除的文件的集合
		ArrayList<FileInfors> needDelete = new ArrayList<FileInfors>();
		//先找到要删除的文件
		ArrayList<FileInfors> infors = fileadapter.getitemInfos();
		for (int x = infors.size()-1; x >= 0; x--) {
			//获取里面每一个数据源
			FileInfors infor = infors.get(x);
			
			if (infor.ischecked) {
				needDelete.add(infor);//把选中的需要删除的文件统一添加到  needDelete的集合中
				infors.remove(x);
			}
		}
		//循环要删除文件的集合
		for (int x = 0; x < needDelete.size(); x++) {
			FileInfors deleteinfor = needDelete.get(x);//获得文件的对象
			String path = deleteinfor.path;//获得文件的路径
			File file = new File(path);//文件转换成File类型的对象
			//获取application对象
			Myapplication my = (Myapplication) getApplication();
			//先通过File对象获取当前文件的大小（字节）
			long size = file.length(); 
			LogUtil.printlog("size", size+"");
			
			long newSize = 0;  
			String newSizes = "";
			
			//将文件删除
			file.delete();
			if (value.equals(FiletypeUtil.TYPE_ANY)) {
				//计算新的空间
				newSize = filemanager.getAll() - size;
				 newSizes = Formatter.formatFileSize(this, newSize);//字节->MB
				 //将FileInfors对象从文件管理器的集合中删除
				 filemanager.getAllfiles().remove(deleteinfor);
				 
				//将计算出的新的设置上去
				filemanager.setAll(newSize);
				filemanager.setAllsize(newSizes);
				
			}else if (value.equals(FiletypeUtil.TYPE_TXT)){
				//计算新的空间
				newSize = filemanager.getText() - size;
				newSizes = Formatter.formatFileSize(this, newSize);
				filemanager.getTextfiles().remove(deleteinfor);
				
				//将计算出的新的设置上去
				filemanager.setText(newSize);
				filemanager.setTextsize(newSizes);
			}else if (value.equals(FiletypeUtil.TYPE_VIDEO)){
				//计算新的空间
				 newSize = filemanager.getVideo() - size;
				 newSizes = Formatter.formatFileSize(this, newSize);
				 filemanager.getVideofiles().remove(deleteinfor);
				 
				//将计算出的新的设置上去
				filemanager.setVideo(newSize);
				filemanager.setVideosize(newSizes);
			}else if (value.equals(FiletypeUtil.TYPE_AUDIO)){
				//计算新的空间
				 newSize = filemanager.getAudio() - size;
				 newSizes = Formatter.formatFileSize(this, newSize);
				 filemanager.getAudiofiles().remove(deleteinfor);
				 
				//将计算出的新的设置上去
				filemanager.setAudio(newSize);
				filemanager.setAudiosize(newSizes);
			}else if (value.equals(FiletypeUtil.TYPE_IMAGE)){
				//计算新的空间
				 newSize = filemanager.getImage() - size;
				 newSizes = Formatter.formatFileSize(this, newSize);
				 filemanager.getImagefiles().remove(deleteinfor);
				 
				//将计算出的新的设置上去
				filemanager.setImage(newSize);
				filemanager.setImagesize(newSizes);
			}else if (value.equals(FiletypeUtil.TYPE_ZIP)){
				//计算新的空间
				newSize = filemanager.getZip() - size;
				newSizes = Formatter.formatFileSize(this, newSize);
				filemanager.getZipfiles().remove(deleteinfor);
				//将计算出的新的设置上去
				filemanager.setZip(newSize);
				filemanager.setZipsize(newSizes);
			}else{
				//计算新的空间
				 newSize = filemanager.getApk() - size;
				 newSizes = Formatter.formatFileSize(this, newSize);
				 filemanager.getApkfiles().remove(deleteinfor);
				//将计算出的新的设置上去
				filemanager.setApk(newSize);
				filemanager.setApksize(newSizes);
			}
			//设置文件个数
			filenumber.setText("文件个数："+infors.size());
			//设置占用空间
			filesizes.setText("占用空间："+newSizes);
			
		}
		
		fileadapter.notifyDataSetChanged();
	}
		
	
	
}
