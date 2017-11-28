// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

// ���C���A�v���P�[�V�����N���XMainApplication
public class MainApplication extends Application {

	// �����o�t�B�[���h�̏�����
	private static final String TAG = "MainApplication";	// TAG��"MainApplication"�ɏ�����.

	// �A�v���P�[�V�������������ꂽ��.
	@Override
	public void onCreate(){
		
		// Toast��"OnCreate"��\��.
		Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();	// "onCreate"��Toast�ŕ\��.
		Log.d(TAG, "onCreate");	// Log.d��"onCreate"���L�^.
		
	}
	
	// �A�v���P�[�V�������I��������.
	@Override
	public void onTerminate(){
		
		// Toast��"onTerminate"��\��.
		Toast.makeText(this, "onTerminate", Toast.LENGTH_LONG).show();	// "onTerminate"��Toast�ŕ\��.
		Log.d(TAG, "onTerminate");	// Log.d��"onTerminate"���L�^.
		
	}
	
	// �g�p�ł��郁���������Ȃ��Ȃ�����.
	@Override
	public void onLowMemory(){
		
		// Toast��"onLowMemory"��\��.
		Toast.makeText(this, "onLowMemory", Toast.LENGTH_LONG).show();	// "onLowMemory"��Toast�ŕ\��.
		Log.d(TAG, "onLowMemory");	// Log.d��"onLowMemory"���L�^.
		
	}
	
}