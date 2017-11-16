// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

// �u�b�N�}�[�N�A�N�e�B�r�e�B�N���XBookmarkActivity
public class BookmarkActivity extends Activity implements OnItemClickListener {	// AdapterView.OnItemClickListener�C���^�[�t�F�[�X�̒ǉ�.

	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_bookmark);	// setContentView��R.layout.activity_bookmark���Z�b�g.

        // bookmark�̍쐬
        List<BookmarkItem> bookmark = new ArrayList<BookmarkItem>();	// �u�b�N�}�[�Nbookmark�̐���.

        // adapter�̐���
        BookmarkAdapter adapter = new BookmarkAdapter(this, R.layout.adapter_bookmark_item, bookmark);	// �A�_�v�^adapter�̐���.
        
        // ListView�̎擾
        ListView lvBookmark = (ListView)findViewById(R.id.listview_bookmark);	// ���X�g�r���[lvBookmark�̎擾.
        
        // ���X�g�r���[�ɃA�_�v�^���Z�b�g.
        lvBookmark.setAdapter(adapter);	//  lvBookmark.setAdapter��adapter���Z�b�g.
        
        // �S�Ẵu�b�N�}�[�N���擾��, �A�C�e���ɒǉ�.
        String[] projection = new String[]{	// �擾�������J�������̔z��projection.
        		Browser.BookmarkColumns._ID,	// ID.
        		Browser.BookmarkColumns.TITLE,	// �^�C�g��.
        		Browser.BookmarkColumns.URL	// URL.
        };
        Cursor c = getContentResolver().query(Browser.BOOKMARKS_URI, projection, null, null, Browser.BookmarkColumns._ID + " desc");	// getContentResolver().query�Ńu�b�N�}�[�N�擾.(Browser.BookmarkColumns._ID + " desc"�ō~���\�[�g.)
        if (c.moveToFirst()){	// �ŏ��̈ʒu�Ɉړ�.
        	do{
        		String title = c.getString(c.getColumnIndex(Browser.BookmarkColumns.TITLE));	// title�̎擾.
        		String url = c.getString(c.getColumnIndex(Browser.BookmarkColumns.URL));	// url�̎擾.
        		BookmarkItem item = new BookmarkItem();	// item�𐶐�.
                item.title = title;	// item.title��title���Z�b�g.
                item.url = url;	// item.url��url���Z�b�g.
                adapter.add(item);	// adapter.add��item��ǉ�.
        	} while(c.moveToNext());	// ���ֈړ�.
        }
        c.close();	// c.close�ŕ���.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
        // AdapterView.OnItemClickListener�̃Z�b�g.
        lvBookmark.setOnItemClickListener(this);	// this(���g)���Z�b�g.
        
    }
    
    // ���X�g�r���[�̃A�C�e�����I�����ꂽ��.
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// �I�����ꂽ�A�C�e���̎擾.
    	final ListView lv = (ListView)parent;	// parent��ListView�I�u�W�F�N�glv�ɃL���X�g.
    	final BookmarkItem item = (BookmarkItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
    	
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