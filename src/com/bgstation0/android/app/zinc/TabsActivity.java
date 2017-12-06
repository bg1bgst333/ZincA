// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

// �^�u�X�A�N�e�B�r�e�B�N���XTabsActivity
public class TabsActivity extends Activity implements OnItemClickListener, OnItemLongClickListener {	// AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener�C���^�[�t�F�[�X�̒ǉ�.

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
    
    // ���X�g�r���[�̃A�C�e�������������ꂽ��.
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// �I�����ꂽ�A�C�e���̎擾.
    	final ListView lv = (ListView)parent;	// parent��ListView�I�u�W�F�N�glv�ɃL���X�g.
    	TabItem item = (TabItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
    	
    	// ���������ꂽ�A�C�e�����^�u����폜.
    	removeTab(item);	// removeTab��item���폜.
    	
    	// �ʏ�̑I����L���ɂ����Ȃ����߂ɂ�true��Ԃ�.
    	return true;	// return��true��Ԃ�.
    	
    }
    
    // �^�u�ꗗ�̎擾.
    public List<TabItem> getActiveTabs(){
    	
    	// tabs�̍쐬
        List<TabItem> tabs = new ArrayList<TabItem>();	// �^�u�Xtabs�̐���.
        
        // �\�[�g�ς݃G���g���[���X�g����TabInfo�����o����, TabItem�ɃZ�b�g.
        for (Entry<String, TabInfo> entry : mApp.getTabMapEntryList()){
        	TabItem item = new TabItem();	// TabItem�I�u�W�F�N�gitem�𐶐�.
        	TabInfo tabInfo = entry.getValue();	// tabInfo�擾.
    		item.title = tabInfo.title;	// title
    		item.url = tabInfo.url;	// url
    		item.tabName = tabInfo.tabName;	// �^�u��.
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

        // AdapterView.OnItemLongClickListener�̃Z�b�g.
        lvTabs.setOnItemLongClickListener(this);	// this(���g)���Z�b�g.
        
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
    
    // �^�u�̍폜.
    public void removeTab(TabItem item){
    	
    	// item�̃^�u�����擾.
    	String tabName = item.tabName;	// item.tabName��tabName���擾.
    	
    	// �^�u�̍폜.
    	mApp.mTabMap.remove(tabName);	// tabName�œo�^���ꂽtabInfo��mTabMap����폜.
    	
    	// ListView�̎擾
    	ListView lvTabs = (ListView)findViewById(R.id.listview_tabs);	// ���X�g�r���[lvTabs�̎擾.
    	
    	// adapter�̎擾.
    	TabAdapter adapter = (TabAdapter)lvTabs.getAdapter();	// lvTabs.getAdapter��adapter���擾.
    	
    	// �폜.
    	adapter.remove(item);	// adapter.remove��item���폜.
    	
    	// �X�V.
    	adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
    	
    }
    
}