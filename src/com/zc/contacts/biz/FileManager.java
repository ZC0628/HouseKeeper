package com.zc.contacts.biz;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import com.zc.contacts.application.Myapplication;
import com.zc.contacts.base.utils.FiletypeUtil;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.entity.FileInfors;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.text.format.Formatter;

/**
 * 文件管理器
 * 查找SD卡中所有的文件
 * @author Administrator
 *
 * 
 */
public class FileManager {
	/**
	 * 由于FileManageActivity和FileManageShowActivity两个界面都想用 FileManager对象
	 * 就可以把FileManager做成单例模式
	 */
	private FileManager(){};//1.先构造器屏蔽，私有化
	private static FileManager filemanage;//2.在本类中创建一个对像
	//3.将建好的对象提供给外界访问（通过函数）
	public static FileManager getInstance(){
		//4.在函数中返回当前类的对象,定义成静态函数，返回的对象也必须是静态的
		//由于多个线程同时访问会每次都new 生成对象，降低性能  
		//而使用加锁可以解决多线程的安全问题，生成实例，提高效率
		if (filemanage == null) {
			synchronized (FileManager.class) {
				if (filemanage == null) {
					filemanage = new FileManager();
				}
			}
		}
		return filemanage;
	}
	
	
	private OnClickListener Mylistener;
	private String allsize;//所有文件总共大小
	public String getAllsize() {
		return allsize;
	}
		//定义个个文件的变量——由于递归会又调用一次自己，会变成0，所以要声明在成员变量上
		private long all = 0;
		private long text = 0;
		private long video = 0;
		private long audio = 0;
		private long image = 0;
		private long zip = 0;
		private long apk = 0;
	
	public long getAll() {
			return all;
		}

		public void setAll(long all) {
			this.all = all;
		}

		public long getText() {
			return text;
		}

		public void setText(long text) {
			this.text = text;
		}

		public long getVideo() {
			return video;
		}

		public void setVideo(long video) {
			this.video = video;
		}

		public long getAudio() {
			return audio;
		}

		public void setAudio(long audio) {
			this.audio = audio;
		}

		public long getImage() {
			return image;
		}

		public void setImage(long image) {
			this.image = image;
		}

		public long getZip() {
			return zip;
		}

		public void setZip(long zip) {
			this.zip = zip;
		}

		public long getApk() {
			return apk;
		}

		public void setApk(long apk) {
			this.apk = apk;
		}
	private String textsize;//文档总共大小
	private String videosize;//视频总共大小
	private String audiosize;//音频总共大小
	private String imagesize;//图像总共大小
	private String zipsize;//压缩包总共大小
	private String apksize;//程序包总共大小
	public String getTextsize() {
		return textsize;
	}

	public String getVideosize() {
		return videosize;
	}

	public String getAudiosize() {
		return audiosize;
	}

	public String getImagesize() {
		return imagesize;
	}

	public String getZipsize() {
		return zipsize;
	}

	public String getApksize() {
		return apksize;
	}
	
	public void setAllsize(String allsize) {
		this.allsize = allsize;
	}

	public void setTextsize(String textsize) {
		this.textsize = textsize;
	}

	public void setVideosize(String videosize) {
		this.videosize = videosize;
	}

	public void setAudiosize(String audiosize) {
		this.audiosize = audiosize;
	}

	public void setImagesize(String imagesize) {
		this.imagesize = imagesize;
	}

	public void setZipsize(String zipsize) {
		this.zipsize = zipsize;
	}

	public void setApksize(String apksize) {
		this.apksize = apksize;
	}

	/**
	 * 重置数据
	 * @return
	 */
	public void resetData(){
		//数据清空
		textsize = null;
		videosize = null;
		audiosize = null;
		imagesize = null;
		zipsize = null;
		apksize = null;
		//集合清空
		allfiles.clear();
		textfiles.clear();
		videofiles.clear();
		audiofiles.clear();
		imagefiles.clear();
		zipfiles.clear();
		apkfiles.clear();
		
		 all = 0;
		 text = 0;
		 video = 0;
		 audio = 0;
		 image = 0;
		 zip = 0;
		 apk = 0;
	}
	
	//存放所有文件信息的集合
	private ArrayList<FileInfors> allfiles = new ArrayList<FileInfors>();
	private ArrayList<FileInfors> textfiles = new ArrayList<FileInfors>();
	private ArrayList<FileInfors> videofiles = new ArrayList<FileInfors>();
	private ArrayList<FileInfors> audiofiles = new ArrayList<FileInfors>();
	private ArrayList<FileInfors> imagefiles = new ArrayList<FileInfors>();
	private ArrayList<FileInfors> zipfiles = new ArrayList<FileInfors>();
	private ArrayList<FileInfors> apkfiles = new ArrayList<FileInfors>();
	
	public OnClickListener getMylistener() {
		return Mylistener;
	}

	public void setMylistener(OnClickListener mylistener) {
		Mylistener = mylistener;
	}

	public ArrayList<FileInfors> getAllfiles() {
		return allfiles;
	}

	public void setAllfiles(ArrayList<FileInfors> allfiles) {
		this.allfiles = allfiles;
	}

	public ArrayList<FileInfors> getTextfiles() {
		return textfiles;
	}

	public void setTextfiles(ArrayList<FileInfors> textfiles) {
		this.textfiles = textfiles;
	}

	public ArrayList<FileInfors> getVideofiles() {
		return videofiles;
	}

	public void setVideofiles(ArrayList<FileInfors> videofiles) {
		this.videofiles = videofiles;
	}

