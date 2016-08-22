package com.zc.contacts.application;

import java.util.ArrayList;

import com.zc.contacts.entity.FileInfors;

import android.app.Application;
/**
 * 	Application类————应用程序所有界面共享的内容，它的实例只有一份（单例）
 * 要用这个类需要在清单文件aapplication 中注册  android:name="com.zc.contacts.application.Myapplication"
 * 所有界面共享的内容
 * @author Administrator
 *
 */
public class Myapplication extends Application {
	
	private ArrayList<FileInfors> allfiles ;
	private ArrayList<FileInfors> textfiles ;
	private ArrayList<FileInfors> videofiles ;
	private ArrayList<FileInfors> audiofiles ;
	private ArrayList<FileInfors> imagefiles ;
	private ArrayList<FileInfors> zipfiles ;
	private ArrayList<FileInfors> apkfiles ;
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
	
	
	
	
	
}
