package com.zc.contacts.entity;

import android.graphics.drawable.Drawable;

/**
 * �ֻ�������Ϣ
 * @author Administrator
 *
 */
public class PhonetestInfor {
	public Drawable icon;//ͼƬ
	public String title;//����
	public String content;//����
	public PhonetestInfor(Drawable icon, String title, String content) {
		super();
		this.icon = icon;
		this.title = title;
		this.content = content;
	}
	public PhonetestInfor() {
		super();
	}
	@Override
	public String toString() {
		return "PhonetestInfor [icon=" + icon + ", title=" + title
				+ ", content=" + content + "]";
	}
	
	
	
}
