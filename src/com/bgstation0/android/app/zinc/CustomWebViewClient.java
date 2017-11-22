// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Browser;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

// �J�X�^���E�F�u�r���[�N���C�A���g�N���XCustomWebViewClient
public class CustomWebViewClient extends WebViewClient {

	// �����o�t�B�[���h�̏�����
	private static final String TAG = "CustomWebViewClient";	// TAG��CustomWebViewClient�ɏ�����.
	private Context mContext = null;	// Context�^mContext��null�ɏ�����.
	private String mStartUrl = "";	// mStartUrl��""�ŏ�����.
	//private String mFinishUrl = "";	// mFinishUrl��""�ŏ�����.
	private int mCount = 0;	// mCount��""�ŏ�����.
	
	// �����t���R���X�g���N�^
	CustomWebViewClient(Context context){
			
		// �����������o�ɃZ�b�g.
		mContext = context;	// mContext��context���Z�b�g.
		
	}
	
	// �y�[�W�̃��[�h���J�n���ꂽ��.
	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon){
			
		// ����̏���
		super.onPageStarted(view, url, favicon);	// �e�N���X��onPageStarted���Ă�.

		// url�����O�ɏo��.
		Log.d(TAG, "onPageStarted: url = " + url);	// Log.d��url���o��.
		
		// URL�o�[��URL���Z�b�g.
		setUrl(url);	// setUrl��url���Z�b�g.
		
		// �v���O���X�o�[��\��.
		setProgressBarVisible(true);	// setProgressBarVisible(true)�ŕ\��.
		
		// ���[�h���J�n����URL��ێ����Ă���.
		mStartUrl = url;	// mStartUrl��url���Z�b�g.
		mCount = 0;	// mCount��0�ɂ��Ă���.
					
	}
		
	// ���[�h����URL���ύX���ꂽ��.
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url){
	
		// url�����O�ɏo��.
		Log.d(TAG, "shouldOverrideUrlLoading: url = " + url);	// Log.d��url���o��.
				
		// URL�o�[��URL���Z�b�g.
		setUrl(url);	// setUrl��url���Z�b�g.
		
		// Chrome�ȂǊ���̃u���E�U�ŊJ���Ȃ��悤�ɂ���ɂ�false��Ԃ�.
		return false;	// false��Ԃ�.
		
	}
	
	// �y�[�W�̃��[�h���I��������.
	@Override
	public void onPageFinished(WebView view, String url){
		
		// ����̏���
		super.onPageFinished(view, url);	// �e�N���X��onPageFinished���Ă�.

		// url�����O�ɏo��.
		Log.d(TAG, "onPageFinished: url = " + url);	// Log.d��url���o��.
			
		// ����o�^�����𖞂������ǂ����𔻒�.
		if (url.equals(mStartUrl) && mCount == 0){	// ���߂̊J�nURL�ň�ԍŏ��̎�.
			addHistory(view, url);	// addHistory��url�𗚗��ɓo�^.
		}
		mCount++;	// mCount��1���₷.
		
		// �v���O���X�o�[���\��.
		setProgressBarVisible(false);	// setProgressBarVisible(false)�Ŕ�\��.
					
	}
	
	// URL�o�[��URL���Z�b�g.
	public void setUrl(String url){
		
		// mContext����MainActivity���擾��, MainActivity��URL�o�[�ɃZ�b�g.
		if (mContext != null){	// mContext��null�łȂ����.
					
			// URL�o�[�ɔ��f.
			MainActivity mainActivity = (MainActivity)mContext;	// mContext��MainActivity�ɃL���X�g��, mainActivity�Ɋi�[.
			mainActivity.setUrlOmit(url);	// mainActivity.setUrlOmit��URL�o�[��URL���Z�b�g.
			
		}
				
	}
	
	// �v���O���X�o�[�̕\��/��\�����Z�b�g.
	public void setProgressBarVisible(boolean visible){
		
		// mContext����MainActivity���擾��, MainActivity�̃v���O���X�o�[�ɃZ�b�g.
		if (mContext != null){	// mContext��null�łȂ����.
							
			// �v���O���X�o�[�ɔ��f.
			MainActivity mainActivity = (MainActivity)mContext;	// mContext��MainActivity�ɃL���X�g��, mainActivity�Ɋi�[.
			mainActivity.setProgressBarVisible(visible);	// mainActivity.setProgressBarVisible��visible���Z�b�g.
							
		}
				
	}
	
	// ������URL��o�^.
	public void addHistory(WebView view, String url){
		
		// mContext����MainActivity���擾��, �����getContentResolver���g��.
		if (mContext != null){	// mContext��null�łȂ����.
			
			// MainActivity�ɃL���X�g.
			MainActivity mainActivity = (MainActivity)mContext;	// mContext��MainActivity�ɃL���X�g��, mainActivity�Ɋi�[.
					
			// ����URL�𗚗��ɓo�^.
			ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�̐���.
			values.put(Browser.BookmarkColumns.TITLE, view.getTitle());	// values.put��view.getTitle�Ŏ擾�����^�C�g����o�^.
			values.put(Browser.BookmarkColumns.URL, url);	// values.put��url��o�^.
			values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// ���ݎ�����o�^.
			values.put(Browser.BookmarkColumns.BOOKMARK, "0");	// values.put��BOOKMARK�t���O��"0"�Ƃ��ēo�^.
			try{	// try�ň͂�.
				Uri uri = mainActivity.getContentResolver().insert(Browser.BOOKMARKS_URI, values);	// mainActivity.getContentResolver().insert��values��}��.(Uri�I�u�W�F�N�guri�Ɋi�[.)
				if (uri == null){	// ���ɑ}������Ă���ꍇ, null���Ԃ�͗l.
					values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�̐���.
					values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// ���ݎ�����o�^.
					int row = mainActivity.getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.URL + "=?", new String[]{url});	// mainActivity.getContentResolver().update��URL�������s���X�V.
					if (row < 0){
						Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
					}
				}
			}
			catch (Exception ex){	// ��O��catch.
				Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
			}
			
		}
		
	}
	
}