// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Browser;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TabHost.TabContentFactory;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.OnEditorActionListener;

//���C���A�N�e�B�r�e�B�N���XMainActivity
public class MainActivity extends Activity/*ActivityGroup*//*TabActivity*/ implements /*TabContentFactory,*/ OnEditorActionListener/*Activity*/ /*implements OnClickListener, OnEditorActionListener*/{	// View.OnClickListener, TextView.OnEditorActionListener�C���^�[�t�F�[�X�̒ǉ�.

	// �����o�t�B�[���h�̏�����.
	public static final int REQUEST_CODE_BOOKMARK = 1001;	// REQUEST_CODE_BOOKMARK��1001�Ƃ���.
	public static final int REQUEST_CODE_HISTORY = 1002;	// REQUEST_CODE_HISTORY��1002�Ƃ���.
	public static final int REQUEST_CODE_TAB = 1003;	// REQUEST_CODE_TAB��1003�Ƃ���.
	public static final String SEARCH_URL_GOOGLE = "https://www.google.co.jp/search?q=";	// SEARCH_URL_GOOGLE��"https://www.google.co.jp/search?q="�Ƃ���.
	public DownloadManager mDownloadManager = null;	// mDownloadManager��null�ŏ�����.
	public String mPhoneUA = "";	// �d�b�p���[�U�G�[�W�F���gmPhoneUA.
	public String mPCUA = "";	// PC�p���[�U�G�[�W�F���gmPCUA.
	public String mCurrentUA = "";	// ���݂̃��[�U�G�[�W�F���gmCurrentUA.
	public static final String PC_WIN_UA_SUBSTRING = "(Windows NT 10.0; Win64; x64)";	// WindowsPC�̃��[�U�G�[�W�F���g�ł��邱�Ƃ���������.
	public MainApplication mApp = null;	// MainApplication�I�u�W�F�N�gmApp��null�ŏ�����.
	public String mCurrentTabName = "";	// mCurrentTabName��""�ŏ�����.
	public Context mContext = null;	// mContext��null���Z�b�g.
	public LocalActivityManager mLAM = null;	// mLAM��null���Z�b�g.
	public FrameLayout mFL = null;	// mFL��null���Z�b�g.
	public TabHost mTabHost = null;
	
	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;	// mContext��null���Z�b�g.
        //Toast.makeText(this, "MainActivity.onCreate()", Toast.LENGTH_LONG).show();
        
        // ���C���A�v���P�[�V�����̎擾.
    	mApp = (MainApplication)getApplicationContext();	// getApplicationContext�Ŏ擾����MainApplication�I�u�W�F�N�g��mApp�Ɋi�[.
    	mApp.mMainActivity = this;
    	mTabHost = (TabHost)this.findViewById(android.R.id.tabhost);
    	mTabHost.setup();
    	
    	/*
    	mLAM = getLocalActivityManager();	// mLAM�̎擾.
    	mFL = (FrameLayout)this.findViewById(R.id.frame_main);
    	if (mApp.mHlpr != null){
    		TabInfo tabInfo = mApp.mHlpr.getLastTabInfo();
    		if (tabInfo != null){
    			//Toast.makeText(this, "last", Toast.LENGTH_LONG).show();
    			// SubActivity��Intent�쐬.
    			Intent intent = new Intent(this, SubActivity.class);
    			Bundle args = new Bundle();
    			args.putString("tag", tabInfo.tabName);
    			intent.putExtra("args", args);
    			mLAM.removeAllActivities();
    			Window window = mLAM.startActivity(tabInfo.tabName, intent);
    			View view = window.getDecorView();
    			mFL.addView(view);
    			mCurrentTabName = tabInfo.tabName;
    		}
    		else{
    			//Toast.makeText(this, "new", Toast.LENGTH_LONG).show();
    			registTab();
    			TabInfo ti = mApp.mHlpr.getLastTabInfo();
    			Intent intent = new Intent(this, SubActivity.class);
    			Bundle args = new Bundle();
    			args.putString("tag", ti.tabName);
    			intent.putExtra("args", args);
    			mLAM.removeAllActivities();
    			Window window = mLAM.startActivity(ti.tabName, intent);
    			View view = window.getDecorView();
    			mFL.addView(view);
    			mCurrentTabName = ti.tabName;
    		}
    	}
    	*/
    	/*
    	if (mApp.mHlpr != null){	// mApp.mHlpr��null�łȂ�.
    		//Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
            TabHost tabHost = getTabHost();	// getTabHost��tabHost���擾.
            mApp.mTabHost = tabHost;	// tabHost�������Ă���.
    		List<TabInfo> tabInfoList = mApp.mHlpr.getTabInfoList();
    		if (tabInfoList != null){
    			//Toast.makeText(this, "3", Toast.LENGTH_LONG).show();
    			int last = tabInfoList.size() - 1;	// tabInfo�̐�-1��last�Ɋi�[.
    			for (int i = last, j = 0; i >= 0; i--){	// last����0�܂ŌJ��Ԃ�.
    				//Toast.makeText(this, "4", Toast.LENGTH_LONG).show();
    				TabInfo tabInfo = tabInfoList.get(i);
    				TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabInfo.tabName);	// tabName
    				//tabSpec.setIndicator(tabInfo.title);	// title.
    				final String tag = tabInfo.tabName;
    				final CustomTabWidget widget = new CustomTabWidget(this, tabInfo.title, tabInfo.tabName, new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							//Toast.makeText(mContext, "click(1) : " + tag, Toast.LENGTH_LONG).show();
							removeTab(tag);
						}
						
					});
    				tabSpec.setIndicator(widget);
    		        Intent intent = new Intent(this, SubActivity.class);	// intent�𐶐�.
    		        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    		        //intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    		        Bundle args = new Bundle();	// args�쐬.
    		        args.putString("tag", tabInfo.tabName);	// ("tag", tabInfo.tabName)�œo�^.
    		        args.putBoolean("remove", false);
    		        intent.putExtras(args);	// args�o�^.
    		        //tabSpec.setContent(intent);	// intent���Z�b�g.
    		        tabSpec.setContent(this);
    		        tabHost.addTab(tabSpec);	// tabSpec��ǉ�.
    		        mApp.mTabNameList.add(tabInfo.tabName);
    		        tabHost.setCurrentTab(j);
    		        j++;
    			}
    			//tabHost.setCurrentTab(last);	// �Ō�̃^�u���J�����g�ɃZ�b�g.
    		}
    		else{
    			//Toast.makeText(this, "3b", Toast.LENGTH_LONG).show();
    			registTab();	// registTab�ŐV�K�^�u��o�^.
    			TabInfo tabInfo = mApp.mHlpr.getLastTabInfo();
    			TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabInfo.tabName);	// tabName
    			//tabSpec.setIndicator(tabInfo.title);	// title.
				final String tag = tabInfo.tabName;
				tabInfo.title = tabInfo.tabName;
				final CustomTabWidget widget = new CustomTabWidget(this, tabInfo.title, tabInfo.tabName, new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//Toast.makeText(mContext, "click(2) : " + tag, Toast.LENGTH_LONG).show();
						removeTab(tag);
					}
					
				});
				tabSpec.setIndicator(widget);
		        Intent intent = new Intent(this, SubActivity.class);	// intent�𐶐�.
		        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        //intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		        Bundle args = new Bundle();	// args�쐬.
		        args.putString("tag", tabInfo.tabName);	// ("tag", tabInfo.tabName)�œo�^.
		        args.putBoolean("remove", false);
		        intent.putExtras(args);	// args�o�^.
		        //tabSpec.setContent(intent);	// intent���Z�b�g.
		        tabSpec.setContent(this);
		        tabHost.addTab(tabSpec);	// tabSpec��ǉ�.
		        mApp.mTabNameList.add(tabInfo.tabName);
		        mApp.mHlpr.updateTabInfo(tag, tabInfo);
    		}
    	}
    	*/
    	
