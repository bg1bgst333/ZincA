// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.net.URLEncoder;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

//���C���A�N�e�B�r�e�B�N���XMainActivity
public class MainActivity extends Activity implements OnClickListener, OnEditorActionListener{	// View.OnClickListener, TextView.OnEditorActionListener�C���^�[�t�F�[�X�̒ǉ�.

	// �����o�t�B�[���h�̏�����.
	public static final int REQUEST_CODE_BOOKMARK = 1001;	// REQUEST_CODE_BOOKMARK��1001�Ƃ���.
	public static final int REQUEST_CODE_HISTORY = 1002;	// REQUEST_CODE_HISTORY��1002�Ƃ���.
	public static final String SEARCH_URL_GOOGLE = "https://www.google.co.jp/search?q=";	// SEARCH_URL_GOOGLE��"https://www.google.co.jp/search?q="�Ƃ���.
	
	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_main);	// setContentView��R.layout.activity_main���Z�b�g.
        initUrlBar();	// initUrlBar��etUrl��������.
        initProgressBar();	// initProgressBar��progressbar��������.
        initWebView();	// initWebView��webView��������.
        
    }
    
    // �o�b�N�L�[�������ꂽ��.
    @Override
    public void onBackPressed(){
    	
    	// �o�b�N�L�[�ɂ�����E�F�u�r���[�̓���.
    	onBackPressedWebView();	// onBackPressedWebView�ɔC����.
    	
    }
    
    // �N�������A�N�e�B�r�e�B�̌��ʂ��Ԃ��ė�����.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	// ����̏���.
    	super.onActivityResult(requestCode, resultCode, data);	// �e�N���X��onActivityResult���Ă�.
    	
    	// �L�����Z���̏ꍇ.
    	if (resultCode == RESULT_CANCELED){	// resultCode��RESULT_CANCELED�̏ꍇ.
    		return;	// ���������I��.
    	}
    	
    	// �N�������A�N�e�B�r�e�B���������̌��ʂɑ΂��鏈��.
    	Bundle bundle = data.getExtras();	// data.getExtras��bundle���擾.
    	
    	// �����̐U�蕪��.
    	switch (requestCode){	// requestCode���ƂɐU�蕪��.
    	
    		// �u�b�N�}�[�N�ꗗ.
    		case REQUEST_CODE_BOOKMARK:	// �u�b�N�}�[�N�̈ꗗ����߂��Ă����ꍇ.
    		// �����ꗗ.
    		case REQUEST_CODE_HISTORY:	// �����̈ꗗ����߂��Ă����ꍇ.
    			
    			// �u�b�N�}�[�N����ї����̃A�C�e�����I�����ꂽ��.
    			if (resultCode == RESULT_OK){	// RESULT_OK�̏ꍇ.
    				loadSelectedUrl(bundle);	// loadSelectedUrl��bundle�œn����URL�����[�h.
    			}
    			
    			// ������.
    			break;	// break�Ŕ�����.
    			
    		// ����ȊO�̎�.
    		default:
    			
    			break;	// break�Ŕ�����.
    			
    	}
    	
    }
    
    // ���j���[���쐬���ꂽ��.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	
    	// ���\�[�X���烁�j���[���쐬.
    	getMenuInflater().inflate(R.menu.menu_main, menu);	// getMenuInflater().inflate��R.menu.menu_main���烁�j���[���쐬.
    	return true;	// true��Ԃ�.
    	
    }
    
    // ���j���[���I�����ꂽ��.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	
    	// �I�����ꂽ���j���[�A�C�e�����ƂɐU�蕪����.
    	int id = item.getItemId();	// item.getItemId�őI�����ꂽ���j���[�A�C�e����id���擾.
    	if (id == R.id.menu_item_bookmark_add){	// R.id.menu_item_bookmark_add("�u�b�N�}�[�N�̒ǉ�")�̎�.

    		// �u�b�N�}�[�N�̒ǉ�.
    		addBookmark();	// addBookmark�Œǉ�.
    		
    	}
    	else if (id == R.id.menu_item_bookmark_show){	// R.id.menu_item_bookmark_show("�u�b�N�}�[�N�̈ꗗ")�̎�.
    	
    		// �u�b�N�}�[�N�̕\��.
    		showBookmark();	// showBookmark�ŕ\��.
    		
    	}
    	else if (id == R.id.menu_item_history_show){	// R.id.menu_item_history_show("�����̈ꗗ")�̎�.
    		
    		// �����̕\��.
    		showHistory();	// showHistory�ŕ\��.
    		
    	}
    	
    	// ���Ƃ͊���̏����ɔC����.
    	return super.onOptionsItemSelected(item);	// �e�N���X��onOptionsItemSelected���Ă�.
    	
    }
    
    // �{�^���������ꂽ��.
    @Override
    public void onClick(View v){	// OnClickListener.onClick���I�[�o�[���C�h.
    	
    	// �{�^�����ƂɐU�蕪����.
    	switch (v.getId()){	// v.getId()��View(Button)��id���擾.
    			
    		// ����ȊO�̎�.
    		default:
    			
    			// ������.
    			break;	// break�Ŕ�����.
    	
    	}
    	
    }
    
    // �G�f�B�b�g�e�L�X�g��Enter�L�[�������ꂽ��.
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
    	
    	// Enter�L�[�������ꂽ��.
    	if (actionId == EditorInfo.IME_ACTION_DONE){	// Enter�L�[�������ꂽ��.(IME_ACTION_DONE)
    		
    		// �\�t�g�E�F�A�L�[�{�[�h�̔�\��.
    		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);	// getSystemService����inputMethodManager���擾.
    		inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);	// inputMethodManager.hideSoftInputFromWindow�Ŕ�\��.
    		
    		// URL�̃��[�h.
    		loadUrl();	// loadUrl��URL�o�[��URL�����[�h.
    	
    		// true��Ԃ�.
    		return true;	// return��true��Ԃ�.
    		
    	}
    	
    	// false��Ԃ�.
    	return false;	// return��false��Ԃ�.
    	
    }
    
    // URL�o�[�̏�����.
    public void initUrlBar(){
    	
    	// etUrl���擾��, OnEditorActionListener�Ƃ��Ď��g(this)���Z�b�g.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    	etUrl.setOnEditorActionListener(this);	// etUrl.setOnEditorActionListene��this(���g)���Z�b�g.
    	
    }
    
    // �v���O���X�o�[�̏�����.
    public void initProgressBar(){
    	
    	// progressBar���擾��, �ŏ��͔�\���ɂ��Ă���.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewById��R.id.progressbar����ProgressBar�I�u�W�F�N�gprogressBar���擾.
    	//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibility�Ŕ�\���ɂ���.
    	progressBar.setVisibility(View.GONE);	// progressBar.setVisibility�Ŕ�\��(View.GONE)�ɂ���.
    	//progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibility�ōŏ�����\���ɂ���.
    	
    }
    
    // �E�F�u�r���[�̏�����.
    public void initWebView(){
    	
    	// webView�̎擾.
        WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
        // JavaScript�L����.
        webView.getSettings().setJavaScriptEnabled(true);	// webView.getSettings().setJavaScriptEnabled��JavaScript��L���ɂ���.
        // CustomWebViewClient�̃Z�b�g.
        webView.setWebViewClient(new CustomWebViewClient(this));	// new�Ő�������CustomWebViewClient�I�u�W�F�N�g(�R���X�g���N�^�̈�����this��n��.)��webView.setWebViewClient�ŃZ�b�g.
        // CustomWebChromeClient�̃Z�b�g.
        webView.setWebChromeClient(new CustomWebChromeClient(this));	// new�Ő�������CustomWebChromeClient�I�u�W�F�N�g(�R���X�g���N�^�̈�����this��n��.)��webView.setWebChromeClient�ŃZ�b�g.
        
    }
    
    // URL�o�[��URL���Z�b�g.
    public void setUrl(String url){
    	
    	// etUrl���擾��, url���Z�b�g.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    	etUrl.setText(url);	// etUrl.SetText��URL�o�[��etUrl��url���Z�b�g.
    	
    }
    
    // URL�o�[��URL���Z�b�g���鎞��"http"�̏ꍇ�͏ȗ�����.
    public void setUrlOmit(String url){
    	
    	// �擪�����񂩂�ȗ����邩�𔻒�.
    	if (url.startsWith("http://")){	// "http"�̎�.
    		String omitUrl = url.substring(7);	// url.substring��7�����ڂ���̕������omitUrl�ɕԂ�.
    		setUrl(omitUrl);	// setUrl��omitUrl���Z�b�g.
    	}
    	else{
    		setUrl(url);	// setUrl�ł��̂܂�url��n��.
    	}
    	
    }
    
    // URL�o�[����URL���擾.
    public String getUrl(){
    	
    	// etUrl��URL���擾.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    	return etUrl.getText().toString();	// etUrl.getText().toString��URL��Ԃ�.
    	
    }
    
    // URL�������L�[���[�h���𔻒肷��.
    public boolean isUrl(String url){
    	
    	// '.'��������, ����, ' '���Ȃ�.
    	if (url.contains(".") && !url.contains(" ")){	// url.contains(".")��true, ����, url.contains(" ")��false�̎�.
    		return true;	// URL�̏ꍇ, true��Ԃ�.
    	}
    	else{
    		return false;	// �����L�[���[�h�̏ꍇ, false��Ԃ�.
    	}
    	
    }
    
    // Google����URL�𐶐�����.
    public String generateSearchUrlGoogle(String url){
    
    	// URL�G���R�[�h.
    	String encoded = null;	// String�^encoded��null�ɏ�����.
    	try{	// try�ň͂�.
    		encoded = URLEncoder.encode(url, "utf-8");	// URLEncoder.encode��url��utf-8�ɕϊ���, encoded�Ɋi�[.
    	}
    	catch (Exception ex){	// catch�ŗ�O�̃L���b�`.
    		encoded = "";	// encoded��""���Z�b�g.
    		return encoded;	// encoded��Ԃ�.
    	}
    	
    	// �擪��Google����URL��t��.
    	String searchUrl = SEARCH_URL_GOOGLE + encoded;	// searchUrl��SEARCH_URL_GOOGLE��encoded��A���������̂��Z�b�g.
    	return searchUrl;	// searchUrl��Ԃ�.
    	
    }
    
    //�@URL�o�[��URL�������L�[���[�h���ǂ����𔻒肵�Ă���, �K�؂Ƀ��[�h.
    public void loadUrl(){
    	
    	// URL�������L�[���[�h���𔻒肷��.
    	String url = getUrl();	// URL�o�[����, getUrl��url���擾.
    	if (isUrl(url)){	// isUrl��url��URL�̏ꍇ.
    		// url�����[�h.
        	loadUrlComplement(url);	// url��loadUrlComplement()�Ń��[�h.
    	}
    	else{	// �����L�[���[�h�̏ꍇ.
    		String searchUrl = generateSearchUrlGoogle(url);	// searchUrl��generateSearchUrlGoogle�Ő�������URL���Z�b�g.
    		loadUrlComplement(searchUrl);	// searchUrl��loadUrlComplement()�Ń��[�h.
    	}
    	
    }
    
    // �w�肳�ꂽURL�����[�h.
    public void loadUrl(String url){
    	
    	// webView���擾��, url�����[�h.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
		webView.loadUrl(url);	// webView.loadUrl��url�̎w��Web�y�[�W�����[�h.
		
    }
    
    // �w�肳�ꂽURL��"http"��⊮���ă��[�h.
    public void loadUrlComplement(String url){
    	
    	// �擪�����񂩂�⊮���邩�𔻒�.
    	if (url.startsWith("https://") || url.startsWith("http://")){	// "https"�܂���"http"�̎�.
    		loadUrl(url);	// loadUrl(String url)�����̂܂܌Ă�.
    	}
    	else{
    		String complementUrl = "http://" + url;	// �擪��"http://"��t��.
    		loadUrl(complementUrl);	// // loadUrl(String url)��complementUrl��n��.
    	}
    	
    }
    
    // �I�����ꂽURL�����[�h.
    public void loadSelectedUrl(Bundle bundle){
    	
    	// bundle����URL���擾�����[�h.
    	String url = bundle.getString("url");	// bundle.getString��url���擾.
		setUrlOmit(url);	// setUrlOmit��URL�o�[��URL���Z�b�g.
		loadUrl();	// loadUrl��URL�o�[��URL�����[�h.
		
    }
    
    // �v���O���X�o�[�ɐi���x���Z�b�g.
    public void setProgressValue(int progress){
    	
    	// �v���O���X�o�[���擾��, �w�肳�ꂽ�i���x���Z�b�g.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewById��R.id.progressbar����ProgressBar�I�u�W�F�N�gprogressBar���擾.
    	progressBar.setProgress(progress);	// progressBar.setProgress��progress���Z�b�g.
    	
    }
    
    // �v���O���X�o�[�̕\��/��\�����Z�b�g.
    public void setProgressBarVisible(boolean visible){
    	
    	// �v���O���X�o�[���擾��, �\��/��\�����Z�b�g.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewById��R.id.progressbar����ProgressBar�I�u�W�F�N�gprogressBar���擾.
    	if (visible){	// true�Ȃ�\��.
    		progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibility��VISIBLE.
    	}
    	else{	// false�Ȃ��\��.
    		//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibility��INVISIBLE.
    		progressBar.setVisibility(View.GONE);	// progressBar.setVisibility��GONE.
    	}
    	
    }
    
    // �A�N�V�����o�[�̃^�C�g�����Z�b�g.
    public void setActionBarTitle(String title){
    	
    	// �A�N�V�����o�[���擾��, �w�肳�ꂽ�^�C�g�����Z�b�g.
    	getActionBar().setTitle(title);	// getActionBar().setTitle��title���Z�b�g.
    	
    }
    
    // �o�b�N�L�[�������ꂽ����WebView�̓���.
    public void onBackPressedWebView(){
    	
    	// �߂��ꍇ��, 1�O�̃y�[�W�ɖ߂�.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
    	if (webView.canGoBack()){	// �o�b�N�\�ȏꍇ.
    		webView.goBack();	// webView.goBack�Ŗ߂�.
    	}
    	else{	// �����łȂ���.
    		super.onBackPressed();	// �e�N���X��onBackPressed���Ă�.
    	}
    	
    }
    
    // �u�b�N�}�[�N�ւ̒ǉ�.
    public void addBookmark(){
    	
    	// webView���擾��, URL�ƃ^�C�g�����擾.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
		String title = webView.getTitle();	// webView.getTitle�Ń^�C�g�����擾.
		String url = webView.getUrl();	// webView.getUrl��URL���擾.
		
		// ����URL���u�b�N�}�[�N�ɓo�^.
		ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�̐���.
		values.put(Browser.BookmarkColumns.TITLE, title);	// values.put��title��o�^.
		values.put(Browser.BookmarkColumns.URL, url);	// values.put��url��o�^.
		values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// ���ݎ�����o�^.
		values.put(Browser.BookmarkColumns.BOOKMARK, "1");	// values.put��BOOKMARK�t���O��"1"�Ƃ��ēo�^.
		try{	// try�ň͂�.
			Uri uri = getContentResolver().insert(Browser.BOOKMARKS_URI, values);	// getContentResolver().insert��values��}��.(Uri�I�u�W�F�N�guri�Ɋi�[.)
			if (uri == null){	// ���ɑ}������Ă���ꍇ, null���Ԃ�͗l.
				values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�̐���.
				values.put(Browser.BookmarkColumns.DATE, System.currentTimeMillis());	// ���ݎ�����o�^.
				values.put(Browser.BookmarkColumns.BOOKMARK, "1");	// values.put��BOOKMARK�t���O��"1"�Ƃ��ēo�^.
				int row = getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.URL + "=?", new String[]{url});	// getContentResolver().update��URL�������s���X�V.
				if (row < 0){	// �X�V���s.
					Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
				}
				else{	// �X�V����.
					Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_success), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_success�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
				}
			}
			else{	// �}������.
				Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_success), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_success�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
			}
		}
		catch (Exception ex){	// ��O��catch.
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
		}
		
    }
    
    // �u�b�N�}�[�N�̕\��.
    public void showBookmark(){
    	
    	// �u�b�N�}�[�N�A�N�e�B�r�e�B���N������.
		String packageName = getPackageName();	// getPackageName��packageName���擾.
		Intent intent = new Intent();	// Intent�I�u�W�F�N�gintent���쐬.
		intent.setClassName(packageName, packageName + ".BookmarkActivity");	// intent.setClassName��".BookmarkActivity"���Z�b�g.
		startActivityForResult(intent, REQUEST_CODE_BOOKMARK);	// startActivityForResult��intent��REQUEST_CODE_BOOKMARK��n��.
		
    }
    
    // �����̕\��.
    public void showHistory(){
    	
    	// �q�X�g���[�A�N�e�B�r�e�B���N������.
		String packageName = getPackageName();	// getPackageName��packageName���擾.
		Intent intent = new Intent();	// Intent�I�u�W�F�N�gintent���쐬.
		intent.setClassName(packageName, packageName + ".HistoryActivity");	// intent.setClassName��".HistoryActivity"���Z�b�g.
		startActivityForResult(intent, REQUEST_CODE_HISTORY);	// startActivityForResult��intent��REQUEST_CODE_HISTORY��n��.
		
    }
    
}