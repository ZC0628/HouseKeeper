package com.zc.contacts.base.utils;

import android.content.Context;
import android.media.MediaPlayer;

public final class MusicUtil {
	private static MediaPlayer mmp;
	
	public static void startmusic(Context contetx,int resid,boolean looping){
		if (mmp == null) {//���û�и����ʹ���
			mmp = MediaPlayer.create(contetx,resid);
		}
		//����������ڲ��ţ�������Ч
		if (!mmp.isPlaying()) {
			mmp.start();
		}
	}
	
	public static void pause(){
		if (mmp == null) {
			return;
		}
		if (mmp.isPlaying()) {
			mmp.pause();
		}
	}
	
	public static void stop(){
		if (mmp == null) {
			return;
		}
		//���������ڲ���ʱ������Ҫֹͣ
		if (mmp.isPlaying()) {
			mmp.stop();
			//�ͷ���Դ
			mmp.release();
			mmp = null;
		}
	}
	
	
	
}