    	//Toast.makeText(this, "5", Toast.LENGTH_LONG).show();
        /*
    	// tabHost�̎擾.
        TabHost tabHost = getTabHost();	// getTabHost��tabHost���擾.
        
        // tabSpec�̍쐬.
        TabHost.TabSpec tabSpec = tabHost.newTabSpec("MainTab");	// tabSpec�쐬.        
        // �e�L�X�g�̃Z�b�g.
        tabSpec.setIndicator("MainTab");	// �e�L�X�g��"MainTab".
        // �R���e���c�̃Z�b�g.
        tabSpec.setContent(R.id.main_content);	// R.id.main_content���Z�b�g.
        // �^�u�̒ǉ�.
        tabHost.addTab(tabSpec);	// tabSpec��ǉ�.
        */
        // tabSpec2�̍쐬.(����Œǉ������, �ŏ��̃^�u�̃e�L�X�gMainContent���\������Ȃ�.)
        /*
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("MainTab2");	// tabSpec2�쐬.      
        // �e�L�X�g�̃Z�b�g.
        tabSpec2.setIndicator("MainTab2");	// �e�L�X�g��"MainTab2".
        // �R���e���c�̃Z�b�g.
        View v = (View)findViewById(R.id.main_content);	// v���擾.
        TextView tv = (TextView)v.findViewById(R.id.textview1);	// tv���擾.
        tv.setText("MainContent2");	// �e�L�X�g��ύX.
        tabSpec2.setContent(R.id.main_content);	// R.id.main_content���Z�b�g.
        // �^�u�̒ǉ�.
        tabHost.addTab(tabSpec2);	// tabSpec2��ǉ�.
        */
        /*
        // tabSpec2�̍쐬.(Intent��Activity��ǉ�.)
        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("MainTab2");	// tabSpec2�쐬.
        // �e�L�X�g�̃Z�b�g.
        tabSpec2.setIndicator("MainTab2");	// �e�L�X�g��"MainTab2".
        // �R���e���c�̃Z�b�g.
        tabSpec2.setContent(new Intent(this, SubActivity.class));	// SubActivity���Z�b�g.
        // �^�u�̒ǉ�.
        tabHost.addTab(tabSpec2);	// tabSpec2��ǉ�.
        
        // tabSpec3�̍쐬.(Intent��Activity��ǉ�.)
        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("MainTab3");	// tabSpec3�쐬.
        // �e�L�X�g�̃Z�b�g.
        tabSpec3.setIndicator("MainTab3");	// �e�L�X�g��"MainTab3".
        // �R���e���c�̃Z�b�g.
        Intent intent = new Intent(this, SubActivity.class);	// intent�𐶐�.
        Bundle args = new Bundle();	// args�쐬.
        args.putString("tag", "Activity3");	// ("tag", "Activity3")�œo�^.
        intent.putExtras(args);	// args�o�^.
        tabSpec3.setContent(intent);	// intent���Z�b�g.
        // �^�u�̒ǉ�.
        tabHost.addTab(tabSpec3);	// tabSpec3��ǉ�.
        */
        
