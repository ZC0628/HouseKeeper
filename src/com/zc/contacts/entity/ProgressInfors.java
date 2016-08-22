package com.zc.contacts.entity;

import android.R.drawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * 进程列表的信息
 * （多选框——应用程序的图标，名称，内存，是否是系统进程）
 * @author Administrator
 *
 */
public class ProgressInfors {
	public Drawable icon ;//图标
	public String name;//名称
	public boolean ischecked;//多选框是否选中
	public double memory;//内存
	public boolean isSystem;//是否是系统进程
	public String PackageName;//包名
	
	public ProgressInfors(Drawable icon, String name, boolean ischecked,
			double memory, boolean isSystem) {
		super();
		this.icon = icon;
		this.name = name;
		this.ischecked = ischecked;
		this.memory = memory;
		this.isSystem = isSystem;
		this.PackageName = PackageName;
	}
	
	public ProgressInfors() {
		super();
	}

	@Override
	public String toString() {
		return "ProgressInfors [icon=" + icon + ", name=" + name
				+ ", ischecked=" + ischecked + ", memory=" + memory
				+ ", isSystem=" + isSystem + ", PackageName=" + PackageName
				+ "]";
	}


	
	
	
	
	
	
	
	
	
	
}
