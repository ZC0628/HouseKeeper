package com.zc.contacts.activity;

import java.io.File;
import java.util.ArrayList;

import com.zc.contacts.R;
import com.zc.contacts.adapter.FileManageshowAdapter;
import com.zc.contacts.application.Myapplication;
import com.zc.contacts.base.activity.MyActionBarActivity;
import com.zc.contacts.base.utils.FiletypeUtil;
import com.zc.contacts.base.utils.LogUtil;
import com.zc.contacts.biz.FileManager;
import com.zc.contacts.entity.FileInfors;
import com.zc.contacts.view.ActionBar;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
/**
 * �ļ����������ת�����Ľ���
 * @author Administrator
 *
 */
public class FileManageShowActivity extends MyActionBarActivity {
	private ArrayList<FileInfors> files;//�ļ�������Ϣ
	private String title,size;//���ⶼ��һ����,ռ�ÿռ�Ҳ�ǲ�һ����
	private TextView filenumber,filesizes;//�ļ�������ռ�ÿռ�
	private FileManager filemanager;//�ļ�������
	private ListView filemanageListview;//�ӽ������Ŀ
	private FileManageshowAdapter fileadapter;//�ӽ�����Ŀ��������
	private String value;//Intent���ݹ���������
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filemanageshow);
		
		getIntentData();
		
		initview();
	}
	
	
	//��ȡ�ļ�������洫�ݹ���������
	private void getIntentData() {
		//��ȡapplication����
		Myapplication my = (Myapplication) getApplication();
		 value = getIntent().getStringExtra("data");
		//��ȡ�ļ�������
		filemanager = FileManager.getInstance();
		if (value.equals(FiletypeUtil.TYPE_ANY)) {
			//��application�л�ȡ����
			files = my.getAllfiles();
			title = "ȫ��";
			size = filemanager.getAllsize();
		}else if (value.equals(FiletypeUtil.TYPE_TXT)){
			files = my.getTextfiles();
			title = "�ĵ�";
			size = filemanager.getTextsize();
		}else if (value.equals(FiletypeUtil.TYPE_VIDEO)){
			files = my.getVideofiles();
			title = "��Ƶ";
			size = filemanager.getVideosize();
		}else if (value.equals(FiletypeUtil.TYPE_AUDIO)){
			files = my.getAudiofiles();
			title = "��Ƶ";
			size = filemanager.getAudiosize();
		}else if (value.equals(FiletypeUtil.TYPE_IMAGE)){
			files = my.getImagefiles();
			title = "ͼ��";
			size = filemanager.getImagesize();
		}else if (value.equals(FiletypeUtil.TYPE_ZIP)){
			files = my.getZipfiles();
			title = "ѹ����";
			size = filemanager.getZipsize();
		}else{
			files = my.getApkfiles();
			title = "�����";
			size = filemanager.getApksize();
		}
		LogUtil.printlog("data", files.toString());
		
	}



	@Override
	protected void initview() {
		initActionBar(R.drawable.returned, title, ActionBar.ID_NULL, on);
		filenumber = (TextView) findViewById(R.id.filemanageshow_filenumber);
		filesizes = (TextView) findViewById(R.id.filemanageshow_filesizes);
		filemanageListview = (ListView) findViewById(R.id.filemanageshow_Listview);
		
		//����Listview ��item�ĵ�������ݲ�ͬ���ļ�����ѡ����Ӧ�Ĵ򿪷�ʽ  
		filemanageListview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String mime = null;
				//��ȡ�����item����Ӧ������
				FileInfors infor = fileadapter.getitemInfos().get(position);
				String name = infor.name;
				//��ȡ��׺��
				int index = name.indexOf(".");
				if (index == -1) {//û�к�׺��
					return;
				}
				String end = name.substring(index+1,name.length());
				//�������в�����������MIMETYPE����  	����0����׺��        ����1�� ����
				for (int x = 0; x < FiletypeUtil.MIME_Table.length; x++) {
					if (end.equals(FiletypeUtil.MIME_Table[x][0])) {
						//�жϵ�0����������׺����	��ȡ����
						mime = FiletypeUtil.MIME_Table[x][1];
						break;
					}
				}
				//�������û���ҵ����ͽ�������
				if (mime == null) {
					mime = "text/plain";//�ü��±���
				}
				
				String path = infor.path;
				//�����������ͣ�ʹ�ö�Ӧ�򿪷�ʽ���ļ�   Intent.ACTION_VIEW�������û���ʾ���ݵ�
				Intent it = new Intent(Intent.ACTION_VIEW);
				 //������ʾ���ݵ���������ܶ࣬����Ҫ�������ݵ�����
				//Uri.parse("file://"+path ��Ҫ��Э��ͷ     ����file//mnt/sdard...
				it.setDataAndType(Uri.parse("file://"+path), mime);//mime������ʲô��ʽ��
				startActivity(it);
			}
		});
		fileadapter = new FileManageshowAdapter(this);	
		filemanageListview.setAdapter(fileadapter);
		
		//�����ļ�����
		filenumber.setText("�ļ�������"+files.size());
		//����ռ�ÿռ�
		filesizes.setText("ռ�ÿռ䣺"+size);
		//��������ӵ���������
		fileadapter.addDatatoadapter(files);
		fileadapter.notifyDataSetChanged();
	}
	
	
	//���ؼ��ļ���
		protected OnClickListener on = new OnClickListener() {
			@Override
			public void onClick(View v) {
				FileManageShowActivity.this.finish();
			}
		};
		
	//���ɾ����ѡ�ļ�	
	public void delefile(View view){
		//��Ҫɾ�����ļ��ļ���
		ArrayList<FileInfors> needDelete = new ArrayList<FileInfors>();
		//���ҵ�Ҫɾ�����ļ�
		ArrayList<FileInfors> infors = fileadapter.getitemInfos();
		for (int x = infors.size()-1; x >= 0; x--) {
			//��ȡ����ÿһ������Դ
			FileInfors infor = infors.get(x);
			
			if (infor.ischecked) {
				needDelete.add(infor);//��ѡ�е���Ҫɾ�����ļ�ͳһ��ӵ�  needDelete�ļ�����
				infors.remove(x);
			}
		}
		//ѭ��Ҫɾ���ļ��ļ���
		for (int x = 0; x < needDelete.size(); x++) {
			FileInfors deleteinfor = needDelete.get(x);//����ļ��Ķ���
			String path = deleteinfor.path;//����ļ���·��
			File file = new File(path);//�ļ�ת����File���͵Ķ���
			//��ȡapplication����
			Myapplication my = (Myapplication) getApplication();
			//��ͨ��File�����ȡ��ǰ�ļ��Ĵ�С���ֽڣ�
			long size = file.length(); 
			LogUtil.printlog("size", size+"");
			
			long newSize = 0;  
			String newSizes = "";
			
			//���ļ�ɾ��
			file.delete();
			if (value.equals(FiletypeUtil.TYPE_ANY)) {
				//�����µĿռ�
				newSize = filemanager.getAll() - size;
				 newSizes = Formatter.formatFileSize(this, newSize);//�ֽ�->MB
				 //��FileInfors������ļ��������ļ�����ɾ��
				 filemanager.getAllfiles().remove(deleteinfor);
				 
				//����������µ�������ȥ
				filemanager.setAll(newSize);
				filemanager.setAllsize(newSizes);
				
			}else if (value.equals(FiletypeUtil.TYPE_TXT)){
				//�����µĿռ�
				newSize = filemanager.getText() - size;
				newSizes = Formatter.formatFileSize(this, newSize);
				filemanager.getTextfiles().remove(deleteinfor);
				
				//����������µ�������ȥ
				filemanager.setText(newSize);
				filemanager.setTextsize(newSizes);
			}else if (value.equals(FiletypeUtil.TYPE_VIDEO)){
				//�����µĿռ�
				 newSize = filemanager.getVideo() - size;
				 newSizes = Formatter.formatFileSize(this, newSize);
				 filemanager.getVideofiles().remove(deleteinfor);
				 
				//����������µ�������ȥ
				filemanager.setVideo(newSize);
				filemanager.setVideosize(newSizes);
			}else if (value.equals(FiletypeUtil.TYPE_AUDIO)){
				//�����µĿռ�
				 newSize = filemanager.getAudio() - size;
				 newSizes = Formatter.formatFileSize(this, newSize);
				 filemanager.getAudiofiles().remove(deleteinfor);
				 
				//����������µ�������ȥ
				filemanager.setAudio(newSize);
				filemanager.setAudiosize(newSizes);
			}else if (value.equals(FiletypeUtil.TYPE_IMAGE)){
				//�����µĿռ�
				 newSize = filemanager.getImage() - size;
				 newSizes = Formatter.formatFileSize(this, newSize);
				 filemanager.getImagefiles().remove(deleteinfor);
				 
				//����������µ�������ȥ
				filemanager.setImage(newSize);
				filemanager.setImagesize(newSizes);
			}else if (value.equals(FiletypeUtil.TYPE_ZIP)){
				//�����µĿռ�
				newSize = filemanager.getZip() - size;
				newSizes = Formatter.formatFileSize(this, newSize);
				filemanager.getZipfiles().remove(deleteinfor);
				//����������µ�������ȥ
				filemanager.setZip(newSize);
				filemanager.setZipsize(newSizes);
			}else{
				//�����µĿռ�
				 newSize = filemanager.getApk() - size;
				 newSizes = Formatter.formatFileSize(this, newSize);
				 filemanager.getApkfiles().remove(deleteinfor);
				//����������µ�������ȥ
				filemanager.setApk(newSize);
				filemanager.setApksize(newSizes);
			}
			//�����ļ�����
			filenumber.setText("�ļ�������"+infors.size());
			//����ռ�ÿռ�
			filesizes.setText("ռ�ÿռ䣺"+newSizes);
			
		}
		
		fileadapter.notifyDataSetChanged();
	}
		
	
	
}
