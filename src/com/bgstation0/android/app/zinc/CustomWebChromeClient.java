// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.content.Context;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

//�J�X�^���E�F�u�N���[���N���C�A���g�N���XCustomWebChromeClient
public class CustomWebChromeClient extends WebChromeClient {
	
	// �����o�t�B�[���h�̏�����
	private Context mContext = null;	// Context�^mContext��null�ɏ�����.
	
	// �����t���R���X�g���N�^
	CustomWebChromeClient(Context context){
				
		// �����������o�ɃZ�b�g.
		mContext = context;	// mContext��context���Z�b�g.
			
	}
	
	// �i���x���ω�������.
	@Override
	public void onProgressChanged(WebView view, int progress){
		
		// �i���x���Z�b�g.
		setProgress(progress);	// setProgress�Ői���xprogress���Z�b�g.
		
	}
	
	// �i���x�̃Z�b�g.
	public void setProgress(int progress){
		
		// mContext����MainActivity���擾��, MainActivity�̃v���O���X�o�[�ɃZ�b�g.
		if (mContext != null){	// mContext��null�łȂ����.
					
			// �v���O���X�o�[�ɔ��f.
			MainActivity mainActivity = (MainActivity)mContext;	// mContext��MainActivity�ɃL���X�g��, mainActivity�Ɋi�[.
			mainActivity.setProgressValue(progress);	// mainActivity.setProgressValue��progress���Z�b�g.
					
		}
		
	}
				
}