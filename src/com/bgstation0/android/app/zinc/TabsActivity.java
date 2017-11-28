// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
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
    	
    }
    
    // �^�u�̃��[�h.
    public void loadTabs(){

    	// tabs�̍쐬
        List<TabItem> tabs = new ArrayList<TabItem>();	// �^�u�Xtabs�̐���.
        
    	for (String key : mApp.mViewMap.keySet()){
    		View v = (View)mApp.mViewMap.get(key);
    		WebView wv = (WebView)v.findViewById(R.id.webview);
    		TabItem item = new TabItem();
    		item.title = wv.getTitle();
    		item.url = wv.getUrl();
    		item.tabName = key;
    		tabs.add(item);
    	}
    	
    	// adapter�̐���
        TabAdapter adapter = new TabAdapter(this, R.layout.adapter_tab_item, tabs);	// �A�_�v�^adapter�̐���.(tabs���3�����ɃZ�b�g.)
        
        // ListView�̎擾
        ListView lvTabs = (ListView)findViewById(R.id.listview_tabs);	// ���X�g�r���[lvTabs�̎擾.
        
        // ���X�g�r���[�ɃA�_�v�^���Z�b�g.
        lvTabs.setAdapter(adapter);	//  lvTabs.setAdapter��adapter���Z�b�g.
        
        // �X�V.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
        // AdapterView.OnItemClickListener�̃Z�b�g.
        lvTabs.setOnItemClickListener(this);	// this(���g)���Z�b�g.
            
    }
    
}