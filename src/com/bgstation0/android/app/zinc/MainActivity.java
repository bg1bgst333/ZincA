// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.app.Activity;
import android.os.Bundle;
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

	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_main);	// setContentView��R.layout.activity_main���Z�b�g.
        
        // navigateButton���擾��, OnClickListener�Ƃ��Ď��g(this)���Z�b�g.
        Button navigateButton = (Button)findViewById(R.id.button_navigate);	// findViewById��R.id.button_navigate����Button�I�u�W�F�N�gnavigateButton���擾.
        navigateButton.setOnClickListener(this);	// navigateButton.setOnClickListener��this(���g)���Z�b�g.
        
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
    		// URL���g�[�X�g�ŕ\��.
    		EditText etUrl = (EditText)findViewById(R.id.edittext_urlbar);	// findViewById��R.id.edittext_urlbar����EditText�I�u�W�F�N�getUrl���擾.
    		Toast.makeText(this, etUrl.getText(), Toast.LENGTH_LONG).show();	// etUrl.getText()�Ŏ擾����URL��Toast�ŕ\��.
    	}
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