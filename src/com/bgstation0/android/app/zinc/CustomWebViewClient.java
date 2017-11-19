// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.Browser;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

// �J�X�^���E�F�u�r���[�N���C�A���g�N���XCustomWebViewClient
public class CustomWebViewClient extends WebViewClient {

	// �����o�t�B�[���h�̏�����
	private static final String TAG = "CustomWebViewClient";	// TAG��CustomWebViewClient�ɏ�����.
	Context mContext = null;	// Context�^mContext��null�ɏ�����.
	private String mStartUrl = "";	// mStartUrl��""�ŏ�����.
	private String mFinishUrl = "";	// mFinishUrl��""�ŏ�����.
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
		
		// mContext����Activity���擾��,�@��������eView���擾.
		if (mContext != null){	// mContext��null�łȂ����.
			
			// URL�o�[�ɔ��f.
			Activity activity = (Activity)mContext;	// mContext��Activity�ɃL���X�g��, activity�Ɋi�[.
			//Toast.makeText(activity, "onPageStarted: url = " + url, Toast.LENGTH_LONG).show();	// Toast��url��\��.
			EditText etUrl = (EditText)activity.findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
			etUrl.setText(url);	// etUrl.SetText��URL�o�[��etUrl��url���Z�b�g.
		
			// ���[�h���J�n����URL��ێ����Ă���.
			mStartUrl = url;	// mStartUrl��url���Z�b�g.
			mCount = 0;	// mCount��0�ɂ��Ă���.
			
		}
			
	}
		
	// ���[�h����URL���ύX���ꂽ��.
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url){
	
		// url�����O�ɏo��.
		Log.d(TAG, "shouldOverrideUrlLoading: url = " + url);	// Log.d��url���o��.
				
		// mContext����Activity���擾��,�@��������eView���擾.
		if (mContext != null){	// mContext��null�łȂ����.
			Activity activity = (Activity)mContext;	// mContext��Activity�ɃL���X�g��, activity�Ɋi�[.
			//Toast.makeText(activity, "shouldOverrideUrlLoading: url = " + url, Toast.LENGTH_LONG).show();	// Toast��url��\��.
			EditText etUrl = (EditText)activity.findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
			etUrl.setText(url);	// etUrl.SetText��URL�o�[��etUrl��url���Z�b�g.
		}
				
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
			
		// mContext����Activity���擾��, �����getContentResolver���g��.
		if (mContext != null){	// mContext��null�łȂ����.
			
			// �O���URL�ƈႤ�ꍇ�͗����ɓo�^.
			Activity activity = (Activity)mContext;	// mContext��Activity�ɃL���X�g��, activity�Ɋi�[.
			//if (!url.equals(mFinishUrl)){	// url��mFinishUrl�ƈႤ�ꍇ.
			if (url.equals(mStartUrl)){	// ���߂̊J�nURL�Ɠ���.
				if (mCount == 0){	// ��������ԍŏ��̎�.

					// ����URL�𗚗��ɓo�^.
					//Toast.makeText(activity, "onPageFinished: url = " + url, Toast.LENGTH_LONG).show();	// Toast��url��\��.
					ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�̐���.
					values.put(Browser.BookmarkColumns.TITLE, view.getTitle());	// values.put��view.getTitle�Ŏ擾�����^�C�g����o�^.
					values.put(Browser.BookmarkColumns.URL, url);	// values.put��url��o�^.
					values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// ���ݎ�����o�^.
					values.put(Browser.BookmarkColumns.BOOKMARK, "0");	// values.put��BOOKMARK�t���O��"0"�Ƃ��ēo�^.
					try{	// try�ň͂�.
						Uri uri = activity.getContentResolver().insert(Browser.BOOKMARKS_URI, values);	// activity.getContentResolver().insert��values��}��.(Uri�I�u�W�F�N�guri�Ɋi�[.)
						if (uri == null){	// ���ɑ}������Ă���ꍇ, null���Ԃ�͗l.
							values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�̐���.
							values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// ���ݎ�����o�^.
							int row = activity.getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.URL + "=?", new String[]{url});	// activity.getContentResolver().update��URL�������s���X�V.
							if (row < 0){
								//Toast.makeText(activity, "error: "+url, Toast.LENGTH_LONG).show();
								Toast.makeText(activity, activity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
							}
							else{
								//Toast.makeText(activity, "update: "+url, Toast.LENGTH_LONG).show();
							}
						}
						else{
							//Toast.makeText(activity, "insert: "+url, Toast.LENGTH_LONG).show();
						}
						//Toast.makeText(activity, "Uri="+uri, Toast.LENGTH_LONG).show();
						//mFinishUrl = url;	// mFinishUrl��url���Z�b�g.
					}
					catch (Exception ex){	// ��O��catch.
						//Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();	// ex.getMessage�Ŏ擾������O���b�Z�[�W��Toast�ŕ\��.
						Toast.makeText(activity, activity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
					}
					
				}
			}
			mCount++;	// mCount��1���₷.
			
		}
				
	}
	
}