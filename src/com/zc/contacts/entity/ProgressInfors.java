package com.zc.contacts.entity;

import android.R.drawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * �����б����Ϣ
 * ����ѡ�򡪡�Ӧ�ó����ͼ�꣬���ƣ��ڴ棬�Ƿ���ϵͳ���̣�
 * @author Administrator
 *
 */
public class ProgressInfors {
	public Drawable icon ;//ͼ��
	public String name;//����
	public boolean ischecked;//��ѡ���Ƿ�ѡ��
	public double memory;//�ڴ�
	public boolean isSystem;//�Ƿ���ϵͳ����
	public String PackageName;//����
	
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
