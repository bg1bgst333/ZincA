// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

// �q�X�g���[�A�N�e�B�r�e�B�N���XHistoryActivity
public class HistoryActivity extends Activity implements OnItemClickListener, OnItemLongClickListener {	// AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener�C���^�[�t�F�[�X�̒ǉ�.

	// �����o�t�B�[���h�̏�����.
	public static final int DIALOG_ID_CONFIRM_HISTORY_REMOVE = 0;	// �����m�F�̃_�C�A���OID.
	
	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_history);	// setContentView��R.layout.activity_history���Z�b�g.
        
        // �����̃��[�h.
        loadHistories();	// loadHistories�ŗ��������[�h.
        
    }
    
    // ���X�g�r���[�̃A�C�e�����I�����ꂽ��.
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// �I�����ꂽ�A�C�e���̎擾.
    	final ListView lv = (ListView)parent;	// parent��ListView�I�u�W�F�N�glv�ɃL���X�g.
    	final HistoryItem item = (HistoryItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
    	
    	// �^�C�g����URL�𑗂�Ԃ�.
    	setReturnItem(item.title, item.url);	// setReturnItem��item.title��item.url�𑗂�Ԃ��f�[�^�Ƃ��ăZ�b�g.
    	finish();	// finish�ł��̃A�N�e�B�r�e�B�����.
    	
    }
    
    // ���X�g�r���[�̃A�C�e�������������ꂽ��.
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// �I�����ꂽ�A�C�e���̎擾.
    	final ListView lv = (ListView)parent;	// parent��ListView�I�u�W�F�N�glv�ɃL���X�g.
    	HistoryItem item = (HistoryItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
    	
    	// �폜�m�F�_�C�A���O
    	showConfirmHistoryRemove(position);	// showConfirmHistoryRemove�ō폜�m�F�_�C�A���O�̕\��.(position��n��.)
    	
    	// �ʏ�̑I����L���ɂ����Ȃ����߂ɂ�true��Ԃ�.
    	return true;	// return��true��Ԃ�.
    	
    }
    
    // �_�C�A���O�̍쐬��.
    @Override
    protected Dialog onCreateDialog(final int id, final Bundle args){
    	
    	// id���ƂɐU�蕪��.
    	if (id == DIALOG_ID_CONFIRM_HISTORY_REMOVE){	// �����폜�m�F.
    		
    		// �A���[�g�_�C�A���O�̍쐬.
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);	// builder�̍쐬.
    		builder.setTitle(getString(R.string.dialog_title_confirm_history_remove));	// �^�C�g���̃Z�b�g.
    		builder.setMessage(getString(R.string.dialog_message_confirm_history_remove));	// ���b�Z�[�W�̃Z�b�g.
    		builder.setPositiveButton(getString(R.string.dialog_button_positive_yes), new DialogInterface.OnClickListener() {
				
    			// "�͂�"�{�^�����I�����ꂽ��.
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int position = args.getInt("position");	// position���擾.
					final ListView lv = (ListView)findViewById(R.id.listview_history);	// ���X�g�r���[lv�̎擾.
					final HistoryItem item = (HistoryItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
					removeHistory(item);	// removeHistory�ō폜.
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
    
    // �����ꗗ�̎擾.
    public List<HistoryItem> getAllHistories(){
    	
    	// histories�̍쐬
    	List<HistoryItem> histories = new ArrayList<HistoryItem>();	// ����histories�̐���.
    
    	// �S�Ẵq�X�g���[���擾��, �A�C�e���ɒǉ�.
        String[] projection = new String[]{	// �擾�������J�������̔z��projection.
        		Browser.BookmarkColumns._ID,	// ID.
        		Browser.BookmarkColumns.TITLE,	// �^�C�g��.
        		Browser.BookmarkColumns.URL,	// URL.
        		Browser.BookmarkColumns.DATE,	// ����.
        		Browser.BookmarkColumns.BOOKMARK,	// �u�b�N�}�[�N�t���O.
        };
        Cursor c = getContentResolver().query(Browser.BOOKMARKS_URI, projection, Browser.BookmarkColumns.BOOKMARK + " = 0", null, Browser.BookmarkColumns.DATE + " desc");	// getContentResolver().query�ŗ����擾.(Browser.BookmarkColumns.DATE + " desc"�ō~���\�[�g, ��3������Browser.BookmarkColumns.BOOKMARK + " = 0"���w�肷��Ɨ����ƂȂ�.)
        if (c.moveToFirst()){	// �ŏ��̈ʒu�Ɉړ�.
        	do{
        		String title = c.getString(c.getColumnIndex(Browser.BookmarkColumns.TITLE));	// title�̎擾.
        		String url = c.getString(c.getColumnIndex(Browser.BookmarkColumns.URL));	// url�̎擾.
        		long date = c.getLong(c.getColumnIndex(Browser.BookmarkColumns.DATE));	// date�̎擾.
        		HistoryItem item = new HistoryItem();	// item�𐶐�.
                item.title = title;	// item.title��title���Z�b�g.
                item.url = url;	// item.url��url���Z�b�g.
                //item.date = Long.toString(date);	// date��Long.toString�ŕ�����ϊ�����item.date�ɃZ�b�g.
                Date dtDate = new Date(date);	// date�����Date�I�u�W�F�N�gdtDate���쐬.
                item.date = dtDate.toString();	// item.date��dtDate.toString�ŕϊ�����������������Z�b�g.
                histories.add(item);	// histories.add��item��ǉ�.
        	} while(c.moveToNext());	// ���ֈړ�.
        }
        c.close();	// c.close�ŕ���.
        
        // histories��Ԃ�.
        return histories;	// return��histories��Ԃ�.
        
    }
    
    // �����̃��[�h.
    public void loadHistories(){
    	
    	// adapter�̐���
        HistoryAdapter adapter = new HistoryAdapter(this, R.layout.adapter_history_item, getAllHistories());	// �A�_�v�^adapter�̐���.(getAllHistories()�ŗ������X�g���擾��, ��3�����ɃZ�b�g.)
        
        // ListView�̎擾
        ListView lvHistory = (ListView)findViewById(R.id.listview_history);	// ���X�g�r���[lvHistory�̎擾.
        
        // ���X�g�r���[�ɃA�_�v�^���Z�b�g.
        lvHistory.setAdapter(adapter);	//  lvHistory.setAdapter��adapter���Z�b�g.
        
        // �X�V.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
        // AdapterView.OnItemClickListener�̃Z�b�g.
        lvHistory.setOnItemClickListener(this);	// this(���g)���Z�b�g.
        
        // AdapterView.OnItemLongClickListener�̃Z�b�g.
        lvHistory.setOnItemLongClickListener(this);	// this(���g)���Z�b�g.
        
    }
    
    // ����Ԃ��C���e���g�Ƀ^�C�g����URL���Z�b�g.
    public void setReturnItem(String title, String url){
    	
    	// ����Ԃ��C���e���g��������, finish���邱�ƂŖ߂�����Ƀf�[�^���Ԃ�.
    	Intent data = new Intent();	// Intent�I�u�W�F�N�gdata�̍쐬.
    	Bundle bundle = new Bundle();	// Bundle�I�u�W�F�N�gbundle�̍쐬.
    	bundle.putString("title", title);	// bundle.putString�ŃL�["title", �ltitle��o�^.
    	bundle.putString("url", url);	// bundle.putString�ŃL�["url", �lurl��o�^.
    	data.putExtras(bundle);	// data.putExtras��bundle��o�^.
    	setResult(RESULT_OK, data);	// setResult��RESULT_OK��data���Z�b�g.
    	
    }
    
    // �����̍폜.
    public void removeHistory(HistoryItem item){
    	
    	// ContentResolver����̍폜.
    	int row = getContentResolver().delete(Browser.BOOKMARKS_URI, Browser.BookmarkColumns.URL + "=?", new String[]{item.url});
    	
    	// ListView�̎擾
    	ListView lvHistory = (ListView)findViewById(R.id.listview_history);	// ���X�g�r���[lvHistory�̎擾.
    	
    	// adapter�̎擾.
    	HistoryAdapter adapter = (HistoryAdapter)lvHistory.getAdapter();	// lvHistory.getAdapter��adapter���擾.
    	
    	// �폜.
    	adapter.remove(item);	// adapter.remove��item���폜.
    	
    	// �X�V.
    	adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
    	
    }
    
    // �����̍폜�m�F�_�C�A���O.
    private void showConfirmHistoryRemove(int position){
    	
    	// �_�C�A���O�̕\��.
    	Bundle bundle = new Bundle();	// bundle�쐬.
    	bundle.putInt("position", position);	// position��o�^.
    	showDialog(DIALOG_ID_CONFIRM_HISTORY_REMOVE, bundle);	// showDialog��DIALOG_ID_CONFIRM_HISTORY_REMOVE��bundle��n��.
    	
    }
    
}