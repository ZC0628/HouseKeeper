package com.zc.contacts.entity;

import android.R.drawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * 应用程序列表的信息
 * （多选框——应用程序的图标，名称，包名，版本号）
 * @author Administrator
 *
 */
public class AppInfors {
	public Drawable icon ;//图标
	public String name;//名称
	public String packagename;//包名
	public String version;//版本
	public boolean ischecked;//多选框是否选中
	
	public AppInfors(Drawable icon, String name, String packagename, String version,
			boolean ischecked) {
		super();
		this.icon = icon;
		this.name = name;
		this.packagename = packagename;
		this.version = version;
		this.ischecked = ischecked;
	}

	public AppInfors() {
		super();
	}
	
	
	
	@Override
	public String toString() {
		return "AppInfors [icon=" + icon + ", name=" + name + ", packagename="
				+ packagename + ", version=" + version + ", ischecked="
				+ ischecked + "]";
	}
	
	
	
	
}