	public ArrayList<FileInfors> getAudiofiles() {
		return audiofiles;
	}

	public void setAudiofiles(ArrayList<FileInfors> audiofiles) {
		this.audiofiles = audiofiles;
	}

	public ArrayList<FileInfors> getImagefiles() {
		return imagefiles;
	}

	public void setImagefiles(ArrayList<FileInfors> imagefiles) {
		this.imagefiles = imagefiles;
	}

	public ArrayList<FileInfors> getZipfiles() {
		return zipfiles;
	}

	public void setZipfiles(ArrayList<FileInfors> zipfiles) {
		this.zipfiles = zipfiles;
	}

	public ArrayList<FileInfors> getApkfiles() {
		return apkfiles;
	}

	public void setApkfiles(ArrayList<FileInfors> apkfiles) {
		this.apkfiles = apkfiles;
	}

	//定义一个接口
	public interface OnClickListener{
		void searchEnd();//结束搜索
		
	}

	
	public void setOnClickListener(OnClickListener on){
		this.Mylistener = on;
		
	}
	
	/**
	 * 搜索SD卡中所有的文件
	 */
	public void searchSDfile(File filepath,Context context) {
		
		//开始搜索
		File[] sdFiles = filepath.listFiles();
		//由于listFiles有可能返回一个空的，一循环的话就会空指针了，所以要做一个安全判断。判断数组是否为空
		if (sdFiles == null) {
			return;//结束函数
		}
		for (int x = 0; x < sdFiles.length; x++) {
			File file = sdFiles[x];//循环，先获的里面的每一个数据
			if (file.isDirectory()) {//文件夹
				//递归，如果是文件夹就再调用自己的函数searchSDfile
				searchSDfile(file,context);
				
			}else {//文件
				//判断文件的大小  ——如果文件小于等于0的，就结束
				if (file.length() <= 0) {
					continue;//结束本次循环的文件， +1 ，继续下次循环判断文件
				}
				
				FileInfors fileinfor = new FileInfors();
				//获取文件大小
				long size = file.length();
				
				all += size;//所有文件总大小
				//超出1024字节的自动转换成KB，超过1000KB的转换成MB
				String filesize = Formatter.formatFileSize(context, size);
				//获取文件名称
				String filename = file.getName();
				
				//获取文件的后缀名    从最后开始——到 .
				int index = filename.lastIndexOf(".");//test.txt
				// 从.的索引后面一个开始剪切到最后。（.的索引是4的话，就是.后面一个5开始剪切到最后lengh()）
				String end = filename.substring(index+1, filename.length());
				LogUtil.printlog("end", end);
				//根据文件的后缀名判断文件属于什么类型的
				String [] iconAndType = FiletypeUtil.getFileType(end);
				
				//获取文件最后修改的日期
				String lastTime = castTime(file.lastModified());
				//添加属性
				fileinfor.name  = filename;
				fileinfor.size = filesize;
				fileinfor.lastTime = lastTime;
				fileinfor.iconName = iconAndType[0];
				fileinfor.type = iconAndType[1];
				fileinfor.path = file.getPath();
				//根据文件类型判断
				if (iconAndType[1].equals(FiletypeUtil.TYPE_TXT)) {
					text += size;
					textfiles.add(fileinfor);//添加数据
					
				}else if (iconAndType[1].equals(FiletypeUtil.TYPE_VIDEO)) {
					video += size;
					videofiles.add(fileinfor);
					
				}else if (iconAndType[1].equals(FiletypeUtil.TYPE_AUDIO)) {
					audio += size;
					audiofiles.add(fileinfor);
					
				}else if (iconAndType[1].equals(FiletypeUtil.TYPE_IMAGE)) {
					LogUtil.printlog("TYPE_IMAGE", imagesize+"");
					image += size;
					imagefiles.add(fileinfor);
					
				}else if (iconAndType[1].equals(FiletypeUtil.TYPE_ZIP)) {
					zipfiles.add(fileinfor);
					
				}else{
					apk += size;
					apkfiles.add(fileinfor);
				}
				
				allfiles.add(fileinfor);//所有文件添加到集合中
				LogUtil.printlog("fileinfors",filesize+","+filename+","+lastTime);
			}
		}
		
		 this.allsize = Formatter.formatFileSize(context, all);//转换完赋值给成员变量上的allsize
		 this.textsize = Formatter.formatFileSize(context, text);
		 this.videosize = Formatter.formatFileSize(context, video);
		 this.audiosize = Formatter.formatFileSize(context, audio);
		 this.imagesize = Formatter.formatFileSize(context, image);
		 this.zipsize = Formatter.formatFileSize(context, zip);
		 this.apksize = Formatter.formatFileSize(context, apk);
		 
		 //添加完数据后将数据保存到application中
		 Activity act = (Activity) context;
		 Myapplication myapp = (Myapplication) act.getApplication();
		 myapp.setAllfiles(allfiles);
		 myapp.setTextfiles(textfiles);
		 myapp.setVideofiles(videofiles);
		 myapp.setAudiofiles(audiofiles);
		 myapp.setImagefiles(imagefiles);
		 myapp.setZipfiles(zipfiles);
		 myapp.setApkfiles(apkfiles);
		 
		//搜索结束回复Activity
		Mylistener.searchEnd();
	}
	
	/**
	 * 将毫秒值转换为良好的时间格式
	 */
	public static String castTime(long time){
		Calendar calendar =Calendar.getInstance();
		calendar.setTimeInMillis(time);
		Date date = calendar.getTime();
		SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String stringdate = simple.format(date);
		return stringdate;
		
	}
	
}	
