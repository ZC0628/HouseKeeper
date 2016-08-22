package com.zc.contacts.base.utils;

import com.zc.contacts.R;
import com.zc.contacts.activity.MainActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

/**
 * 发送通知和关闭通知的工具类
 * @author Administrator
 *
 */
public final class NotificationUtil {
	//通知管理器
	private NotificationManager mmanager;
	private static final int NOTIFICATION_ID = 1;//编号
	private NotificationCompat.Builder ncompat;//节省内存做成 成员变量
	
	public NotificationUtil (Context context){//获取通知管理器
		if (mmanager == null) {
			//获取系统服务
			mmanager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		}
	}
	
	
	//打开通知
	public void showNotification(Context context){
		if (ncompat == null) {
			//创建通知	NotificationCompat.Builder——用来创建通知
			ncompat = new NotificationCompat.Builder(context);
		}
		//设置属性:状态栏弹出时的文字
		ncompat.setTicker("新通知");
		//内容标题
		ncompat.setContentTitle("天气不错");
		ncompat.setContentText("你节操掉了");
		//设置通知震动    参数的数组表示震动的频率
		ncompat.setVibrate(new long[]{10,60});
		//设置 内容信息
//		ncompat.setContentInfo("信息");
		//设置通知发送时间  System.currentTimeMillis()——立即发送，System.currentTimeMillis()+1000  点击之后1秒发送
		ncompat.setWhen(System.currentTimeMillis());
		//获取Bitmap类型的图片
		/*
		 * BitmapFactory 图片工厂
		 * 图片 int ——> Bitmap
		 * context.getResources()   获取应用程序中res文件夹中的对象
		 */
		/*Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.housekeeper);
		//设置大图标
		ncompat.setLargeIcon(bitmap);*/
		//创建一个意图对象
		Intent  it = new Intent(context,MainActivity.class);
		//创建一个延迟意图，PendingIntent intent
		/*
		 * 参数
		 * 1.上下文
		 * 2.请求码
		 * 3.意图对象
		 * 4.如果有多个通知对象，则是否需要更新通知内容等数据
		 */
		PendingIntent pintent = PendingIntent.getActivity(context, 1, it, 0);
		
		//设置通知的点击，必须的
		ncompat.setContentIntent(pintent);
		//设置小图标,必须
		ncompat.setSmallIcon(R.drawable.housekeeper);
		//调用build（）创建notification对象
		Notification notif = ncompat.build();
		//通过通知管理器发送通知	notify函数需要获取notification对象
		mmanager.notify(NOTIFICATION_ID, notif);
	}

	
	//关闭通知
	public void closeNotification() {
		//取消通知
		mmanager.cancel(NOTIFICATION_ID);
	}
}
