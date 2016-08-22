package com.zc.contacts.service;

import com.zc.contacts.R;
import com.zc.contacts.base.utils.MusicUtil;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
/**
 * 引导界面播放音乐的服务
 * @author Administrator
 *
 */
public class MusicService extends Service {
	private MediaPlayer mmp;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
	}

	//开启服务
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		MusicUtil.startmusic(getApplicationContext(), R.raw.alttiestory, true);
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	//服务结束
	@Override
	public void onDestroy() {
		MusicUtil.stop();
		super.onDestroy();
	}

}
