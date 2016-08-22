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
 * �ļ�������
 * ����SD�������е��ļ�
 * @author Administrator
 *
 * 
 */
public class FileManager {
	/**
	 * ����FileManageActivity��FileManageShowActivity�������涼���� FileManager����
	 * �Ϳ��԰�FileManager���ɵ���ģʽ
	 */
	private FileManager(){};//1.�ȹ��������Σ�˽�л�
	private static FileManager filemanage;//2.�ڱ����д���һ������
	//3.�����õĶ����ṩ�������ʣ�ͨ��������
	public static FileManager getInstance(){
		//4.�ں����з��ص�ǰ��Ķ���,����ɾ�̬���������صĶ���Ҳ�����Ǿ�̬��
		//���ڶ���߳�ͬʱ���ʻ�ÿ�ζ�new ���ɶ��󣬽�������  
		//��ʹ�ü������Խ�����̵߳İ�ȫ���⣬����ʵ�������Ч��
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
	private String allsize;//�����ļ��ܹ���С
	public String getAllsize() {
		return allsize;
	}
		//��������ļ��ı����������ڵݹ���ֵ���һ���Լ�������0������Ҫ�����ڳ�Ա������
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
	private String textsize;//�ĵ��ܹ���С
	private String videosize;//��Ƶ�ܹ���С
	private String audiosize;//��Ƶ�ܹ���С
	private String imagesize;//ͼ���ܹ���С
	private String zipsize;//ѹ�����ܹ���С
	private String apksize;//������ܹ���С
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
	 * ��������
	 * @return
	 */
	public void resetData(){
		//�������
		textsize = null;
		videosize = null;
		audiosize = null;
		imagesize = null;
		zipsize = null;
		apksize = null;
		//�������
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
	
	//��������ļ���Ϣ�ļ���
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

	//����һ���ӿ�
	public interface OnClickListener{
		void searchEnd();//��������
		
	}

	
	public void setOnClickListener(OnClickListener on){
		this.Mylistener = on;
		
	}
	
	/**
	 * ����SD�������е��ļ�
	 */
	public void searchSDfile(File filepath,Context context) {
		
		//��ʼ����
		File[] sdFiles = filepath.listFiles();
		//����listFiles�п��ܷ���һ���յģ�һѭ���Ļ��ͻ��ָ���ˣ�����Ҫ��һ����ȫ�жϡ��ж������Ƿ�Ϊ��
		if (sdFiles == null) {
			return;//��������
		}
		for (int x = 0; x < sdFiles.length; x++) {
			File file = sdFiles[x];//ѭ�����Ȼ�������ÿһ������
			if (file.isDirectory()) {//�ļ���
				//�ݹ飬������ļ��о��ٵ����Լ��ĺ���searchSDfile
				searchSDfile(file,context);
				
			}else {//�ļ�
				//�ж��ļ��Ĵ�С  ��������ļ�С�ڵ���0�ģ��ͽ���
				if (file.length() <= 0) {
					continue;//��������ѭ�����ļ��� +1 �������´�ѭ���ж��ļ�
				}
				
				FileInfors fileinfor = new FileInfors();
				//��ȡ�ļ���С
				long size = file.length();
				
				all += size;//�����ļ��ܴ�С
				//����1024�ֽڵ��Զ�ת����KB������1000KB��ת����MB
				String filesize = Formatter.formatFileSize(context, size);
				//��ȡ�ļ�����
				String filename = file.getName();
				
				//��ȡ�ļ��ĺ�׺��    �����ʼ������ .
				int index = filename.lastIndexOf(".");//test.txt
				// ��.����������һ����ʼ���е���󡣣�.��������4�Ļ�������.����һ��5��ʼ���е����lengh()��
				String end = filename.substring(index+1, filename.length());
				LogUtil.printlog("end", end);
				//�����ļ��ĺ�׺���ж��ļ�����ʲô���͵�
				String [] iconAndType = FiletypeUtil.getFileType(end);
				
				//��ȡ�ļ�����޸ĵ�����
				String lastTime = castTime(file.lastModified());
				//�������
				fileinfor.name  = filename;
				fileinfor.size = filesize;
				fileinfor.lastTime = lastTime;
				fileinfor.iconName = iconAndType[0];
				fileinfor.type = iconAndType[1];
				fileinfor.path = file.getPath();
				//�����ļ������ж�
				if (iconAndType[1].equals(FiletypeUtil.TYPE_TXT)) {
					text += size;
					textfiles.add(fileinfor);//�������
					
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
				
				allfiles.add(fileinfor);//�����ļ���ӵ�������
				LogUtil.printlog("fileinfors",filesize+","+filename+","+lastTime);
			}
		}
		
		 this.allsize = Formatter.formatFileSize(context, all);//ת���긳ֵ����Ա�����ϵ�allsize
		 this.textsize = Formatter.formatFileSize(context, text);
		 this.videosize = Formatter.formatFileSize(context, video);
		 this.audiosize = Formatter.formatFileSize(context, audio);
		 this.imagesize = Formatter.formatFileSize(context, image);
		 this.zipsize = Formatter.formatFileSize(context, zip);
		 this.apksize = Formatter.formatFileSize(context, apk);
		 
		 //��������ݺ����ݱ��浽application��
		 Activity act = (Activity) context;
		 Myapplication myapp = (Myapplication) act.getApplication();
		 myapp.setAllfiles(allfiles);
		 myapp.setTextfiles(textfiles);
		 myapp.setVideofiles(videofiles);
		 myapp.setAudiofiles(audiofiles);
		 myapp.setImagefiles(imagefiles);
		 myapp.setZipfiles(zipfiles);
		 myapp.setApkfiles(apkfiles);
		 
		//���������ظ�Activity
		Mylistener.searchEnd();
	}
	
	/**
	 * ������ֵת��Ϊ���õ�ʱ���ʽ
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
