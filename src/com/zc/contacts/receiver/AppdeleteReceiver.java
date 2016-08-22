package com.zc.contacts.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 		广播接收者
 * @author Administrator
 *
 */
public class AppdeleteReceiver extends BroadcastReceiver {
	private MyDataListener mdlistener;
	
	
	//创建
	public interface MyDataListener{
		void getData();
			
	}
	
	public void setMyDataListener(MyDataListener mdlistener){
		this.mdlistener = mdlistener;
	}
	
	
	
	//当有广播来时在此接收		intent发送广播时携带过来的数据
	@Override
	public void onReceive(Context context, Intent intent) {
		//判断接收到的广播类型是否是删除应用类型的
		//getAction()——获得的频道
		if (intent.getAction().equals(intent.ACTION_PACKAGE_REMOVED)) {
			//删除应用之后重新获取数据源
			mdlistener.getData();
		}
		
		
		
	}

}
