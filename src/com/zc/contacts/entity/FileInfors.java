package com.zc.contacts.entity;

import android.graphics.drawable.Drawable;

/**
 * 			�ļ���Ϣ
 * @author Administrator
 *
 */
public class FileInfors {
	public String name;//�ļ�����
	public String lastTime;//�ļ�����޸�ʱ��
	public String size;//�ļ���С
	public boolean ischecked;//��ѡ���Ƿ�ѡ��
	public String iconName;//�ļ�����ʹ�õ�ͼƬ����
	public String type;//�ļ�����
	public String path;//�ļ�·��
	public FileInfors(String name, String lastTime, String size,
			boolean ischecked, String iconName, String type, String path) {
		super();
		this.name = name;
		this.lastTime = lastTime;
		this.size = size;
		this.ischecked = ischecked;
		this.iconName = iconName;
		this.type = type;
		this.path = path;
	}
	public FileInfors() {
		super();
	}
	@Override
	public String toString() {
		return "FileInfors [name=" + name + ", lastTime=" + lastTime
				+ ", size=" + size + ", ischecked=" + ischecked + ", iconName="
				+ iconName + ", type=" + type + ", path=" + path + "]";
	}
	
	
	
	
}
