// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

// �^�u�X�A�N�e�B�r�e�B�N���XTabsActivity
public class TabsActivity extends Activity implements OnItemClickListener {	// AdapterView.OnItemClickListener�C���^�[�t�F�[�X�̒ǉ�.

	// �����o�t�B�[���h�̏�����.
	public MainApplication mApp = null;	// MainApplication�I�u�W�F�N�gmApp��null�ŏ�����.
	
	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_tabs);	// setContentView��R.layout.activity_tabs���Z�b�g.
        mApp = (MainApplication)getApplicationContext();	// getApplicationContext��mApp���擾.
        
        // �^�u�̃��[�h.
        loadTabs();	// loadTabs�Ń^�u�����[�h���ĕ\��.
        
    }
    
    // ���X�g�r���[�̃A�C�e�����I�����ꂽ��.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){  	
    	
    	// �I�����ꂽ�A�C�e���̎擾.
    	final ListView lv = (ListView)parent;	// parent��ListView�I�u�W�F�N�glv�ɃL���X�g.
    	final TabItem item = (TabItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
    	
    	// �^�u���ƃ^�C�g���𑗂�Ԃ�.
    	setReturnItem(item.tabName, item.title);	// setReturnItem��item.tabName, item.title�𑗂�Ԃ��f�[�^�Ƃ��ăZ�b�g.
    	finish();	// finish�ł��̃A�N�e�B�r�e�B�����.
    	
    }
    
    // �^�u�ꗗ�̎擾.
    public List<TabItem> getActiveTabs(){
    	
    	// tabs�̍쐬
        List<TabItem> tabs = new ArrayList<TabItem>();	// �^�u�Xtabs�̐���.
        
        // �r���[�}�b�v�̃L�[�����o��, ��������l�����o��, �K�v�ȏ������o��.
    	for (String key : mApp.mViewMap.keySet()){	// �L�[�̎��o�����J��Ԃ�.
    		View v = (View)mApp.mViewMap.get(key);	// �l�����o��.
    		WebView wv = (WebView)v.findViewById(R.id.webview);	// webview�����o��.
    		TabItem item = new TabItem();	// TabItem�𐶐�.
    		item.title = wv.getTitle();		//�@title�擾.
    		item.url = wv.getUrl();	// url�擾.
    		item.tabName = key;	// key�̓^�u��.
    		tabs.add(item);	// tabs.add��item�ǉ�.
    	}
    	
    	// tabs��Ԃ�.
    	return tabs;	// return��tabs��Ԃ�.
    	
    }
    
    // �^�u�̃��[�h.
    public void loadTabs(){

    	// adapter�̐���
        TabAdapter adapter = new TabAdapter(this, R.layout.adapter_tab_item, getActiveTabs());	// �A�_�v�^adapter�̐���.(getActiveTabs()���3�����ɃZ�b�g.)
        
        // ListView�̎擾
        ListView lvTabs = (ListView)findViewById(R.id.listview_tabs);	// ���X�g�r���[lvTabs�̎擾.
        
        // ���X�g�r���[�ɃA�_�v�^���Z�b�g.
        lvTabs.setAdapter(adapter);	//  lvTabs.setAdapter��adapter���Z�b�g.
        
        // �X�V.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
        // AdapterView.OnItemClickListener�̃Z�b�g.
        lvTabs.setOnItemClickListener(this);	// this(���g)���Z�b�g.
            
    }
    
    // ����Ԃ��C���e���g�Ƀ^�u�����Z�b�g.
    public void setReturnItem(String tabName, String title){
    	
    	// ����Ԃ��C���e���g��������, finish���邱�ƂŖ߂�����Ƀf�[�^���Ԃ�.
       	Intent data = new Intent();	// Intent�I�u�W�F�N�gdata�̍쐬.
   	    Bundle bundle = new Bundle();	// Bundle�I�u�W�F�N�gbundle�̍쐬.
   	    bundle.putString("tabName", tabName);	// bundle.putString�L�["tabName", �ltabName��o�^.
   	    bundle.putString("title", title);	// bundle.putString�L�["title", �ltitle��o�^.
   	    data.putExtras(bundle);	// data.putExtras��bundle��o�^.
   	    setResult(RESULT_OK, data);	// setResult��RESULT_OK��data���Z�b�g.
    	    	
    }
    
}