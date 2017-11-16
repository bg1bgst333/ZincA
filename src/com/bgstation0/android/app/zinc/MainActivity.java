// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

//���C���A�N�e�B�r�e�B�N���XMainActivity
public class MainActivity extends Activity {

	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_main);	// setContentView��R.layout.activity_main���Z�b�g.
        
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
    
}