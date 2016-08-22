package com.zc.contacts.entity;

import android.graphics.drawable.Drawable;

/**
 * 手机检测的信息
 * @author Administrator
 *
 */
public class PhonetestInfor {
	public Drawable icon;//图片
	public String title;//标题
	public String content;//内容
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
