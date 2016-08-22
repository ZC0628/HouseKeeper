package com.zc.contacts.biz;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.base.utils.ToastUtil;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

/**
 * �������ڹ�������assets�ļ����µ����ݿ��ļ�
 * @author Administrator
 *
 */
public class AssetsDBManager {
	public static final String TAG = "AssetsDBManager";
	
	/**����Assets�ļ����µ�db�ļ����ֻ��� 
	 * ����ĳ���ļ�����Ӧ��λ��   
	 * @param context
	 * 		������
	 * @param from
	 * 		Ҫ�����ļ���·��
	 * @param to
	 * 		����������ȥ��·��
	 */
	public static void copyAssetsDBFileToPhoneSD(Context context,String from,String to){
		AssetManager am = context.getAssets();//ͨ��getAssets������ȡAssetManager����
		
		//IO����д�ļ�   �ֽ���
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			InputStream is = am.open(from);
			
			 bis = new BufferedInputStream(is);
			 bos = new BufferedOutputStream(new FileOutputStream(to));
		
		int x;
		byte [] b = new byte [1024 * 2];
		while((x = bis.read(b)) != -1){
			bos.write(b, 0, x);
		}
		bos.flush();//ˢ��
		} catch (IOException e) {
			//���ݿ��ļ�����ʧ����Ϣ��ʾ
			ToastUtil.show(context, "���ݿ��ļ�����ʧ��....", Toast.LENGTH_SHORT);
			
		}finally{
			if(bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					LogUtil.printlog(TAG, "�������ݿ��ļ������쳣");
				}
			}
			if(bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					LogUtil.printlog(TAG, "�������ݿ��ļ������쳣");
				}
			}
		}
		
		
		
	}
	
	
	
}
