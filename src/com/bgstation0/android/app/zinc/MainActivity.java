// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Browser;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//���C���A�N�e�B�r�e�B�N���XMainActivity
public class MainActivity extends Activity implements OnClickListener {	// View.OnClickListener�C���^�[�t�F�[�X�̒ǉ�.

	// �����o�t�B�[���h�̏�����.
	public static final int REQUEST_CODE_BOOKMARK = 1001;	// REQUEST_CODE_BOOKMARK��1001�Ƃ���.
		
	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_main);	// setContentView��R.layout.activity_main���Z�b�g.
        
        // navigateButton���擾��, OnClickListener�Ƃ��Ď��g(this)���Z�b�g.
        Button navigateButton = (Button)findViewById(R.id.button_navigate);	// findViewById��R.id.button_navigate����Button�I�u�W�F�N�gnavigateButton���擾.
        navigateButton.setOnClickListener(this);	// navigateButton.setOnClickListener��this(���g)���Z�b�g.
        
        // CustomWebViewClient�̃Z�b�g.
        WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
        webView.setWebViewClient(new CustomWebViewClient(this));	// new�Ő�������CustomWebViewClient�I�u�W�F�N�g(�R���X�g���N�^�̈�����this��n��.)��webView.setWebViewClient�ŃZ�b�g.
        
    }
    
    // �o�b�N�L�[�������ꂽ��.
    @Override
    public void onBackPressed(){
    	
    	// �߂��ꍇ��, 1�O�̃y�[�W�ɖ߂�.
    	WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
    	if (webView.canGoBack()){	// �o�b�N�\�ȏꍇ.
    		webView.goBack();	// webView.goBack�Ŗ߂�.
    	}
    	else{	// �����łȂ���.
    		super.onBackPressed();	// �e�N���X��onBackPressed���Ă�.
    	}
    	
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
    			
    			// REQUEST_CODE_BOOKMARK�u���b�N
    			{
    			
    				if (resultCode == RESULT_OK){	// RESULT_OK�̏ꍇ.
    					String title = bundle.getString("title");	// bundle.getString��title���擾.
    					String url = bundle.getString("url");	// bundle.getString��url���擾.
    					EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    		    		WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
    		    		etUrl.setText(url);	// etUrl.SetText��URL�o�[��etUrl��url���Z�b�g.
    		    		webView.loadUrl(url);	// webView.loadUrl��url�̎w��Web�y�[�W�����[�h.
    				}

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
    		WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
    		String title = webView.getTitle();	// webView.getTitle�Ń^�C�g�����擾.
    		String url = webView.getUrl();	// webView.getUrl��URL���擾.
    		Browser.saveBookmark(this, title, url);	// Browser.saveBookmark�Ńu�b�N�}�[�N�ɒǉ�.
    		Toast.makeText(this, title + "(" + url + ")", Toast.LENGTH_LONG).show();	// �ǉ������u�b�N�}�[�N��title��url��Toast�ŕ\��.
    		
    	}
    	else if (id == R.id.menu_item_bookmark_show){	// R.id.menu_item_bookmark_show("�u�b�N�}�[�N�̈ꗗ")�̎�.
    		
    		// �u�b�N�}�[�N�A�N�e�B�r�e�B���N������.
    		String packageName = getPackageName();	// getPackageName��packageName���擾.
    		Intent intent = new Intent();	// Intent�I�u�W�F�N�gintent���쐬.
    		intent.setClassName(packageName, packageName + ".BookmarkActivity");	// intent.setClassName��".BookmarkActivity"���Z�b�g.
    		startActivityForResult(intent, REQUEST_CODE_BOOKMARK);	// startActivityForResult��intent��REQUEST_CODE_BOOKMARK��n��.
    		
    	}
    	else if (id == R.id.menu_item_history_show){	// R.id.menu_item_history_show("�����̈ꗗ")�̎�.
    		
    		// �q�X�g���[�A�N�e�B�r�e�B���N������.
    		String packageName = getPackageName();	// getPackageName��packageName���擾.
    		Intent intent = new Intent();	// Intent�I�u�W�F�N�gintent���쐬.
    		intent.setClassName(packageName, packageName + ".HistoryActivity");	// intent.setClassName��".HistoryActivity"���Z�b�g.
    		startActivity(intent);	// startActivity��intent��n����, HistoryActivity���N��.
    		
    	}
    	
    	// ���Ƃ͊���̏����ɔC����.
    	return super.onOptionsItemSelected(item);	// �e�N���X��onOptionsItemSelected���Ă�.
    	
    }
    
    // navigateButton("���M")�������ꂽ��.
    public void onClick(View v){	// OnClickListener.onClick���I�[�o�[���C�h.
    	
    	// �{�^�����ƂɐU�蕪����.
    	switch (v.getId()){	// v.getId()��View(Button)��id���擾.
    	
	    	// R.id.button_navigate("���M")�̎�.
			case R.id.button_navigate:
				
				// button_navigate�u���b�N
				{
					
					// �E�F�u�r���[�œ��͂��ꂽURL��Web�y�[�W��\��.
		    		EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
		    		WebView webView = (WebView)findViewById(R.id.webview);	// findViewById��R.id.webview����WebView�I�u�W�F�N�gwebView���擾.
		    		String url = etUrl.getText().toString();	// etUrl.getText().toString()�œ��͂��ꂽURL��������擾��, url�Ɋi�[.
		    		webView.loadUrl(url);	// webView.loadUrl��url�̎w��Web�y�[�W�����[�h.
	
				}
				
				// ������.
				break;	// break�Ŕ�����.
    			
    		// ����ȊO�̎�.
    		default:
    			
    			// ������.
    			break;	// break�Ŕ�����.
    	
    	}
    	
    }
    
}