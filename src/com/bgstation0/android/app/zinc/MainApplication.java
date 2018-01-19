// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

// ���C���A�v���P�[�V�����N���XMainApplication
public class MainApplication extends Application {

	// �����o�t�B�[���h�̏�����
	private static final String TAG = "MainApplication";	// TAG��"MainApplication"�ɏ�����.
	public Map<String, TabInfo> mTabMap = null;	// �^�umTabMap��null�ŏ�����.
	//public int mNextViewNo = 0;	// mNextViewNo��0�ɏ�����.
	public UrlListDatabaseHelper mHlpr = null;	// UrlListDatabaseHelper�I�u�W�F�N�gmHlpr��null�ɏ�����.
	
	// �A�v���P�[�V�������������ꂽ��.
	@Override
	public void onCreate(){
		
		// Toast��"OnCreate"��\��.
		//Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();	// "onCreate"��Toast�ŕ\��.
		Log.d(TAG, "onCreate");	// Log.d��"onCreate"���L�^.
		
		// �^�uDB�w���p�[�̐���.
		mHlpr = new UrlListDatabaseHelper(this);	// UrlListDatabaseHelper��this��n���Đ���.
		
		// �^�u�}�b�v�̐���.
		mTabMap = new HashMap<String, TabInfo>();	// mTabMap��HashMap�ō쐬.
		//mNextViewNo = 0;	// mNextViewNo��0�Ƃ��Ă���.
		
	}
	
	// �A�v���P�[�V�������I��������.
	@Override
	public void onTerminate(){
		
		// Toast��"onTerminate"��\��.
		//Toast.makeText(this, "onTerminate", Toast.LENGTH_LONG).show();	// "onTerminate"��Toast�ŕ\��.
		Log.d(TAG, "onTerminate");	// Log.d��"onTerminate"���L�^.
		
	}
	
	// �g�p�ł��郁���������Ȃ��Ȃ�����.
	@Override
	public void onLowMemory(){
		
		// Toast��"onLowMemory"��\��.
		//Toast.makeText(this, "onLowMemory", Toast.LENGTH_LONG).show();	// "onLowMemory"��Toast�ŕ\��.
		Log.d(TAG, "onLowMemory");	// Log.d��"onLowMemory"���L�^.
		
	}
	
	// �G���g���[���X�g�̎擾.
	public List<Entry<String, TabInfo>> getTabMapEntryList(){
		
		// �����Ń\�[�g.
        List<Entry<String, TabInfo>> list_entries = new ArrayList<Entry<String, TabInfo>>(mTabMap.entrySet());	// �G���g���[���X�g�̐���.
        Collections.sort(list_entries, new Comparator<Entry<String, TabInfo>>(){	// Collections.sort�Ń\�[�g.
        	public int compare(Entry<String, TabInfo> obj1, Entry<String, TabInfo> obj2){	// ��r�֐�compare.
        		TabInfo tabInfo1 = obj1.getValue();
        		TabInfo tabInfo2 = obj2.getValue();
        		if (tabInfo1.date < tabInfo2.date){
        			return 1;	// 1��Ԃ�.
        		}
        		else{
        			return -1;	// -1��Ԃ�.
        		}
        	}
        });
        return list_entries;	// list_entries��Ԃ�.
        
	}
	
}