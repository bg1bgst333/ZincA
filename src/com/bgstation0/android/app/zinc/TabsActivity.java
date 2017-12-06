// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
	public static final int DIALOG_ID_CONFIRM_TAB_REMOVE = 0;	// �^�u�폜�m�F�̃_�C�A���OID.
	
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
    	
    	// �폜�m�F�_�C�A���O
    	showConfirmTabRemove(position);	// showConfirmTabRemove�ō폜�m�F�_�C�A���O�̕\��.(position��n��.)
    	
    	// �ʏ�̑I����L���ɂ����Ȃ����߂ɂ�true��Ԃ�.
    	return true;	// return��true��Ԃ�.
    	
    }
    
    // �_�C�A���O�̍쐬��.
    @Override
    protected Dialog onCreateDialog(final int id, final Bundle args){
    	
    	// id���ƂɐU�蕪��.
    	if (id == DIALOG_ID_CONFIRM_TAB_REMOVE){	// �^�u�폜�m�F.

    		// �A���[�g�_�C�A���O�̍쐬.
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);	// builder�̍쐬.
    		builder.setTitle(getString(R.string.dialog_title_confirm_tab_remove));	// �^�C�g���̃Z�b�g.
    		builder.setMessage(getString(R.string.dialog_message_confirm_tab_remove));	// ���b�Z�[�W�̃Z�b�g.
    		builder.setPositiveButton(getString(R.string.dialog_button_positive_yes), new DialogInterface.OnClickListener() {
    			
    			// "�͂�"�{�^�����I�����ꂽ��.
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int position = args.getInt("position");	// position���擾.
			    	final ListView lv = (ListView)findViewById(R.id.listview_tabs);	// ���X�g�r���[lv�̎擾.
			    	final TabItem item = (TabItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
			    	removeTab(item);	// removeTab�ō폜.
			    	removeDialog(id);	// removeDialog�Ń_�C�A���O���폜.
				}				
				
			});
    		Dialog dialog = builder.create();	// builder.create��dialog���쐬.(������, �܂��Ԃ��Ȃ�.)
    		dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {	// �L�����Z�����̓����ǉ�.

    			// �_�C�A���O�̃L�����Z����.
				@Override
				public void onCancel(DialogInterface dialog) {
					removeDialog(id);	// removeDialog�Ń_�C�A���O���폜.
				}
				
			});
    		return dialog;	// dialog��Ԃ�.
    		
    	}
    	
    	// null��Ԃ�.
    	return null;	// return��null��Ԃ�.
    	
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
    
    // �^�u�̍폜�m�F�_�C�A���O.
    private void showConfirmTabRemove(int position){
    	
    	// �_�C�A���O�̕\��.
    	Bundle bundle = new Bundle();	// bundle�쐬.
    	bundle.putInt("position", position);	// position��o�^.
    	showDialog(DIALOG_ID_CONFIRM_TAB_REMOVE, bundle);	// showDialog��DIALOG_ID_CONFIRM_TAB_REMOVE��bundle��n��.
    	
    }
    
}