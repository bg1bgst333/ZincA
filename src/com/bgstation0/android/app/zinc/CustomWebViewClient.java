// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
	private static final String TAG = "CustomWebViewClient";	// TAG��"CustomWebViewClient"�ɏ�����.
	private Context mContext = null;	// Context�^mContext��null�ɏ�����.
	public MainApplication mApp = null;	// MainApplication�I�u�W�F�N�gmApp��null�ŏ�����.
	private String mStartUrl = "";	// mStartUrl��""�ŏ�����.
	//private String mFinishUrl = "";	// mFinishUrl��""�ŏ�����.
	private int mCount = 0;	// mCount��""�ŏ�����.
	
	// �����t���R���X�g���N�^
	CustomWebViewClient(Context context){
			
		// �����������o�ɃZ�b�g.
		mContext = context;	// mContext��context���Z�b�g.
		mApp = (MainApplication)mContext.getApplicationContext();	// getApplicationContext��mApp���擾.
		
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
		
		// �^�C�g���̕ύX.
		setActionBarTitle(view.getTitle());	// view.getTitle�Ŏ擾�����^�C�g����setActionBarTitle�ŃZ�b�g.
		
		// ����o�^�����𖞂������ǂ����𔻒�.
		if (url.equals(mStartUrl) && mCount == 0){	// ���߂̊J�nURL�ň�ԍŏ��̎�.
			addHistoryToDB(view, url);	// addHistoryToDB��url�𗚗��ɓo�^.
		}
		mCount++;	// mCount��1���₷.
		
		// �v���O���X�o�[���\��.
		setProgressBarVisible(false);	// setProgressBarVisible(false)�Ŕ�\��.
		//setProgress(0);	// setProgress�Ői���x��0�ɃZ�b�g.
		
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
	
	// �i���x�̃Z�b�g.
	public void setProgress(int progress){
			
		// mContext����MainActivity���擾��, MainActivity�̃v���O���X�o�[�ɃZ�b�g.
		if (mContext != null){	// mContext��null�łȂ����.
						
			// �v���O���X�o�[�ɔ��f.
			MainActivity mainActivity = (MainActivity)mContext;	// mContext��MainActivity�ɃL���X�g��, mainActivity�Ɋi�[.
			mainActivity.setProgressValue(progress);	// mainActivity.setProgressValue��progress���Z�b�g.
						
		}
			
	}
		
	// �A�N�V�����o�[�̃^�C�g�����Z�b�g.
    public void setActionBarTitle(String title){
    	
    	// mContext����MainActivity���擾��, MainActivity�̃A�N�V�����o�[�̃^�C�g���ɃZ�b�g.
    	if (mContext != null){	// mContext��null�łȂ����.
    	
    		// �A�N�V�����o�[�̃^�C�g���ɔ��f.
    		MainActivity mainActivity = (MainActivity)mContext;	// mContext��MainActivity�ɃL���X�g��, mainActivity�Ɋi�[.
    		mainActivity.setTitle(title);	// mainActivity.setTitle��title���Z�b�g.
    		
    		// ��L��setTitle�������Ɣ��f����Ȃ���������̂�, �A�N�V�����o�[������Z�b�g����.
    		ActionBar act = mainActivity.getActionBar();	// mainActivity.getActionBar��act���擾.
    		if (act != null){	// act��null�łȂ����.
    			act.setTitle(title);	// act.setTitle�Ń^�C�g�����Z�b�g.
    		}
    		
    	}
    	
    }
    
	// ������URL��o�^.(Rrowser�N���X��.)
	public void addHistoryToBrowser(WebView view, String url){
		
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
			values.put(Browser.BookmarkColumns.VISITS, "1");	// values.put��VISITS��"1"�Ƃ��ēo�^.
			try{	// try�ň͂�.
				Uri uri = mainActivity.getContentResolver().insert(Browser.BOOKMARKS_URI, values);	// mainActivity.getContentResolver().insert��values��}��.(Uri�I�u�W�F�N�guri�Ɋi�[.)
				if (uri == null){	// ���ɑ}������Ă���ꍇ, null���Ԃ�͗l.
					// VISITS����肽��.
					String[] projection = new String[]{
							Browser.BookmarkColumns.URL,	// URL.
							Browser.BookmarkColumns.DATE,	// ����.
							Browser.BookmarkColumns.VISITS	// �K���?
					};
					int visits;	// �K���.
					Cursor c = mainActivity.getContentResolver().query(Browser.BOOKMARKS_URI, projection, Browser.BookmarkColumns.URL + "=?", new String[]{url}, Browser.BookmarkColumns.DATE + " desc");	// ���v����s���擾.
					if (c.getCount() == 1){
						if (c.moveToFirst()){
							visits = c.getInt(c.getColumnIndex(Browser.BookmarkColumns.VISITS));	// visits�̎擾.
							visits++;	// visits��1���₷.
							values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�̐���.
							values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// ���ݎ�����o�^.
							String strVisits = String.valueOf(visits);	// visits�𕶎���ɕϊ�.
							values.put(Browser.BookmarkColumns.VISITS, strVisits);	// �K��񐔂�o�^.
							int row = mainActivity.getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.URL + "=?", new String[]{url});	// mainActivity.getContentResolver().update��URL�������s���X�V.
							if (row <= 0){
								Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
							}
							else{
								//Toast.makeText(mainActivity, "visits = " + strVisits, Toast.LENGTH_LONG).show();	// �Ƃ肠����visits���o��.
							}
						}
					}
					else{
						Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
					}
				}
				else{
					//Toast.makeText(mainActivity, "visits = 1", Toast.LENGTH_LONG).show();	// �Ƃ肠����visits��1�Ƃ��ďo��.
				}
			}
			catch (Exception ex){	// ��O��catch.
				Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
			}
			
		}
		
	}
	
	// ������URL��o�^.(�Ǝ�DB��.)
	public void addHistoryToDB(WebView view, String url){
		
		// URL�ƃ^�C�g�����擾.
		String title = view.getTitle();	// view.getTitle�Ń^�C�g�����擾.
		long datemillisec = System.currentTimeMillis();	// System.currentTimeMillis�Ō��ݎ������擾��, datemillisec�Ɋi�[.
		
		// MainActivity�ɃL���X�g.
		MainActivity mainActivity = (MainActivity)mContext;	// mContext��MainActivity�ɃL���X�g��, mainActivity�Ɋi�[.
					
		// ����URL�𗚗��֒ǉ�.
		long id = mApp.mHlpr.insertRowHistory(title, url, datemillisec);	// mApp.mHlpr.insertRowHistory��title, url, datemillisec��ǉ�.
		if (id == -1){	// -1�Ȃ�.
			Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_history_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_history_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
		}
		
	}
	
}