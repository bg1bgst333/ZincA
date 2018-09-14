package com.bgstation0.android.app.zinc;

import java.net.URLEncoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class SubActivity extends Activity implements OnEditorActionListener{

	// �����o�t�B�[���h�̒�`.
	public String mPhoneUA = "";	// �d�b�p���[�U�G�[�W�F���gmPhoneUA.
	public String mPCUA = "";	// PC�p���[�U�G�[�W�F���gmPCUA.
	public String mCurrentUA = "";	// ���݂̃��[�U�G�[�W�F���gmCurrentUA.
	public static final String PC_WIN_UA_SUBSTRING = "(Windows NT 10.0; Win64; x64)";	// WindowsPC�̃��[�U�G�[�W�F���g�ł��邱�Ƃ���������.
	MainApplication mApp = null;	// mApp��null�ɂ���.
	public static final String SEARCH_URL_GOOGLE = "https://www.google.co.jp/search?q=";	// SEARCH_URL_GOOGLE��"https://www.google.co.jp/search?q="�Ƃ���.
	String mTabName = "";	// mTabName��""�ŏ�����.
	MainActivity mMainActivity = null;	// mMainActivity��null���Z�b�g.
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        
        // �^�O(�^�u��)�̎擾.
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("args");
        String tag = args.getString("tag");
        //Toast.makeText(this, "tag = " + tag, Toast.LENGTH_LONG).show();

        Toast.makeText(this, "onCreate", Toast.LENGTH_LONG).show();
        
        // ���C���A�v���P�[�V�����̎擾.
    	mApp = (MainApplication)getApplicationContext();	// getApplicationContext�Ŏ擾����MainApplication�I�u�W�F�N�g��mApp�Ɋi�[.
    	mTabName = tag;
        initUrlBar();
    	initProgressBar();
    	initWebView();
    	TabInfo tabInfo = mApp.mHlpr.getTabInfo(tag);
    	if (tabInfo == null){
    		Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
    	}
    	if (tabInfo != null){
    		if (tabInfo.url != null){
    			Toast.makeText(this, "url = " + tabInfo.url, Toast.LENGTH_LONG).show();
    			if (!tabInfo.url.equals("")){
    				Toast.makeText(this, "url = " + tabInfo.url, Toast.LENGTH_LONG).show();
    				setUrlOmit(tabInfo.url);
    				loadUrl();
    			}
    		}
    		TabInfo ti = mApp.mHlpr.getTabInfo(tag);
        	View rootView = getWindow().getDecorView();
    		View content = rootView.findViewById(R.id.layout_sub);
        	ti.view = content;
        	mApp.mTabMap.put(tag, ti);
    	}
        /*
        //Toast.makeText(this, "SubActivity.onCreate()", Toast.LENGTH_LONG).show();
        // ���C���A�v���P�[�V�����̎擾.
    	mApp = (MainApplication)getApplicationContext();	// getApplicationContext�Ŏ擾����MainApplication�I�u�W�F�N�g��mApp�Ɋi�[.
    	//Toast.makeText(this, "mTabMap = " + String.valueOf(mApp.mTabMap.size()), Toast.LENGTH_LONG).show();
        // Bundle���`�F�b�N.
        Bundle args = getIntent().getExtras();	// args�擾.
        if (args != null){	// null�łȂ�.
	        String tag = args.getString("tag");	// tag�擾.
	        boolean remove = args.getBoolean("remove");
	        if (remove){
	        	TabInfo mapti = mApp.mTabMap.get(tag);
	        	//Toast.makeText(this, "view = " + mapti.view.toString(), Toast.LENGTH_LONG).show();
	        	setContentView(mapti.view);
	        }
	        else{
		        //TextView tv = (TextView)findViewById(R.id.textview_sub);	// tv�擾.
	        	//tv.setText(tag);	// tag���Z�b�g.
		        mTabName = tag;	// mTabName��tag���Z�b�g.
	        	TabInfo tabInfo = mApp.mHlpr.getTabInfo(tag);
	        	tabInfo.title = tag;
	        	mApp.mHlpr.updateTabInfo(tag, tabInfo);
	        	initUrlBar();
	        	initProgressBar();
	        	initWebView();
	        	if (tabInfo.url != null){
	        		if (!tabInfo.url.equals("")){
	        			
	        			//TabWidget widget = mApp.mTabHost.getTabWidget();
	        			//if (widget != null){
	        			//	Toast.makeText(this, "10", Toast.LENGTH_LONG).show();
	        			//}
	        			//int c = widget.getChildCount();
	        			//Toast.makeText(this, "c = " + String.valueOf(c), Toast.LENGTH_LONG).show();
	        			//View v = widget.getChildAt(0);
	        			//Toast.makeText(this,  "v = " + v.toString(), Toast.LENGTH_LONG).show();
	        			//ViewGroup vg = (ViewGroup)v;
	        			//int c2 = vg.getChildCount();
	        			//Toast.makeText(this, "c2 = " + String.valueOf(c2), Toast.LENGTH_LONG).show();
	        			//View v2 = vg.getChildAt(0);
	        			//Toast.makeText(this, "v2 = " + v2.toString(), Toast.LENGTH_LONG).show();
	        			//View v3 = vg.getChildAt(1);
	        			//Toast.makeText(this, "v3 = " + v3.toString(), Toast.LENGTH_LONG).show();
	        			//TextView tv = (TextView)widget.findViewById(android.R.id.title);
	        			//tv.setText("hogehoge");
	        			
	        			//setUrlOmit(tabInfo.url);
	        			loadUrl();
	        		}
	        	}
	        	// �^�u�}�b�v�Ƀr���[��ۑ�.
	        	TabInfo ti = mApp.mHlpr.getTabInfo(tag);
	        	View rootView = getWindow().getDecorView();	// getWindow().getDecorView��rootView���擾.
	    		View content = rootView.findViewById(R.id.layout_sub);	// rootView����layout_sub�𔲂��o��.
	        	ti.view = content;
	        	//Toast.makeText(this, "ti.view = " + ti.view.toString(), Toast.LENGTH_LONG).show();
	        	mApp.mTabMap.put(tag, ti);
	        }
        }
        */
        
    }
    
    // �o�b�N�L�[�������ꂽ��.
    @Override
    public void onBackPressed(){
    	
    	Toast.makeText(this, "Sub", Toast.LENGTH_LONG).show();
    	
    	WebView webView = (WebView)findViewById(R.id.webview_sub);
    	if (webView.canGoBack()){	// �o�b�N�\�ȏꍇ.
    		Toast.makeText(this, "B", Toast.LENGTH_LONG).show();
    		webView.goBack();	// webView.goBack�Ŗ߂�.
    	}
    	else{	// �����łȂ���.
    		Toast.makeText(this, "C", Toast.LENGTH_LONG).show();
    		super.onBackPressed();	// �e�N���X��onBackPressed���Ă�.
    	}
    	
    	// �o�b�N�L�[�ɂ�����E�F�u�r���[�̓���.
    	//onBackPressedWebView();	// onBackPressedWebView�ɔC����.
    	
    }
    
    // Activity���J�n���ꂽ�Ƃ�.
    protected void onStart() {	// onStart�̒�`
    	
    	// �e�N���X�̏���
    	super.onStart();	// super.onStart�Őe�N���X�̊��菈��.
    	
    	// onStart�̃��O��\��.
    	//Log.v(TAG, "onStart()");	// Log.v��"onStart()"�Əo��.
    	//Toast.makeText(this, "onStart", Toast.LENGTH_LONG).show();
    }
    
    // Activity���J�n���ꂽ�Ƃ�.
    protected void onResume() {	// onResume�̒�`
    	
    	// �e�N���X�̏���
    	super.onResume();	// super.onStart�Őe�N���X�̊��菈��.
    	
    	// onStart�̃��O��\��.
    	//Log.v(TAG, "onStart()");	// Log.v��"onStart()"�Əo��.
    	//Toast.makeText(this, "onResume", Toast.LENGTH_LONG).show();
    	
    	//TabInfo ti = mApp.mTabMap.get(mTabName);
    	//if (ti != null){
    		//Toast.makeText(this, "about:blank", Toast.LENGTH_LONG).show();
			//WebView wv = (WebView)findViewById(R.id.webview_sub);
			//wv.loadUrl("about:blank");
    		//if (ti.url != null){
    			//if (ti.url.equals("")){	// url ""
    				//Toast.makeText(this, "about:blank", Toast.LENGTH_LONG).show();
    				//WebView wv2 = (WebView)findViewById(R.id.webview_sub);
    				//wv2.loadUrl("about:blank");
    			//}
    		//}
    		//else{	// url null
    			
    		//}
    	//}
    	//else{	// null
    		//Toast.makeText(this, "about:blank", Toast.LENGTH_LONG).show();
			//WebView wv = (WebView)findViewById(R.id.webview_sub);
			//wv.loadUrl("about:blank");
			//EditText et = (EditText)findViewById(R.id.edittext_sub_urlbar);
			//et.setText("");
    	//}
    	
    }
    
    @Override
    protected void onNewIntent(Intent intent){
    	super.onNewIntent(intent);
    	//Toast.makeText(this, "onNewIntent", Toast.LENGTH_LONG).show();
    }
    
 // Activity���j�����ꂽ�Ƃ�.
    protected void onDestroy() {	// onDestroy�̒�`
    	
    	// �e�N���X�̏���
    	super.onDestroy();	// super.onDestroy�Őe�N���X�̊��菈��.
    	
    	// onDestroy�̃��O��\��.
    	//Toast.makeText(this, "onDestroy", Toast.LENGTH_LONG).show();
    }
    
    // URL�o�[�̏�����.
    public void initUrlBar(){
    	
    	// etUrl���擾��, OnEditorActionListener�Ƃ��Ď��g(this)���Z�b�g.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_sub_urlbar);	// findViewById��R.id.edittext_sub_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    	etUrl.setOnEditorActionListener(this);	// etUrl.setOnEditorActionListene��this(���g)���Z�b�g.
    	
    }
    
 // �v���O���X�o�[�̏�����.
    public void initProgressBar(){
    	
    	// progressBar���擾��, �ŏ��͔�\���ɂ��Ă���.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar_sub);	// findViewById��R.id.progressbar����ProgressBar�I�u�W�F�N�gprogressBar���擾.
    	//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibility�Ŕ�\���ɂ���.
    	progressBar.setVisibility(View.GONE);	// progressBar.setVisibility�Ŕ�\��(View.GONE)�ɂ���.
    	//progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibility�ōŏ�����\���ɂ���.
    }
    
    // �E�F�u�r���[�̏�����.
    public void initWebView(){
    	
    	// webView�̎擾.
        WebView webView = (WebView)findViewById(R.id.webview_sub);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
        // JavaScript�L����.
        webView.getSettings().setJavaScriptEnabled(true);	// webView.getSettings().setJavaScriptEnabled��JavaScript��L���ɂ���.
        // �f�t�H���g�̃��[�U�G�[�W�F���g���擾.
        mPhoneUA = webView.getSettings().getUserAgentString();	// webView.getSettings().getUserAgentString�Ŏ擾����UA��mPhoneUA�Ɋi�[.(�ŏ��͓d�b�p�Ǝv����̂�, mPhoneUA�Ɋi�[.)
        //Toast.makeText(this, mPhoneUA, Toast.LENGTH_LONG).show();	// mPhoneUA��Toast�ŕ\��.
        mPCUA = generatePCUserAgentString(mPhoneUA);	// mPhoneUA����PC�p���[�U�G�[�W�F���g������𐶐�.
        //Toast.makeText(this, mPCUA, Toast.LENGTH_LONG).show();	// mPCUA��Toast�ŕ\��.
        mCurrentUA = mPhoneUA;	// ���݂̃��[�U�G�[�W�F���g��mPhoneUA�Ƃ���.
        // CustomWebViewClient�̃Z�b�g.
        webView.setWebViewClient(new CustomWebViewClient(this, mTabName));	// new�Ő�������CustomWebViewClient�I�u�W�F�N�g(�R���X�g���N�^�̈�����this��n��.)��webView.setWebViewClient�ŃZ�b�g.
        // CustomWebChromeClient�̃Z�b�g.
        webView.setWebChromeClient(new CustomWebChromeClient(this));	// new�Ő�������CustomWebChromeClient�I�u�W�F�N�g(�R���X�g���N�^�̈�����this��n��.)��webView.setWebChromeClient�ŃZ�b�g.
    	
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
    
    // URL�o�[��URL���Z�b�g.
    public void setUrl(String url){
    	
    	// etUrl���擾��, url���Z�b�g.
    	EditText etUrl = (EditText)findViewById(R.id.edittext_sub_urlbar);	// findViewById��R.id.edittext_sub_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    	if (etUrl == null){
    		//Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
    	}
    	else{
    		etUrl.setText(url);	// etUrl.SetText��URL�o�[��etUrl��url���Z�b�g.
    	}
    	
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
    	EditText etUrl = (EditText)findViewById(R.id.edittext_sub_urlbar);	// findViewById��R.id.edittext_sub_urlbar����EditText�I�u�W�F�N�getUrl���擾.
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
    	WebView webView = (WebView)findViewById(R.id.webview_sub);	// findViewById��R.id.webview_sub����WebView�I�u�W�F�N�gwebView���擾.
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
    
    // �v���O���X�o�[�ɐi���x���Z�b�g.
    public void setProgressValue(int progress){
    	
    	// �v���O���X�o�[���擾��, �w�肳�ꂽ�i���x���Z�b�g.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar_sub);	// findViewById��R.id.progressbar_sub����ProgressBar�I�u�W�F�N�gprogressBar���擾.
    	progressBar.setProgress(progress);	// progressBar.setProgress��progress���Z�b�g.
    	
    }
    
    // �v���O���X�o�[�̕\��/��\�����Z�b�g.
    public void setProgressBarVisible(boolean visible){
    	
    	// �v���O���X�o�[���擾��, �\��/��\�����Z�b�g.
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar_sub);	// findViewById��R.id.progressbar_sub����ProgressBar�I�u�W�F�N�gprogressBar���擾.
    	if (visible){	// true�Ȃ�\��.
    		progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibility��VISIBLE.
    	}
    	else{	// false�Ȃ��\��.
    		//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibility��INVISIBLE.
    		progressBar.setVisibility(View.GONE);	// progressBar.setVisibility��GONE.
    	}
    	
    }
    
    
    // PC�p���[�U�G�[�W�F���g������̍쐬.
    public String generatePCUserAgentString(String phoneUserAgentString){
    
    	// �ŏ��̊��ʂ�T��.
    	int s = phoneUserAgentString.indexOf('(');	// phoneUserAgentString.indexOf�ōŏ��̊��ʂ̈ʒu���擾.
    	int e = phoneUserAgentString.indexOf(')');	// phoneUserAgentString.indexOf�ōŏ��̕����ʂ̈ʒu���擾.
    	if (s < e){	// s���e�̂ق������Ȃ̂ő傫���͂�.
    		String substring = phoneUserAgentString.substring(s, e + 1);	// s����e�܂ł̕�������擾.
    		String pcUA = phoneUserAgentString.replace(substring, PC_WIN_UA_SUBSTRING);	// phoneUserAgentString.replace��PC_WIN_UA_SUBSTRING�ɒu��������.
    		return pcUA;	// pcUA��Ԃ�.
    	}
    	return "";	// �����O�̏ꍇ��""��Ԃ�.
    	
    }
    
}