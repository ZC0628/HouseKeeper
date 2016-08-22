package com.zc.contacts.base.utils;

import com.zc.contacts.biz.MemoryManager;

import android.content.Context;
import android.text.format.Formatter;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * �ڴ�ת��������
 * @author Administrator
 *
 */
public final class MemoryUtil {
	private static TextView mphonememory,msdmemory;//��ȡ�ֻ������ڴ��SD���Ŀ����ڴ�
	private static ProgressBar mphonePB,msdPB;//�ֻ��ڴ�Ľ�������SD���Ľ�����
	
	public static void phoneSDmemory(Context context){
		
				long phoneAll = MemoryManager.getphonememory();
				long phoneFree = MemoryManager.getphoneFreememory();
				long SDAll = MemoryManager.getSDmemory();
				long SDFree = MemoryManager.getSDFreememory();
				
				//�û������ֽ�ת������M		formatFileSize(context, number)�ļ��Ĵ�С
				String phoneAllMB = Formatter.formatFileSize(context, phoneAll);
				String phoneFreeMB = Formatter.formatFileSize(context, phoneFree);
				String SDAllMB = Formatter.formatFileSize(context, SDAll);
				String SDFreeMB = Formatter.formatFileSize(context, SDFree);
				
				LogUtil.printlog("�ڴ���Ϣ", phoneAllMB+"����"+phoneFreeMB+"����"+SDAllMB+"����"+SDFreeMB);
				
				//�������� 
				mphonememory.setText("���� :"+phoneFreeMB+"/"+phoneAllMB);
				msdmemory.setText("���� :"+SDFreeMB+"/"+SDAllMB);
				//���ý����� 
				/* �ֻ����ڴ��ȥʣ���ڴ���ǽ���������ʾ���ڴ�
				*�ó�һ���ٷֱ�  Ȼ����������
				 * 
				 */
				mphonePB.setProgress((int)Math.round((phoneAll - phoneFree) / (double)phoneAll*100));
				msdPB.setProgress((int)Math.round((SDAll - SDFree) / (double)SDAll*100));
		
	}
	
	
}
