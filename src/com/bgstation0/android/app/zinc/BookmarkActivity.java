// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.widget.ListView;

// �u�b�N�}�[�N�A�N�e�B�r�e�B�N���XBookmarkActivity
public class BookmarkActivity extends Activity {

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
        Cursor c = Browser.getAllBookmarks(getContentResolver());	// getContentResolver()�Ŏ擾����ContentResolver��Browser.getAllBookmarks�ɓn����, �u�b�N�}�[�N�̃J�[�\�����擾.
        if (c.moveToFirst()){	// �ŏ��̈ʒu�Ɉړ�.
        	do{
        		//String title = c.getString(c.getColumnIndex(Browser.BookmarkColumns.TITLE));	// title�̎擾.(���ۂɂ�-1���Ԃ�, �^�C�g�����擾�ł��Ȃ�.)
        		String url = c.getString(c.getColumnIndex(Browser.BookmarkColumns.URL));	// url�̎擾.
        		BookmarkItem item = new BookmarkItem();	// item�𐶐�.
                item.title = "";//title;	// item.title��title���Z�b�g.(�^�C�g�����擾�ł��Ȃ��̂�, �Ƃ肠�����󗓂ɂ���.)
                item.url = url;	// item.url��url���Z�b�g.
                adapter.add(item);	// adapter.add��item��ǉ�.
        	} while(c.moveToNext());	// ���ֈړ�.
        }
        c.close();	// c.close�ŕ���.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
    }
    
}