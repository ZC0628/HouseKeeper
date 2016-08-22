package com.zc.contacts.base.utils;

import android.content.Context;
import android.media.MediaPlayer;

public final class MusicUtil {
	private static MediaPlayer mmp;
	
	public static void startmusic(Context contetx,int resid,boolean looping){
		if (mmp == null) {//如果没有歌曲就创建
			mmp = MediaPlayer.create(contetx,resid);
		}
		//如果歌曲正在播放，则点击无效
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
		//当歌曲正在播放时，才需要停止
		if (mmp.isPlaying()) {
			mmp.stop();
			//释放资源
			mmp.release();
			mmp = null;
		}
	}
	
	
	
}
