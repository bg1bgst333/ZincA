// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
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
        
        // ���A�C�e���̒ǉ�.
        BookmarkItem item1 = new BookmarkItem();	// item1�𐶐�.
        item1.title = "test1";	// title��"test1".
        item1.url = "http://test1.com";	// url��"http://test1.com"
        BookmarkItem item2 = new BookmarkItem();	// item2�𐶐�.
        item2.title = "test2";	// title��"test2".
        item2.url = "http://test2.com";	// url��"http://test2.com"
        BookmarkItem item3 = new BookmarkItem();	// item3�𐶐�.
        item3.title = "test3";	// title��"test3".
        item3.url = "http://test3.com";	// url��"http://test3.com"
        adapter.add(item1);	// adapter.add��item1��ǉ�.
        adapter.add(item2);	// adapter.add��item2��ǉ�.
        adapter.add(item3);	// adapter.add��item3��ǉ�.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
    }
    
}