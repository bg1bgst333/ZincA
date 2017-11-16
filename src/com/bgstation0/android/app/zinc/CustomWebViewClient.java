// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.app.Activity;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

// �J�X�^���E�F�u�r���[�N���C�A���g�N���XCustomWebViewClient
public class CustomWebViewClient extends WebViewClient {

	// �����o�t�B�[���h�̏�����
	Context mContext = null;	// Context�^mContext��null�ɏ�����.
		
	// �����t���R���X�g���N�^
	CustomWebViewClient(Context context){
			
		// �����������o�ɃZ�b�g.
		mContext = context;	// mContext��context���Z�b�g.
			
	}
		
	// ���[�h����URL���ύX���ꂽ��.
	public boolean shouldOverrideUrlLoading(WebView view, String url){
	
		// mContext����Activity���擾��,�@��������eView���擾.
		if (mContext != null){	// mContext��null�łȂ����.
			Activity activity = (Activity)mContext;	// mContext��Activity�ɃL���X�g��, activity�Ɋi�[.
			EditText etUrl = (EditText)activity.findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
			etUrl.setText(url);	// etUrl.SetText��URL�o�[��etUrl��url���Z�b�g.
		}
				
		// Chrome�ȂǊ���̃u���E�U�ŊJ���Ȃ��悤�ɂ���ɂ�false��Ԃ�.
		return false;	// false��Ԃ�.
		
	}
	
}