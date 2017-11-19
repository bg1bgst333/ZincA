// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

// �q�X�g���[�A�N�e�B�r�e�B�N���XHistoryActivity
public class HistoryActivity extends Activity implements OnItemClickListener {	// AdapterView.OnItemClickListener�C���^�[�t�F�[�X�̒ǉ�.

	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_history);	// setContentView��R.layout.activity_history���Z�b�g.
        
        // history�̍쐬
        List<HistoryItem> history = new ArrayList<HistoryItem>();	// �u�b�N�}�[�Nhistory�̐���.

        // adapter�̐���
        HistoryAdapter adapter = new HistoryAdapter(this, R.layout.adapter_history_item, history);	// �A�_�v�^adapter�̐���.
        
        // ListView�̎擾
        ListView lvHistory = (ListView)findViewById(R.id.listview_history);	// ���X�g�r���[lvHistory�̎擾.
        
        // ���X�g�r���[�ɃA�_�v�^���Z�b�g.
        lvHistory.setAdapter(adapter);	//  lvHistory.setAdapter��adapter���Z�b�g.
        
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
                adapter.add(item);	// adapter.add��item��ǉ�.
        	} while(c.moveToNext());	// ���ֈړ�.
        }
        c.close();	// c.close�ŕ���.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
        // AdapterView.OnItemClickListener�̃Z�b�g.
        lvHistory.setOnItemClickListener(this);	// this(���g)���Z�b�g.
        
    }
    
    // ���X�g�r���[�̃A�C�e�����I�����ꂽ��.
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// �I�����ꂽ�A�C�e���̎擾.
    	final ListView lv = (ListView)parent;	// parent��ListView�I�u�W�F�N�glv�ɃL���X�g.
    	final HistoryItem item = (HistoryItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
    	
    	// ����Ԃ��C���e���g��������, finish���邱�ƂŖ߂�����Ƀf�[�^���Ԃ�.
    	Intent data = new Intent();	// Intent�I�u�W�F�N�gdata�̍쐬.
    	Bundle bundle = new Bundle();	// Bundle�I�u�W�F�N�gbundle�̍쐬.
    	bundle.putString("title", item.title);	// bundle.putString�ŃL�["title", �litem.title��o�^.
    	bundle.putString("url", item.url);	// bundle.putString�ŃL�["url", �litem.url��o�^.
    	data.putExtras(bundle);	// data.putExtras��bundle��o�^.
    	setResult(RESULT_OK, data);	// setResult��RESULT_OK��data���Z�b�g.
    	finish();	// finish�ł��̃A�N�e�B�r�e�B�����.
    	
    }
    
}