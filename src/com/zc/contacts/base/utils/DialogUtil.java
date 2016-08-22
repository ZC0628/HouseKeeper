package com.zc.contacts.base.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;

import com.zc.contacts.activity.TelephonelistActivity;

/**
 * 对话框工具类
 * @author Administrator
 *
 */
public final class DialogUtil {
	public static void show(String title,String message,Context context,String buttext1,
			OnClickListener on1,String buttext2,OnClickListener on2){
		//弹出对话框
		new AlertDialog.Builder(context)
		.setTitle(title)
		.setMessage(message)
		.setPositiveButton(buttext1, on1).setNegativeButton(buttext2, on2)//对话框按钮默认有关闭作用
		.show();
	}
	
	
}
