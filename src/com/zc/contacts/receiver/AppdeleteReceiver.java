package com.zc.contacts.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
/**
 * 		�㲥������
 * @author Administrator
 *
 */
public class AppdeleteReceiver extends BroadcastReceiver {
	private MyDataListener mdlistener;
	
	
	//����
	public interface MyDataListener{
		void getData();
			
	}
	
	public void setMyDataListener(MyDataListener mdlistener){
		this.mdlistener = mdlistener;
	}
	
	
	
	//���й㲥��ʱ�ڴ˽���		intent���͹㲥ʱЯ������������
	@Override
	public void onReceive(Context context, Intent intent) {
		//�жϽ��յ��Ĺ㲥�����Ƿ���ɾ��Ӧ�����͵�
		//getAction()������õ�Ƶ��
		if (intent.getAction().equals(intent.ACTION_PACKAGE_REMOVED)) {
			//ɾ��Ӧ��֮�����»�ȡ����Դ
			mdlistener.getData();
		}
		
		
		
	}

}
