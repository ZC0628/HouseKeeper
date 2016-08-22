package com.zc.contacts.entity;

import android.graphics.drawable.Drawable;

/**
 * 			文件信息
 * @author Administrator
 *
 */
public class FileInfors {
	public String name;//文件名称
	public String lastTime;//文件最后修改时间
	public String size;//文件大小
	public boolean ischecked;//多选框是否被选中
	public String iconName;//文件所所使用的图片名称
	public String type;//文件类型
	public String path;//文件路径
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
