// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.webkit.WebView;
import android.webkit.WebViewClient;

// �J�X�^���E�F�u�r���[�N���C�A���g�N���XCustomWebViewClient
public class CustomWebViewClient extends WebViewClient {

	// ���[�h����URL���ύX���ꂽ��.
	public boolean shouldOverrideUrlLoading(WebView view, String url){
	
		// Chrome�ȂǊ���̃u���E�U�ŊJ���Ȃ��悤�ɂ���ɂ�false��Ԃ�.
		return false;	// false��Ԃ�.
		
	}
	
}