    	/*
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_main);	// setContentView��R.layout.activity_main���Z�b�g.
        initUrlBar();	// initUrlBar��etUrl��������.
        initProgressBar();	// initProgressBar��progressbar��������.
        initWebView();	// initWebView��webView��������.
        initDownloadManager();	// initDownloadManager��mDownloadManager��������.
        //loadUrlFromIntent();	// loadUrlFromIntent�ŃC���e���g�Ŏw�肳�ꂽURL�����[�h.
        initMainApplication();	// initMainApplication�Ń��C���A�v���P�[�V������������.
        */
        
    }
    
    /*
    @Override
    public View createTabContent(String tag){
    	//Toast.makeText(this, "createTabContent tag = " + tag, Toast.LENGTH_LONG).show();
    	LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
    	View view = inflater.inflate(R.layout.activity_sub, null);
    	initUrlBar2(view);
    	initProgressBar2(view);
    	initWebView2(view, tag);
    	
    	// �^�u�����^�u�}�b�v����擾.
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		//Toast.makeText(this, "TabMapExist", Toast.LENGTH_LONG).show();
    		return ti.view;
    	}
    	else{
    		// �^�u�����^�uDB����擾.
    		TabInfo ti2 = mApp.mHlpr.getTabInfo(tag);
    		if (ti2 != null){
    			//Toast.makeText(this, "TabDBExist", Toast.LENGTH_LONG).show();
    			ti2.view = view;
    			mApp.mTabMap.put(tag, ti2);
    			if (ti2.url != null){
	        		if (!ti2.url.equals("")){
	        			setUrlOmit(ti2.url, tag);
	        			loadUrl();
	        		}
    			}
    			return ti2.view;
    		}
    		else{
    			//Toast.makeText(this, "TabNotExist", Toast.LENGTH_LONG).show();
    			return view;
    		}
    	}    	
    }
    */
    
    // URL�o�[�̏�����.
    public void initUrlBar2(View view){
    	
    	// etUrl���擾��, OnEditorActionListener�Ƃ��Ď��g(this)���Z�b�g.
    	EditText etUrl = (EditText)view.findViewById(R.id.edittext_sub_urlbar);	// findViewById��R.id.edittext_sub_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    	etUrl.setOnEditorActionListener(this);	// etUrl.setOnEditorActionListene��this(���g)���Z�b�g.
    	
    }
    
    // �v���O���X�o�[�̏�����.
    public void initProgressBar2(View view){
    	
    	// progressBar���擾��, �ŏ��͔�\���ɂ��Ă���.
    	ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progressbar_sub);	// findViewById��R.id.progressbar����ProgressBar�I�u�W�F�N�gprogressBar���擾.
    	//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibility�Ŕ�\���ɂ���.
    	progressBar.setVisibility(View.GONE);	// progressBar.setVisibility�Ŕ�\��(View.GONE)�ɂ���.
    	//progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibility�ōŏ�����\���ɂ���.
    }
    
    // �E�F�u�r���[�̏�����.
    public void initWebView2(View view, String tag){
    	
    	// webView�̎擾.
        WebView webView = (WebView)view.findViewById(R.id.webview_sub);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
        // JavaScript�L����.
        webView.getSettings().setJavaScriptEnabled(true);	// webView.getSettings().setJavaScriptEnabled��JavaScript��L���ɂ���.
        // �f�t�H���g�̃��[�U�G�[�W�F���g���擾.
        mPhoneUA = webView.getSettings().getUserAgentString();	// webView.getSettings().getUserAgentString�Ŏ擾����UA��mPhoneUA�Ɋi�[.(�ŏ��͓d�b�p�Ǝv����̂�, mPhoneUA�Ɋi�[.)
        //Toast.makeText(this, mPhoneUA, Toast.LENGTH_LONG).show();	// mPhoneUA��Toast�ŕ\��.
        mPCUA = generatePCUserAgentString(mPhoneUA);	// mPhoneUA����PC�p���[�U�G�[�W�F���g������𐶐�.
        //Toast.makeText(this, mPCUA, Toast.LENGTH_LONG).show();	// mPCUA��Toast�ŕ\��.
        mCurrentUA = mPhoneUA;	// ���݂̃��[�U�G�[�W�F���g��mPhoneUA�Ƃ���.
        // CustomWebViewClient�̃Z�b�g.
        webView.setWebViewClient(new CustomWebViewClient(this, tag));	// new�Ő�������CustomWebViewClient�I�u�W�F�N�g(�R���X�g���N�^�̈�����this��n��.)��webView.setWebViewClient�ŃZ�b�g.
        // CustomWebChromeClient�̃Z�b�g.
        webView.setWebChromeClient(new CustomWebChromeClient(this, tag));	// new�Ő�������CustomWebChromeClient�I�u�W�F�N�g(�R���X�g���N�^�̈�����this��n��.)��webView.setWebChromeClient�ŃZ�b�g.
    	
    }
    
    // �����̃C���X�^���X���ė��p����, �C���e���g�����ŗ�����.
    @Override
    protected void onNewIntent(Intent intent){
    	
    	// OnNewIntent�ɗ��������g�[�X�g�ŕ\��.
    	//Toast.makeText(this, "OnNewIntent", Toast.LENGTH_LONG).show();	// "OnNewIntent"��Toast�ŕ\��.
    	
    }
    
    // �o�b�N�L�[�������ꂽ��.
    @Override
    public void onBackPressed(){
    	
    	//Toast.makeText(this, "Main", Toast.LENGTH_LONG).show();
    	
    	// �o�b�N�L�[�ɂ�����E�F�u�r���[�̓���.
    	onBackPressedWebView();	// onBackPressedWebView�ɔC����.
    	
    }
    
    // �N�������A�N�e�B�r�e�B�̌��ʂ��Ԃ��ė�����.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	
    	//Toast.makeText(this, "0a", Toast.LENGTH_LONG).show();
    	// ����̏���.
    	super.onActivityResult(requestCode, resultCode, data);	// �e�N���X��onActivityResult���Ă�.
    	//Toast.makeText(this, "0b", Toast.LENGTH_LONG).show();
    	// �L�����Z���̏ꍇ.
    	if (requestCode != REQUEST_CODE_TAB && resultCode == RESULT_CANCELED){	// requestCode��REQUEST_CODE_TAB�ł͂Ȃ�, resultCode��RESULT_CANCELED�̏ꍇ.
    		//Toast.makeText(this, "0c", Toast.LENGTH_LONG).show();
    		return;	// ���������I��.
    	}
    	//Toast.makeText(this, "0d", Toast.LENGTH_LONG).show();
    	
    	// �N�������A�N�e�B�r�e�B���������̌��ʂɑ΂��鏈��.
    	Bundle bundle = null;	// Bundle�^bundle��null�ŏ�����.
    	if (data != null){	// data��null�łȂ����.
    		bundle = data.getExtras();	// data.getExtras��bundle���擾.
    	}
    	//Toast.makeText(this, "0e", Toast.LENGTH_LONG).show();
    	
    	// �����̐U�蕪��.
    	switch (requestCode){	// requestCode���ƂɐU�蕪��.
    	
    		// �u�b�N�}�[�N�ꗗ.
    		case REQUEST_CODE_BOOKMARK:	// �u�b�N�}�[�N�̈ꗗ����߂��Ă����ꍇ.
    		// �����ꗗ.
    		case REQUEST_CODE_HISTORY:	// �����̈ꗗ����߂��Ă����ꍇ.
    			
    			// �u�b�N�}�[�N����ї����̃A�C�e�����I�����ꂽ��.
    			if (resultCode == RESULT_OK){	// RESULT_OK�̏ꍇ.
    				if (bundle != null){	// bundle��null�łȂ����.
    					loadSelectedUrl(bundle);	// loadSelectedUrl��bundle�œn����URL�����[�h.
    				}
    			}
    			
    			// ������.
    			break;	// break�Ŕ�����.
    		
    		// �^�u�ꗗ.
    		case REQUEST_CODE_TAB:	// �^�u�ꗗ����߂��Ă����ꍇ.
    			//Toast.makeText(this, "0", Toast.LENGTH_LONG).show();
    			// �^�u�̃A�C�e�����I�����ꂽ�Ƃ�.
    			if (resultCode == RESULT_OK){	// RESULT_OK�̏ꍇ.
    				if (bundle != null){	// bundle��null�łȂ����.
    					String tabName = bundle.getString("tabName");	// bundle����tabName���擾.
    					//String title = bundle.getString("title");	// bundle����title���擾.
    					if (mApp.mTabMap.containsKey(tabName)){	// �^�u�}�b�v�ɂ���ꍇ.
    						//setContentViewByTabName(tabName, title);	// setContentViewByTabName��tabName����r���[���Z�b�g.(�^�C�g�����Z�b�g���Ă���.)
    						setViewByTabName(tabName);
    					}
    					else{	// �}�b�v�ɂ͖����ꍇ.
    						// �^�uDB����̎擾.
    						TabInfo ti = mApp.mTabMap.get(mCurrentTabName);
    				    	ti.date = System.currentTimeMillis();
    				    	ti.view = mFL.findViewById(R.id.layout_sub);
    				    	mApp.mHlpr.updateTabInfo(mCurrentTabName, ti);
    				    	
    						TabInfo tabInfo = mApp.mHlpr.getTabInfo(tabName);	// tabName����tabInfo�擾
    						if (tabInfo != null){	// DB�ɂ͂���ꍇ.
    							Intent intent = new Intent(this, SubActivity.class);
    			    			Bundle args = new Bundle();
    			    			args.putString("tag", tabInfo.tabName);
    			    			intent.putExtra("args", args);
    			    			mFL.removeAllViews();
    			    			Window window = mLAM.startActivity(tabInfo.tabName, intent);
    			    			View view = window.getDecorView();
    			    			mFL.addView(view);
    			    			mCurrentTabName = tabInfo.tabName;
    			    			ActionBar act = getActionBar();
    			        		if (act != null){
    			        			act.setTitle(tabInfo.title);
    			        		}
    							/*
    							// ���C���A�N�e�B�r�e�B���N������.(�^�u�𕜌�����.)
	    				    	String packageName = getPackageName();	// getPackageName��packageName���擾.
	    				    	Intent intent = new Intent();	// Intent�I�u�W�F�N�gintent���쐬.
	    				    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassName��".MainActivity"���Z�b�g.
	    				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// ���ꂾ�ƋN������A�N�e�B�r�e�B�ȊO�͔j�������.
	    				    	intent.putExtra("LaunchMode", "RestoreTab");	// "LaunchMode"���L�[, "RestoreTab"��l�Ƃ��ēo�^.
	    				    	intent.putExtra("TabName", tabInfo.tabName);	// "TabName"��tabinfo.tabName��o�^.
	    				    	intent.putExtra("Url", tabInfo.url);	// "Url"���L�[�ɂ���, tabInfo.url�𑗂�.
	    				    	startActivity(intent);	// startActivity��intent��n��.
	    				    	*/
    						}
    						/*
    						else{	// DB�ɂ��Ȃ��ꍇ.(�����ɂ��邱�Ƃ͂Ȃ�.)
	    						// ���C���A�N�e�B�r�e�B���N������.(�^�u�̐V�K�쐬����.)
	    				    	String packageName = getPackageName();	// getPackageName��packageName���擾.
	    				    	Intent intent = new Intent();	// Intent�I�u�W�F�N�gintent���쐬.
	    				    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassName��".MainActivity"���Z�b�g.
	    				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// ���ꂾ�ƋN������A�N�e�B�r�e�B�ȊO�͔j�������.
	    				    	intent.putExtra("LaunchMode", "NewTab");	// "LaunchMode"���L�[, "NewTab"��l�Ƃ��ēo�^.
	    				    	startActivity(intent);	// startActivity��intent��n��.
    						}
    						*/
    					}
    				}
    			}
    			else if (resultCode == RESULT_CANCELED){	// RESULT_CANCELED�̏ꍇ.
    				//Toast.makeText(this, "1", Toast.LENGTH_LONG).show();
    				if (!mApp.mTabMap.containsKey(mCurrentTabName)){
    					//Toast.makeText(this, "2", Toast.LENGTH_LONG).show();
    					TabInfo tabInfo = mApp.mHlpr.getLastTabInfo();	// �����ꂽ�J�����g�^�u�ȊO�Œ��߂̃^�u��T��.
    					if (tabInfo != null){	// ���߃^�u���݂�����.
    						//Toast.makeText(this, "3", Toast.LENGTH_LONG).show();
    						TabInfo ti = mApp.mTabMap.get(tabInfo.tabName);	// �}�b�v����擾.
    						if (ti != null){	// �}�b�v�ɂ�����.
    							//Toast.makeText(this, "map exist", Toast.LENGTH_LONG).show();
    							// SubActivity��Intent�쐬.
    							Intent intent = new Intent(this, SubActivity.class);
    							Bundle args = new Bundle();
    							args.putString("tag", ti.tabName);
    							intent.putExtra("args", args);
    							mFL.removeAllViews();
    							Window window = mLAM.startActivity(ti.tabName, intent);
    							View view = window.getDecorView();
    							mFL.addView(view);
    							mCurrentTabName = ti.tabName;
    							ActionBar act = getActionBar();
    				    		if (act != null){
    				    			if (ti.title.equals("")){
    				    				act.setTitle("Zinc");
    				    			}
    				    			else{
    				    				act.setTitle(ti.title);
    				    			}
    				    		}    				    		
    						}
    						else{	// �}�b�v�ɖ���.
    							
    							//Toast.makeText(this, "db last", Toast.LENGTH_LONG).show();
    			    			// SubActivity��Intent�쐬.
    			    			Intent intent = new Intent(this, SubActivity.class);
    			    			Bundle args = new Bundle();
    			    			args.putString("tag", tabInfo.tabName);
    			    			intent.putExtra("args", args);
    			    			mFL.removeAllViews();
    			    			Window window = mLAM.startActivity(tabInfo.tabName, intent);
    			    			View view = window.getDecorView();
    			    			mFL.addView(view);
    			    			mCurrentTabName = tabInfo.tabName;
    			    			
    						}
    					}
    					else{	// �^�u���Ȃ�.
    						
    						//Toast.makeText(this, "all delete new", Toast.LENGTH_LONG).show();
    		    			registTab();
    		    			TabInfo ti2 = mApp.mHlpr.getLastTabInfo();
    		    			Intent intent = new Intent(this, SubActivity.class);
    		    			Bundle args = new Bundle();
    		    			args.putString("tag", ti2.tabName);
    		    			intent.putExtra("args", args);
    		    			mLAM.removeAllActivities();
    		    			Window window = mLAM.startActivity(ti2.tabName, intent);
    		    			View view = window.getDecorView();
    		    			mFL.addView(view);
    		    			mCurrentTabName = ti2.tabName;
    		    			ActionBar act = getActionBar();
				    		if (act != null){
				    			if (ti2.title.equals("")){
				    				act.setTitle("Zinc");
				    			}
				    			else{
				    				act.setTitle(ti2.title);
				    			}
				    		}   
				    		
    					}
    					
    				}
    				/*
    				if (!mApp.mTabMap.containsKey(mCurrentTabName)){	// �\�����Ă����^�u����������.
    					// ���߃^�u���ǂ���.
    					TabInfo tabInfo = mApp.mHlpr.getLastTabInfo();	// ���߂̃^�u���擾.
    					if (tabInfo != null){	// 1�݂͂�����.
	    					if (mApp.mTabMap.containsKey(tabInfo.tabName)){	// �}�b�v�ɂ���ꍇ.
	     				        setContentViewByTabName(tabInfo.tabName, tabInfo.title);	// tabInfo�̃r���[���Z�b�g.
	    					}
	    					else{	// �}�b�v�ɖ���.
	    						// ���C���A�N�e�B�r�e�B���N������.(�^�u�𕜌�����.)
	    				    	String packageName = getPackageName();	// getPackageName��packageName���擾.
	    				    	Intent intent = new Intent();	// Intent�I�u�W�F�N�gintent���쐬.
	    				    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassName��".MainActivity"���Z�b�g.
	    				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// ���ꂾ�ƋN������A�N�e�B�r�e�B�ȊO�͔j�������.
	    				    	intent.putExtra("LaunchMode", "RestoreTab");	// "LaunchMode"���L�[, "RestoreTab"��l�Ƃ��ēo�^.
	    				    	intent.putExtra("TabName", tabInfo.tabName);	// "TabName"��tabinfo.tabName��o�^.
	    				    	intent.putExtra("Url", tabInfo.url);	// "Url"���L�[�ɂ���, tabInfo.url�𑗂�.
	    				    	startActivity(intent);	// startActivity��intent��n��.
	    					}
    					}
    					else{	// 1���Ȃ�.
    						// ���C���A�N�e�B�r�e�B���N������.(�^�u�̐V�K�쐬����.)
    				    	String packageName = getPackageName();	// getPackageName��packageName���擾.
    				    	Intent intent = new Intent();	// Intent�I�u�W�F�N�gintent���쐬.
    				    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassName��".MainActivity"���Z�b�g.
    				    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// ���ꂾ�ƋN������A�N�e�B�r�e�B�ȊO�͔j�������.
    				    	intent.putExtra("LaunchMode", "NewTab");	// "LaunchMode"���L�[, "NewTab"��l�Ƃ��ēo�^.
    				    	startActivity(intent);	// startActivity��intent��n��.
    					}
    				}
    				*/
    				
    				// ���̂܂܏I���΋A���Ă����Ƃ�, �^�u�͍Đ�������Ȃ�.
    				//Toast.makeText(mContext, "RESULT_CANCEL", Toast.LENGTH_LONG).show();
    				
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
    	if (id == R.id.menu_item_new_tab){	// R.id.menu_item_new_tab("�V�����^�u")�̎�.
    		
    		//�@�V�����^�u�̒ǉ�.
    		//addTab();	// addTab�Œǉ�.
    		//addTabToActivity();	// addTabToActivity�Œǉ�.
    		addTabToLAM();
    		
    	}
    	else if (id == R.id.menu_item_tabs_show){	// R.id.menu_item_tabs_show("�^�u�ꗗ�̕\��")�̎�.

    		// �^�u�ꗗ�̕\��.
    		showTabs();	// showTabs�Œǉ�.
    		
    	}
    	/*
    	else if (id == R.id.menu_item_remove_tab){	// R.id.menu_item_remove_tab("�^�u�̍폜")�̎�.
    		
    		// �^�u�̍폜.
    		removeTab();	// removeTab�ō폜.
    		
    	}
    	*/
    	else if (id == R.id.menu_item_bookmark_add){	// R.id.menu_item_bookmark_add("�u�b�N�}�[�N�̒ǉ�")�̎�.

    		// �u�b�N�}�[�N�̒ǉ�.
    		addBookmarkToDB();	// addBookmarkToDB�Œǉ�.
    		
    	}
    	else if (id == R.id.menu_item_bookmark_show){	// R.id.menu_item_bookmark_show("�u�b�N�}�[�N�̈ꗗ")�̎�.
    	
    		// �u�b�N�}�[�N�̕\��.
    		showBookmark();	// showBookmark�ŕ\��.
    		
    	}
    	else if (id == R.id.menu_item_history_show){	// R.id.menu_item_history_show("�����̈ꗗ")�̎�.
    		
    		// �����̕\��.
    		showHistory();	// showHistory�ŕ\��.
    		
    	}
    	/*
    	else if (id == R.id.menu_item_download){	// R.id.menu_item_download("�_�E�����[�h")�̎�.
    		
    		// �_�E�����[�h.
    		download();	// download�Ń_�E�����[�h.
    		
    	}
    	else if (id == R.id.menu_item_pc_site_browser){	// R.id.menu_item_pc_site_browser("PC�T�C�g�u���E�U")�̎�. 

    		// �\���̐؂�ւ�.
    		changePhonePCSite(item);	// changePhonePCSite��item��n��.
    		
    	}
    	*/
    	
    	// ���Ƃ͊���̏����ɔC����.
    	return super.onOptionsItemSelected(item);	// �e�N���X��onOptionsItemSelected���Ă�.
    	
    }
    
    // �{�^���������ꂽ��.
    /*
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
    */
    
    // �G�f�B�b�g�e�L�X�g��Enter�L�[�������ꂽ��.
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
    	
    	// Enter�L�[�������ꂽ��.
    	if (actionId == EditorInfo.IME_ACTION_DONE){	// Enter�L�[�������ꂽ��.(IME_ACTION_DONE)
    		
    		// �\�t�g�E�F�A�L�[�{�[�h�̔�\��.
    		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);	// getSystemService����inputMethodManager���擾.
    		inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);	// inputMethodManager.hideSoftInputFromWindow�Ŕ�\��.
    		
    		// URL�̃��[�h.
    		//loadUrl();	// loadUrl��URL�o�[��URL�����[�h.
    	
    		// true��Ԃ�.
    		return true;	// return��true��Ԃ�.
    		
    	}
    	
    	// false��Ԃ�.
    	return false;	// return��false��Ԃ�.
    	
    }
    
    // URL�o�[�̏�����.
    public void initUrlBar(){
    	
    	// etUrl���擾��, OnEditorActionListener�Ƃ��Ď��g(this)���Z�b�g.
    	/*
    	EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    	etUrl.setOnEditorActionListener(this);	// etUrl.setOnEditorActionListene��this(���g)���Z�b�g.
    	*/
    }
    
    // �v���O���X�o�[�̏�����.
    public void initProgressBar(){
    	
    	// progressBar���擾��, �ŏ��͔�\���ɂ��Ă���.
    	/*
    	ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewById��R.id.progressbar����ProgressBar�I�u�W�F�N�gprogressBar���擾.
    	//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibility�Ŕ�\���ɂ���.
    	progressBar.setVisibility(View.GONE);	// progressBar.setVisibility�Ŕ�\��(View.GONE)�ɂ���.
    	//progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibility�ōŏ�����\���ɂ���.
    	*/
    }
    
    // �E�F�u�r���[�̏�����.
    public void initWebView(){
    	
    	/*
    	// webView�̎擾.
        WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
        // JavaScript�L����.
        webView.getSettings().setJavaScriptEnabled(true);	// webView.getSettings().setJavaScriptEnabled��JavaScript��L���ɂ���.
        // �f�t�H���g�̃��[�U�G�[�W�F���g���擾.
        mPhoneUA = webView.getSettings().getUserAgentString();	// webView.getSettings().getUserAgentString�Ŏ擾����UA��mPhoneUA�Ɋi�[.(�ŏ��͓d�b�p�Ǝv����̂�, mPhoneUA�Ɋi�[.)
        //Toast.makeText(this, mPhoneUA, Toast.LENGTH_LONG).show();	// mPhoneUA��Toast�ŕ\��.
        mPCUA = generatePCUserAgentString(mPhoneUA);	// mPhoneUA����PC�p���[�U�G�[�W�F���g������𐶐�.
        //Toast.makeText(this, mPCUA, Toast.LENGTH_LONG).show();	// mPCUA��Toast�ŕ\��.
        mCurrentUA = mPhoneUA;	// ���݂̃��[�U�G�[�W�F���g��mPhoneUA�Ƃ���.
        // CustomWebViewClient�̃Z�b�g.
        webView.setWebViewClient(new CustomWebViewClient(this));	// new�Ő�������CustomWebViewClient�I�u�W�F�N�g(�R���X�g���N�^�̈�����this��n��.)��webView.setWebViewClient�ŃZ�b�g.
        // CustomWebChromeClient�̃Z�b�g.
        webView.setWebChromeClient(new CustomWebChromeClient(this));	// new�Ő�������CustomWebChromeClient�I�u�W�F�N�g(�R���X�g���N�^�̈�����this��n��.)��webView.setWebChromeClient�ŃZ�b�g.
        */
    	
    }
    
    // �_�E�����[�h�}�l�[�W���[�̏�����.
    public void initDownloadManager(){
    	
    	// DownloadManager�̎擾.
    	if (mDownloadManager == null){	// mDownloadManager��null�̎�.
    		mDownloadManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);	// getSystemService��mDownloadManager���擾.
    	}
    	
    }
    
    // ���C���A�v���P�[�V�����̏�����.
    public void initMainApplication(){
    
    	// ���C���A�v���P�[�V�����̎擾.
    	mApp = (MainApplication)getApplicationContext();	// getApplicationContext�Ŏ擾����MainApplication�I�u�W�F�N�g��mApp�Ɋi�[.
    	//String tabName = getTabNameFromIntent();	// tabName�擾.
    	//if (tabName == null){	// tabName�͎w�肳��Ă��Ȃ�.
    	//registTabMap();	// registTabMap�Œǉ�.
    	String launchMode = getTabLaunchModeFromIntent();	// getTabLaunchModeFromIntent�ŋN�����[�h�擾.
    	if (launchMode != null && launchMode.equals("NewTab")){
    		registTab();	// registTab�ŐV�K�^�u��o�^.
    	}
    	else if (launchMode != null && launchMode.equals("RestoreTab")){
    		String tabName = getIntent().getExtras().getString("TabName");	// tabName�擾.
    		TabInfo tabInfo = mApp.mHlpr.getTabInfo(tabName);	// tabName����tabInfo�擾.
    		loadTab(tabInfo);	// loadTab��DB��̃^�u�𕜌�.
    	}
    	else{
    		TabInfo lastTabInfo = mApp.mHlpr.getLastTabInfo();	// ���߂̃^�u���擾.
    		if (lastTabInfo == null){	// lastTabInfo��null�̎�.
    			registTab();	// registTab�ŐV�K�^�u��o�^.
    		}	
    		else{
    			loadTab(lastTabInfo);	// loadTab��DB��̃^�u�𕜌�.
    		}
    	}
    	
    	//}
    	//else{	// tabName������ꍇ.
    	//	setContentViewByTabName(tabName, "Zinc");	// setContentViewByTabName�Ńr���[���Z�b�g.(�^�C�g����"Zinc"�ɂ��Ă���.)
    	//}
    	
    }
    
    // �^�u����t���ċN�����ꂽ�ꍇ�̃^�u�����擾.
    public String getTabNameFromIntent(){
    	
    	// �C���e���g�ɃZ�b�g����Ă���N���^�u����Ԃ�.
    	Intent intent = getIntent();	// getIntent��intent�擾.
    	String tabName = intent.getStringExtra("tabName");	// intent.getStringExtra��tabName�擾.
    	return tabName;	// return��tabName��Ԃ�.
    	
    }
    
    // �^�u�̋N�����[�h���擾.
    public String getTabLaunchModeFromIntent(){
    	
    	// �C���e���g�ɃZ�b�g���ꂽ�N�����[�h��Ԃ�.
    	Intent intent = getIntent();
    	String launchMode = intent.getStringExtra("LaunchMode");	// intent.getStringExtra��"LaunchMode"���擾.
    	return launchMode;	// return��launchMode��Ԃ�.
    	
    }
    
    // �^�u���ƃr���[�̃y�A���^�u�}�b�v�ɓo�^.
    public void registTabMapx(){
    
    	// ���݂̃^�u��V�K�쐬��, �r���[�}�b�v�ɒǉ�.
    	/*
    	//mCurrentTabName = "web" + String.valueOf(mApp.mNextViewNo);	// ���݂̃^�u����V�K�쐬.
		View rootView = getWindow().getDecorView();	// getWindow().getDecorView��rootView���擾.
		View content = rootView.findViewById(R.id.layout_main);	// rootView����layout_main�𔲂��o��.
		TabInfo tabInfo = new TabInfo();	// tabInfo���쐬.
		tabInfo.tabName = mCurrentTabName;	// tabName���Z�b�g.
		tabInfo.title = "";	// �Ƃ肠������.
		tabInfo.url = "";	// �Ƃ肠������.
		tabInfo.date = System.currentTimeMillis();	// ���ݎ������Z�b�g.
		tabInfo.view = content;	//content���Z�b�g.
		mApp.mTabMap.put(mCurrentTabName, tabInfo);	// tabInfo��o�^.
		//mApp.mNextViewNo++;	// mApp.mNextViewNo�𑝂₷.
		*/
    }
    
    // �^�u�̓o�^.
    public void registTab(){
    	
    	// DB���̍X�V.
    	long datemillisec = System.currentTimeMillis();	// datemillisec���擾.
    	long id = mApp.mHlpr.insertRowTab("", "", "", datemillisec);	// mApp.mHlpr.insertRowTab�ł܂��͋�̃^�u���œo�^��, id���擾.
    	String currentTabName = "web" + String.valueOf(id);	// ���݂̃^�u�����쐬.
    	boolean b = mApp.mHlpr.updateTabName(id, currentTabName);	// �^�u���̍X�V.
    	//boolean c;
    	//c = b;
    	
    	// �}�b�v���̍X�V.
    	/*
    	View rootView = getWindow().getDecorView();	// getWindow().getDecorView��rootView���擾.
		View content = rootView.findViewById(R.id.layout_main);	// rootView����layout_main�𔲂��o��.
		TabInfo tabInfo = new TabInfo();	// tabInfo���쐬.
		tabInfo.tabName = currentTabName;	// tabName���Z�b�g.
		tabInfo.title = "";	// �Ƃ肠������.
		tabInfo.url = "";	// �Ƃ肠������.
		tabInfo.date = datemillisec;	// ���ݎ������Z�b�g.
		tabInfo.view = content;	//content���Z�b�g.
		mApp.mTabMap.put(currentTabName, tabInfo);	// tabInfo��o�^.
		mCurrentTabName = currentTabName;	// mCurrentTabName�ɃZ�b�g.
		*/
    }
    
    // �^�u��񂩂�^�u�𐶐�.
    public void loadTab(TabInfo tabInfo){
    	
    	// �}�b�v���̍X�V.
    	/*
    	View rootView = getWindow().getDecorView();	// getWindow().getDecorView��rootView���擾.
		View content = rootView.findViewById(R.id.layout_main);	// rootView����layout_main�𔲂��o��.
		tabInfo.date = System.currentTimeMillis();	// ���ݎ������Z�b�g.
		tabInfo.view = content;	//content���Z�b�g.
		setUrlOmit(tabInfo.url);	// tabInfo.url��setUrlOmit�ŃZ�b�g.
		if (!tabInfo.url.equals("")){	// ""�łȂ����.
			loadUrl();	// loadUrl�Ń��[�h.
		}
		mCurrentTabName = tabInfo.tabName;	// tabInfo.tabName���Z�b�g.
		mApp.mTabMap.put(mCurrentTabName, tabInfo);	// tabInfo��o�^.
		*/
    }
    
    // �^�u������擾�����r���[���Z�b�g.
    public void setViewByTabName(String tabName){
    	
    	TabInfo tabInfo = mApp.mTabMap.get(mCurrentTabName);
    	tabInfo.date = System.currentTimeMillis();
    	tabInfo.view = mFL.findViewById(R.id.layout_sub);
    	mApp.mHlpr.updateTabInfo(mCurrentTabName, tabInfo);
    	
    	TabInfo tabInfo2 = mApp.mTabMap.get(tabName);
    	if (tabInfo2 != null){    		
    		//Toast.makeText(this, "change", Toast.LENGTH_LONG).show();
			// SubActivity��Intent�쐬.
			Intent intent = new Intent(this, SubActivity.class);
			Bundle args = new Bundle();
			args.putString("tag", tabInfo2.tabName);
			intent.putExtra("args", args);
			mFL.removeAllViews();
			Window window = mLAM.startActivity(tabInfo2.tabName, intent);
			View view = window.getDecorView();
			mFL.addView(view);
			mCurrentTabName = tabInfo2.tabName;
			ActionBar act = getActionBar();
    		if (act != null){
    			if (tabInfo2.title.equals("")){
    				act.setTitle("Zinc");
    			}
    			else{
    				act.setTitle(tabInfo2.title);
    			}
    		}
    	}
    	
    }
    
    // �^�u������擾�����r���[���Z�b�g.
    public void setContentViewByTabName(String tabName, String title){
    
    	// tabName����content���擾��, �Z�b�g.
    	TabInfo tabInfo = mApp.mTabMap.get(tabName);	// mApp.mTabMap.get��tabInfo���擾.
    	if (tabInfo != null){	// tabInfo��null�łȂ����.
    		View content = tabInfo.view;	// tabInfo.view��content�ɃZ�b�g.
    		ViewGroup vg = (ViewGroup)content.getParent();	// content.getParent�ł�������e��ViewGroup�擾.
			if (vg != null){	// null�łȂ����.(null�̏ꍇ�ŗ����Ă��̂�, null�̏ꍇ�͍폜���Ȃ�.)
				vg.removeView(content);	// removeView����ƃG���[���o���̂�, vg.removeView�ō폜.
			}
			setContentView(content);	// setContentView��content���Z�b�g.
			initUrlBar();	// initUrlBar��etUrl��������.
	        initProgressBar();	// initProgressBar��progressbar��������.
	        initWebView();	// initWebView��webView��������.
	        if (title.equals("")){	// title����.
	        	setActionBarTitle(tabInfo.title);	// setActionBarTitle��tabInfo.title���Z�b�g.
	        }
	        else{
	        	setActionBarTitle(title);	// setActionBar��title���Z�b�g.
	        }
			mCurrentTabName = tabName;	// ���݂̃^�u���Ƃ���.
    	}
    	/*
		else{
			//registTabMap();	// �������͐���.
			registTab();	// registTab�ŐV�K�^�u��o�^.
		}
		*/
		
    }
    
    // URL�o�[��URL���Z�b�g.
    public void setUrl(String url, String tag){
    	
    	// etUrl���擾��, url���Z�b�g.
    	//EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
				EditText etUrl = (EditText)ti.view.findViewById(R.id.edittext_sub_urlbar);
				if (etUrl == null){
					//Toast.makeText(this, "null", Toast.LENGTH_LONG).show();
				}
				else{
					etUrl.setText(url);	// etUrl.SetText��URL�o�[��etUrl��url���Z�b�g.
				}
    		}
    	}
    	
    }
    
    /*
    // �J�����g�^�u��URL���Z�b�g.
    public void setUrlOmit(String url){
    	
    	// �J�����g�^�u�ɃZ�b�g.
    	String tag = this.getTabHost().getCurrentTabTag();
    	setUrlOmit(url ,tag);
    	
    }
    */
    
    // URL�o�[��URL���Z�b�g���鎞��"http"�̏ꍇ�͏ȗ�����.
    public void setUrlOmit(String url, String tag){
    	
    	// �擪�����񂩂�ȗ����邩�𔻒�.
    	if (url.startsWith("http://")){	// "http"�̎�.
    		String omitUrl = url.substring(7);	// url.substring��7�����ڂ���̕������omitUrl�ɕԂ�.
    		setUrl(omitUrl, tag);	// setUrl��omitUrl���Z�b�g.
    	}
    	else{
    		setUrl(url, tag);	// setUrl�ł��̂܂�url��n��.
    	}
    	
    }
    
    /*
    // URL�o�[����URL���擾.
    public String getUrl(){
    	
    	// etUrl��URL���擾.
    	String tag = this.getTabHost().getCurrentTabTag();
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
    			EditText etUrl = (EditText)ti.view.findViewById(R.id.edittext_sub_urlbar);	// findViewById��R.id.edittext_sub_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    	    	return etUrl.getText().toString();	// etUrl.getText().toString��URL��Ԃ�.
    		}
    	}
    	return "";
    	
    }
    */
    
    // �E�F�u�r���[����^�C�g�����擾.
    public String getWebTitle(){
    	SubActivity subActivity = (SubActivity)mLAM.getActivity(mCurrentTabName);
    	WebView webView = (WebView)subActivity.findViewById(R.id.webview_sub);
    	return webView.getTitle();
    	/*
    	// WebView��URL���擾.
    	String tag = this.getTabHost().getCurrentTabTag();
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
    			WebView webView = (WebView)ti.view.findViewById(R.id.webview_sub);
    			return webView.getTitle();
    		}
    	}
    	return "";
    	*/
    	
    }
    
    // �E�F�u�r���[����URL���擾.
    public String getWebUrl(){
    	SubActivity subActivity = (SubActivity)mLAM.getActivity(mCurrentTabName);
    	WebView webView = (WebView)subActivity.findViewById(R.id.webview_sub);
    	return webView.getUrl();
    	/*
    	// webView��URL���擾.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
    	return webView.getUrl();	// return��webView.getUrl�Ŏ擾����URL��Ԃ�.
    	*/
    	//return null;
    	
    	/*
    	// WebView��URL���擾.
    	String tag = this.getTabHost().getCurrentTabTag();
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
    			WebView webView = (WebView)ti.view.findViewById(R.id.webview_sub);
    			return webView.getUrl();
    		}
    	}
    	*/
    	//return "";
    	
    }
    
    // �_�E�����[�h���ɏI�[��'/'�̏ꍇ, "index.html"��⊮����URL��Ԃ�.
    public String getDownloadUrl(){
    	
    	// URL�̎擾.
    	String url = getWebUrl();	// getWebUrl��url���擾.
    	
    	// '/'�ŏI����Ă�����, "index.html"��t��.
    	if (url.charAt(url.length() - 1) == '/'){	// �Ōオ'/'�̏ꍇ.
    		url = url + "index.html";	// url��"index.html"��t������.
    	}
    	
    	// url��Ԃ�.
    	return url;	// return��url��Ԃ�.
    	
    }
    
    // �_�E�����[�hURL�̃t�@�C�����̕��������擾.
    public String getDownloadFileName(String url){
    	
    	// Uri�̐���.
    	Uri downloadUri = Uri.parse(url);	// Uri.parse��url���p�[�X����downloadUri�Ɋi�[.
    	String downloadFileName = downloadUri.getLastPathSegment();	// downloadUri.getLastPathSegment�Ńt�@�C���������������擾��, downloadFileName�Ɋi�[.
    	return downloadFileName;	// return��downloadFileName��Ԃ�.
    	
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
    
    /*
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
    */
    
    // �w�肳�ꂽURL�����[�h.
    public void loadUrl(String url){
    	
    	/*
    	// webView���擾��, url�����[�h.
    	//WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
    	String tag = this.getTabHost().getCurrentTabTag();
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
    			WebView webView = (WebView)ti.view.findViewById(R.id.webview_sub);
    			webView.loadUrl(url);	// webView.loadUrl��url�̎w��Web�y�[�W�����[�h.    	
    		}
    	}
    	*/
    	
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
    	SubActivity subActivity = (SubActivity)mLAM.getActivity(mCurrentTabName);
    	subActivity.setUrlOmit(url);
    	subActivity.loadUrl();
    	//setUrlOmit(url);	// setUrlOmit��URL�o�[��URL���Z�b�g.
		//loadUrl();	// loadUrl��URL�o�[��URL�����[�h.
		
    }
    
    // �C���e���g�Ŏw�肳�ꂽURL�����[�h.
    public void loadUrlFromIntent(){
    	
    	/*
    	// �N�����̃C���e���g����URL���擾��, ������g���ă��[�h.
        Intent intent = getIntent();	// getIntent��intent���擾.
        String action = intent.getAction();	// intent.getAction��action���擾.
        String schema = intent.getScheme();	// intent.getSchema��schema���擾.
        String url = intent.getDataString();	// intent.getDataString��url���擾.
        if (action != null && action.equals(Intent.ACTION_VIEW) && (schema.equals("http") || schema.equals("https"))){	// ACTION_VIEW��http�܂���https�̎�.
        	//setUrlOmit(url);	// setUrlOmit��URL�o�[��URL���Z�b�g.
    		loadUrl();	// loadUrl��URL�o�[��URL�����[�h.
        }
        */
        
    }
    
    // �v���O���X�o�[�ɐi���x���Z�b�g.
    public void setProgressValue(int progress, String tag){
    	
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
		    	// �v���O���X�o�[���擾��, �w�肳�ꂽ�i���x���Z�b�g.
		    	//ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewById��R.id.progressbar����ProgressBar�I�u�W�F�N�gprogressBar���擾.
		    	//progressBar.setProgress(progress);	// progressBar.setProgress��progress���Z�b�g.
		    	ProgressBar progressBar = (ProgressBar)ti.view.findViewById(R.id.progressbar_sub);	// findViewById��R.id.progressbar����ProgressBar�I�u�W�F�N�gprogressBar���擾.
		    	progressBar.setProgress(progress);	// progressBar.setProgress��progress���Z�b�g.
    		}
    	}
    	
    }
    
    // �v���O���X�o�[�̕\��/��\�����Z�b�g.
    public void setProgressBarVisible(boolean visible, String tag){
    	
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
				// �v���O���X�o�[���擾��, �\��/��\�����Z�b�g.
				//ProgressBar progressBar = (ProgressBar)findViewById(R.id.progressbar);	// findViewById��R.id.progressbar����ProgressBar�I�u�W�F�N�gprogressBar���擾.
				ProgressBar progressBar = (ProgressBar)ti.view.findViewById(R.id.progressbar_sub);
				if (visible){	// true�Ȃ�\��.
					progressBar.setVisibility(View.VISIBLE);	// progressBar.setVisibility��VISIBLE.
				}
				else{	// false�Ȃ��\��.
					//progressBar.setVisibility(View.INVISIBLE);	// progressBar.setVisibility��INVISIBLE.
					progressBar.setVisibility(View.GONE);	// progressBar.setVisibility��GONE.
				}
    		}
    	}
    	
    }
    
    // �A�N�V�����o�[�̃^�C�g�����Z�b�g.
    public void setActionBarTitle(String title){
    	
    	// �A�N�V�����o�[���擾��, �w�肳�ꂽ�^�C�g�����Z�b�g.
    	getActionBar().setTitle(title);	// getActionBar().setTitle��title���Z�b�g.
    	
    }
    
    // �o�b�N�L�[�������ꂽ����WebView�̓���.
    public void onBackPressedWebView(){
    	
    	//Toast.makeText(this, "A", Toast.LENGTH_LONG).show();
    	SubActivity subActivity = (SubActivity)mLAM.getActivity(mCurrentTabName);
    	WebView webView = (WebView)subActivity.findViewById(R.id.webview_sub);
    	if (webView.canGoBack()){	// �o�b�N�\�ȏꍇ.
    		//Toast.makeText(this, "B", Toast.LENGTH_LONG).show();
    		webView.goBack();	// webView.goBack�Ŗ߂�.
    	}
    	else{	// �����łȂ���.
    		//Toast.makeText(this, "C", Toast.LENGTH_LONG).show();
    		super.onBackPressed();	// �e�N���X��onBackPressed���Ă�.
    	}
    	/*
    	// �߂��ꍇ��, 1�O�̃y�[�W�ɖ߂�.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
    	if (webView.canGoBack()){	// �o�b�N�\�ȏꍇ.
    		webView.goBack();	// webView.goBack�Ŗ߂�.
    	}
    	else{	// �����łȂ���.
    		super.onBackPressed();	// �e�N���X��onBackPressed���Ă�.
    	}
    	*/
    	
    	/*
    	// WebView��URL���擾.
    	String tag = this.getTabHost().getCurrentTabTag();
    	TabInfo ti = mApp.mTabMap.get(tag);
    	if (ti != null){
    		if (ti.view != null){
    			WebView webView = (WebView)ti.view.findViewById(R.id.webview_sub);
    			if (webView.canGoBack()){	// �o�b�N�\�ȏꍇ.
    	    		webView.goBack();	// webView.goBack�Ŗ߂�.
    	    	}
    	    	else{	// �����łȂ���.
    	    		super.onBackPressed();	// �e�N���X��onBackPressed���Ă�.
    	    	}
    		}
    	}
    	*/
    	
    }
    
    // �^�u�̒ǉ�.(Intent��.)
    public void addTabByIntent(){
    	
    	// ���݂̃^�u���}�b�v�ɕۑ�.
    	saveTabState();	// saveTabState�ŕۑ�.
    	
    	// ���C���A�N�e�B�r�e�B���N������.
    	String packageName = getPackageName();	// getPackageName��packageName���擾.
    	Intent intent = new Intent();	// Intent�I�u�W�F�N�gintent���쐬.
    	intent.setClassName(packageName, packageName + ".MainActivity");	// intent.setClassName��".MainActivity"���Z�b�g.
    	intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	// ���ꂾ�ƋN������A�N�e�B�r�e�B�ȊO�͔j�������.
    	intent.putExtra("LaunchMode", "NewTab");	// "LaunchMode"���L�[, "NewTab"��l�Ƃ��ēo�^.
    	startActivity(intent);	// startActivity��intent��n��.
    	
    }
    
    // �^�u�̒ǉ�.(LocalActivityManager��.)
    public void addTabToLAM(){
    	
    	//TabInfo tabInfo = mApp.mHlpr.getTabInfo(mCurrentTabName);
    	TabInfo tabInfo = mApp.mTabMap.get(mCurrentTabName);
    	tabInfo.date = System.currentTimeMillis();
    	tabInfo.view = mFL.findViewById(R.id.layout_sub);
    	mApp.mHlpr.updateTabInfo(mCurrentTabName, tabInfo);
    	
    	//Toast.makeText(this, "newtab", Toast.LENGTH_LONG).show();
    	
    	registTab();
    	TabInfo newTabInfo = mApp.mHlpr.getLastTabInfo();
		Intent intent = new Intent(this, SubActivity.class);
		Bundle args = new Bundle();
		args.putString("tag", newTabInfo.tabName);
		intent.putExtra("args", args);
		mFL.removeAllViews();
		mLAM.destroyActivity(newTabInfo.tabName, true);
		Window window = mLAM.startActivity(newTabInfo.tabName, intent);
		View view = window.getDecorView();
		mFL.addView(view);
		mCurrentTabName = newTabInfo.tabName;
		ActionBar act = getActionBar();
		if (act != null){
			if (newTabInfo.title.equals("")){
				act.setTitle("Zinc");
			}
		}
		
    }
    
    // �^�u�̒ǉ�.(TabActivity��.)
    public void addTabToActivity(){
    	
    	// �^�u��Ԃ̕ۑ�.(�ȈՓI. ���[�h���̐؂�ւ����l�����Ă��Ȃ�.)
    	String tag = mApp.mTabHost.getCurrentTabTag();
    	//Toast.makeText(mContext, "tag = " + tag, Toast.LENGTH_LONG).show();
    	TabInfo tabInfo = mApp.mHlpr.getTabInfo(tag);
    	tabInfo.date = System.currentTimeMillis();
    	mApp.mHlpr.updateTabInfo(tag, tabInfo);
    	
    	// �V�K�^�u�̒ǉ�.
    	registTab();	// registTab�ŐV�K�^�u��o�^.
		TabInfo newTabInfo = mApp.mHlpr.getLastTabInfo();
		//Toast.makeText(this, "url = " + newTabInfo.url, Toast.LENGTH_LONG).show();
		TabHost.TabSpec tabSpec = mApp.mTabHost.newTabSpec(newTabInfo.tabName);	// tabName
		//tabSpec.setIndicator(tabInfo.title);	// title.
		final String tag2 = newTabInfo.tabName;
		newTabInfo.title = newTabInfo.tabName;
		final CustomTabWidget widget = new CustomTabWidget(this, newTabInfo.title, newTabInfo.tabName, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Toast.makeText(mContext, "click(3) : " + tag2, Toast.LENGTH_LONG).show();
				removeTab(tag2);
			}
			
		});
		tabSpec.setIndicator(widget);
		mApp.mTabNameList.add(newTabInfo.tabName);
        Intent intent = new Intent(this, SubActivity.class);	// intent�𐶐�.
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intent.setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle args = new Bundle();	// args�쐬.
        args.putString("tag", newTabInfo.tabName);	// ("tag", newTabInfo.tabName)�œo�^.
        args.putBoolean("remove", false);
        intent.putExtras(args);	// args�o�^.
        //tabSpec.setContent(intent);	// intent���Z�b�g.
        //tabSpec.setContent(this);
        mApp.mTabHost.addTab(tabSpec);	// tabSpec��ǉ�.
        int last = mApp.mTabNameList.size() - 1;	// last.
        //getTabHost().setCurrentTab(last);
        mApp.mHlpr.updateTabInfo(newTabInfo.tabName, newTabInfo);
    }
    
 // �^�u�̍폜.(���ݕ\������Ă���^�u���폜.)
    public void removeTab(){
    
    	String tag = mApp.mTabHost.getCurrentTabTag();
    	//Toast.makeText(this, "before", Toast.LENGTH_LONG).show();
    	int i = mApp.mTabHost.getCurrentTab();
    	//mApp.mTabHost.getTabWidget().removeAllViews();
    	mApp.mTabHost.clearAllTabs();
    	//Toast.makeText(this, "after", Toast.LENGTH_LONG).show();
    	boolean b = mApp.mHlpr.removeRowTab(tag);
    	if (b){
    		//Toast.makeText(this, "true", Toast.LENGTH_LONG).show();
    	}
    	else{
    		//Toast.makeText(this, "false", Toast.LENGTH_LONG).show();
    	}
    	mApp.mTabMap.remove(tag);
    	mApp.mTabNameList.remove(i);
    	int s = mApp.mTabMap.size();
    	//Toast.makeText(this, "s = " + String.valueOf(s), Toast.LENGTH_LONG).show();
    	for (TabInfo ti : mApp.mTabMap.values()){
    		TabHost.TabSpec tabSpec = mApp.mTabHost.newTabSpec(ti.tabName);
    		//tabSpec.setIndicator(tabInfo.title);	// title.
    		final String tag2 = ti.tabName;
    		if (ti.title.equals("")){
    			//Toast.makeText(this, "add tab title = " + ti.tabName, Toast.LENGTH_LONG).show();
    			ti.title = ti.tabName;
    		}
    		final CustomTabWidget widget = new CustomTabWidget(this, ti.title, ti.tabName, new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				//Toast.makeText(mContext, "click(4) : " + tag2, Toast.LENGTH_LONG).show();
    				removeTab(tag2);
    			}
    			
    		});
    		tabSpec.setIndicator(widget);
    		Intent intent = new Intent(this, SubActivity.class);	// intent�𐶐�.
            Bundle args = new Bundle();	// args�쐬.
            args.putString("tag", ti.tabName);	// ("tag", ti.tabName)�œo�^.
            args.putBoolean("remove", true);
            intent.putExtras(args);	// args�o�^.
            //tabSpec.setContent(intent);	// intent���Z�b�g.
            //tabSpec.setContent(this);
            mApp.mTabHost.addTab(tabSpec);	// tabSpec��ǉ�.
            //Toast.makeText(this, "add tabName = " + ti.tabName + " title = " + ti.title, Toast.LENGTH_LONG).show();
    	}
    	
    }
    
    // �^�u�̍폜.�i�����ꂽ�^�u���폜.)
    public void removeTab(String tag){
    
    	//String tag = mApp.mTabHost.getCurrentTabTag();
    	//Toast.makeText(this, "before", Toast.LENGTH_LONG).show();
    	//int i = mApp.mTabHost.getCurrentTab();
    	int i = -1;
    	for (int ii = 0; ii < mApp.mTabNameList.size(); ii++){
    		if (mApp.mTabNameList.get(ii).equals(tag)){
    			i = ii;
    		}
    	}
    	//mApp.mTabHost.getTabWidget().removeAllViews();
    	mApp.mTabHost.clearAllTabs();
    	//Toast.makeText(this, "after", Toast.LENGTH_LONG).show();
    	boolean b = mApp.mHlpr.removeRowTab(tag);
    	if (b){
    		//Toast.makeText(this, "true", Toast.LENGTH_LONG).show();
    	}
    	else{
    		//Toast.makeText(this, "false", Toast.LENGTH_LONG).show();
    	}
    	mApp.mTabMap.remove(tag);
    	if (i != -1){
    		mApp.mTabNameList.remove(i);
    	}
    	int s = mApp.mTabMap.size();
    	//Toast.makeText(this, "s = " + String.valueOf(s), Toast.LENGTH_LONG).show();
    	for (TabInfo ti : mApp.mTabMap.values()){
    		TabHost.TabSpec tabSpec = mApp.mTabHost.newTabSpec(ti.tabName);
    		//tabSpec.setIndicator(tabInfo.title);	// title.
    		if (ti.title.equals("")){
    			//Toast.makeText(this, "add tab title = " + ti.tabName, Toast.LENGTH_LONG).show();
    			ti.title = ti.tabName;
    		}
    		final String tag2 = ti.tabName;
    		final CustomTabWidget widget = new CustomTabWidget(this, ti.title, ti.tabName, new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				//Toast.makeText(mContext, "click(4) : " + tag2, Toast.LENGTH_LONG).show();
    				removeTab(tag2);
    			}
    			
    		});
    		tabSpec.setIndicator(widget);
    		Intent intent = new Intent(this, SubActivity.class);	// intent�𐶐�.
            Bundle args = new Bundle();	// args�쐬.
            args.putString("tag", ti.tabName);	// ("tag", ti.tabName)�œo�^.
            args.putBoolean("remove", true);
            intent.putExtras(args);	// args�o�^.
            //tabSpec.setContent(intent);	// intent���Z�b�g.
            //tabSpec.setContent(this);
            mApp.mTabHost.addTab(tabSpec);	// tabSpec��ǉ�.
            //Toast.makeText(this, "add tabName = " + ti.tabName + " title = " + ti.title, Toast.LENGTH_LONG).show();
    	}
    	
    }
    
    // �^�u��Ԃ̕ۑ�.
    public void saveTabState(){
    	
    	TabInfo tabInfo = mApp.mTabMap.get(mCurrentTabName);
    	tabInfo.date = System.currentTimeMillis();
    	tabInfo.view = mFL.findViewById(R.id.layout_sub);
    	mApp.mHlpr.updateTabInfo(mCurrentTabName, tabInfo);
    	
    	/*
    	// ���݂̃^�u���}�b�v�ɕۑ�.
    	View rootView = getWindow().getDecorView();	// getWindow().getDecorView��rootView���擾.
		View content = rootView.findViewById(R.id.layout_main);	// rootView����layout_main�𔲂��o��.
		TabInfo tabInfo = mApp.mTabMap.get(mCurrentTabName);	// tabInfo�擾.
		*/
		/*
		if (tabInfo == null){	// �^�u������.
			tabInfo = new TabInfo();	// ����.
			tabInfo.view = content;	// content��ۑ�.
			tabInfo.tabName = mCurrentTabName;	// ���݂̃^�u�����Z�b�g.
			tabInfo.title = getActionBar().getTitle().toString();	// �^�C�g�����擾.(�A�N�V�����o�[����擾���m������.)
			tabInfo.url = getUrl();
			tabInfo.date = System.currentTimeMillis();	// ���ݓ������Z�b�g.
			mApp.mTabMap.put(mCurrentTabName, tabInfo);	// mCurrentTabName���L�[��tabInfo��o�^.
		}
		else{
		*/
    	/*
		tabInfo.view = content;	// content��ۑ�.
		tabInfo.tabName = mCurrentTabName;	// ���݂̃^�u�����Z�b�g.
		tabInfo.title = getActionBar().getTitle().toString();	// �^�C�g�����擾.(�A�N�V�����o�[����擾���m������.)
		tabInfo.url = getUrl();
		tabInfo.date = System.currentTimeMillis();	// ���ݓ������Z�b�g.
		mApp.mTabMap.put(mCurrentTabName, tabInfo);	// mCurrentTabName���L�[��tabInfo��o�^.
		//}
		
		// DB�ɂ��ۑ�.
		mApp.mHlpr.updateTabInfo(mCurrentTabName, tabInfo);	// updateTabInfo��tabInfo���X�V.
		*/
		
    }
    
    // �^�u�ꗗ�̕\��.
    public void showTabs(){
    	
    	// ���݂̃^�u���}�b�v�ɕۑ�.
    	saveTabState();	// saveTabState�ŕۑ�.
    	
    	// �^�u�X�A�N�e�B�r�e�B���N������.
    	String packageName = getPackageName();	// getPackageName��packageName���擾.
    	Intent intent = new Intent();	// Intent�I�u�W�F�N�gintent���쐬.
    	intent.setClassName(packageName, packageName + ".TabsActivity");	// intent.setClassName��".TabsActivity"���Z�b�g.
    	startActivityForResult(intent, REQUEST_CODE_TAB);	// startActivityForResult��intent��REQUEST_CODE_TAB��n��. 			
    			
    }
    
    // �u�b�N�}�[�N�ւ̒ǉ�.(Browser�N���X��.)
    public void addBookmarkToBrowser(){
    	
    	/*
    	// webView���擾��, URL�ƃ^�C�g�����擾.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
		String title = webView.getTitle();	// webView.getTitle�Ń^�C�g�����擾.
		String url = webView.getUrl();	// webView.getUrl��URL���擾.
		
		// ����URL���u�b�N�}�[�N�ɓo�^.
		ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�̐���.
		values.put(Browser.BookmarkColumns.BOOKMARK, "1");	// values.put��BOOKMARK�t���O��"1"�Ƃ��ēo�^.
		try{	// try�ň͂�.
			int row = getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.URL + "=?", new String[]{url});	// getContentResolver().update��URL�������s���X�V.
			if (row <= 0){	// �X�V���s.
				Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
			}
			else{
				Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_success), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_success�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
			}
		}
		catch (Exception ex){	// ��O��catch.
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
		}
		*/
		
    }
    
    // �u�b�N�}�[�N�ւ̒ǉ�.(�Ǝ�DB��.)
    public void addBookmarkToDB(){
    	
    	// �J�����g�^�u����^�C�g����URL���擾.
    	String title = getWebTitle();
    	String url = getWebUrl();
    	long datemillisec = System.currentTimeMillis();	// System.currentTimeMillis�Ō��ݎ������擾��, datemillisec�Ɋi�[.
    	//Toast.makeText(this, "title = " + title + ", url = " + url, Toast.LENGTH_LONG).show();
    	// ����URL���u�b�N�}�[�N�֒ǉ�.
    	long id = mApp.mHlpr.insertRowBookmark(title, url, datemillisec);	// mApp.mHlpr.insertRowBookmark��title, url, datemillisec��ǉ�.
		if (id <= 0){	// �X�V���s.
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
		}
		else{
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_success), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_success�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
		}
    	/*
    	// webView���擾��, URL�ƃ^�C�g�����擾.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
		String title = webView.getTitle();	// webView.getTitle�Ń^�C�g�����擾.
		String url = webView.getUrl();	// webView.getUrl��URL���擾.
		long datemillisec = System.currentTimeMillis();	// System.currentTimeMillis�Ō��ݎ������擾��, datemillisec�Ɋi�[.
		
		// ����URL���u�b�N�}�[�N�֒ǉ�.
		long id = mApp.mHlpr.insertRowBookmark(title, url, datemillisec);	// mApp.mHlpr.insertRowBookmark��title, url, datemillisec��ǉ�.
		if (id <= 0){	// �X�V���s.
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_error), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_error�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
		}
		else{
			Toast.makeText(this, getString(R.string.toast_message_bookmark_regist_success), Toast.LENGTH_LONG).show();	// R.string.toast_message_bookmark_regist_success�ɒ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
		}
		*/
		
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
    
    // �_�E�����[�h���N�G�X�g�̍쐬.
    public Request createDownloadRequest(Uri downloadUri, String downloadFileName){
    	
    	// �_�E�����[�h���N�G�X�g���쐬����.
    	Request request = new Request(downloadUri);	// downloadUri���w�肵��, request���쐬.
    	request.setDestinationInExternalFilesDir(getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, "/" + downloadFileName);	// request.setDestinationInExternalFilesDir�Ń_�E�����[�h����w��.
    	request.setTitle("Zinc - �_�E�����[�h");		// "Zinc - �_�E�����[�h"���Z�b�g.
    	request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);	// �l�b�g���[�N�^�C�v�̓��o�C����WiFi����.
    	request.setMimeType("application/octet-stream");	// MIME�^�C�v��"application/octet-stream".
    	return request;	// request��Ԃ�.
    	
    }
    
    // �_�E�����[�h.
    public void download(){
    	
    	// �_�E�����[�hURI�̎擾.
    	Uri downloadUri = Uri.parse(getWebUrl());	// getWebUrl�Ŏ擾������URL��Uri.parse�Ńp�[�X����downloadUri���擾.
    	
    	// �_�E�����[�h�t�@�C�����̎擾.
    	String downloadFileName = getDownloadFileName(getDownloadUrl());	// getDownloadUrl�̕⊮�ς�URL����getDownloadFileName�Ńt�@�C���������������o��.
    	
    	// �_�E�����[�h���N�G�X�g�̍쐬.
    	Request request = createDownloadRequest(downloadUri, downloadFileName);	// createDownloadRequest��request���쐬.
    	
    	// �_�E�����[�h���V�[�o�[�̍쐬.
    	DownloadReceiver receiver = new DownloadReceiver(this);	// DownloadReceiver�I�u�W�F�N�greceiver�̍쐬.
    	
    	// ���V�[�o�[�̓o�^.
    	registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));	// registerReceiver�Ń��V�[�o�[�o�^.

    	// �_�E�����[�h�^�X�N�̒ǉ�.
    	mDownloadManager.enqueue(request);	//  mDownloadManager.enqueue��request���^�X�N�ɒǉ�.
    	
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
    
    // ���[�U�G�[�W�F���g�̕ύX.
    public void setUserAgent(String strUA){
    	
    	/*
    	// webView�̎擾.
    	mCurrentUA = strUA;	// mCurrentUA��strUA���Z�b�g.
        WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
        webView.getSettings().setUserAgentString(mCurrentUA);	// webView.getSettings().setUserAgentString��mCurrentUA���Z�b�g.
        webView.reload();	// webView.reload�Ń����[�h.
        */
    	
    }
    
    // �d�b/PC�T�C�g�u���E�U�̕\���ؑ�.
    public void changePhonePCSite(MenuItem item){
    	
    	// �`�F�b�N�����Ă��Ȃ��ꍇ.
		if (!item.isChecked()){	// item.isChecked��false.
			// �`�F�b�N��t����.
			item.setChecked(true);	//item.setChecked��true���Z�b�g����, �`�F�b�N�ς�.
			// PC�p���[�U�G�[�W�F���g���Z�b�g.
			setUserAgent(mPCUA);	// setUserAgent��mPCUA���Z�b�g.
		}
		else{	// �`�F�b�N�����Ă���ꍇ.
			// �`�F�b�N���O��.
			item.setChecked(false);	// item.setChecked��false���Z�b�g����, �`�F�b�N���O��.
			// �d�b�p���[�U�G�[�W�F���g���Z�b�g.
			setUserAgent(mPhoneUA);	// setUserAgent��mPhoneUA���Z�b�g.
		}
		
    }
    
}