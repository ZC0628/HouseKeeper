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
 * ����֪ͨ�͹ر�֪ͨ�Ĺ�����
 * @author Administrator
 *
 */
public final class NotificationUtil {
	//֪ͨ������
	private NotificationManager mmanager;
	private static final int NOTIFICATION_ID = 1;//���
	private NotificationCompat.Builder ncompat;//��ʡ�ڴ����� ��Ա����
	
	public NotificationUtil (Context context){//��ȡ֪ͨ������
		if (mmanager == null) {
			//��ȡϵͳ����
			mmanager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
		}
	}
	
	
	//��֪ͨ
	public void showNotification(Context context){
		if (ncompat == null) {
			//����֪ͨ	NotificationCompat.Builder������������֪ͨ
			ncompat = new NotificationCompat.Builder(context);
		}
		//��������:״̬������ʱ������
		ncompat.setTicker("��֪ͨ");
		//���ݱ���
		ncompat.setContentTitle("��������");
		ncompat.setContentText("��ڲٵ���");
		//����֪ͨ��    �����������ʾ�𶯵�Ƶ��
		ncompat.setVibrate(new long[]{10,60});
		//���� ������Ϣ
//		ncompat.setContentInfo("��Ϣ");
		//����֪ͨ����ʱ��  System.currentTimeMillis()�����������ͣ�System.currentTimeMillis()+1000  ���֮��1�뷢��
		ncompat.setWhen(System.currentTimeMillis());
		//��ȡBitmap���͵�ͼƬ
		/*
		 * BitmapFactory ͼƬ����
		 * ͼƬ int ����> Bitmap
		 * context.getResources()   ��ȡӦ�ó�����res�ļ����еĶ���
		 */
		/*Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.housekeeper);
		//���ô�ͼ��
		ncompat.setLargeIcon(bitmap);*/
		//����һ����ͼ����
		Intent  it = new Intent(context,MainActivity.class);
		//����һ���ӳ���ͼ��PendingIntent intent
		/*
		 * ����
		 * 1.������
		 * 2.������
		 * 3.��ͼ����
		 * 4.����ж��֪ͨ�������Ƿ���Ҫ����֪ͨ���ݵ�����
		 */
		PendingIntent pintent = PendingIntent.getActivity(context, 1, it, 0);
		
		//����֪ͨ�ĵ���������
		ncompat.setContentIntent(pintent);
		//����Сͼ��,����
		ncompat.setSmallIcon(R.drawable.housekeeper);
		//����build��������notification����
		Notification notif = ncompat.build();
		//ͨ��֪ͨ����������֪ͨ	notify������Ҫ��ȡnotification����
		mmanager.notify(NOTIFICATION_ID, notif);
	}

	
	//�ر�֪ͨ
	public void closeNotification() {
		//ȡ��֪ͨ
		mmanager.cancel(NOTIFICATION_ID);
	}
}
