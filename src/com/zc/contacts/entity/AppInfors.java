package com.zc.contacts.entity;

import android.R.drawable;
import android.graphics.drawable.Drawable;

/**
 * 
 * Ӧ�ó����б����Ϣ
 * ����ѡ�򡪡�Ӧ�ó����ͼ�꣬���ƣ��������汾�ţ�
 * @author Administrator
 *
 */
public class AppInfors {
	public Drawable icon ;//ͼ��
	public String name;//����
	public String packagename;//����
	public String version;//�汾
	public boolean ischecked;//��ѡ���Ƿ�ѡ��
	
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
