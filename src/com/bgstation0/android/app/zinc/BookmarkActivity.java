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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

// �u�b�N�}�[�N�A�N�e�B�r�e�B�N���XBookmarkActivity
public class BookmarkActivity extends Activity implements OnItemClickListener, OnItemLongClickListener{	// AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener�C���^�[�t�F�[�X�̒ǉ�.

	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_bookmark);	// setContentView��R.layout.activity_bookmark���Z�b�g.
        
        // �u�b�N�}�[�N�̃��[�h.
        loadBookmarks();	// loadBookmarks�Ńu�b�N�}�[�N�����[�h���ĕ\��.
        
    }
    
    // ���X�g�r���[�̃A�C�e�����I�����ꂽ��.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// �I�����ꂽ�A�C�e���̎擾.
    	final ListView lv = (ListView)parent;	// parent��ListView�I�u�W�F�N�glv�ɃL���X�g.
    	final BookmarkItem item = (BookmarkItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
    	
    	// �^�C�g����URL�𑗂�Ԃ�.
    	setReturnItem(item.title, item.url);	// setReturnItem��item.title��item.url�𑗂�Ԃ��f�[�^�Ƃ��ăZ�b�g.
    	finish();	// finish�ł��̃A�N�e�B�r�e�B�����.
    	
    }
    
    // ���X�g�r���[�̃A�C�e�������������ꂽ��.
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
    	
    	// �I�����ꂽ�A�C�e���̎擾.
    	final ListView lv = (ListView)parent;	// parent��ListView�I�u�W�F�N�glv�ɃL���X�g.
    	BookmarkItem item = (BookmarkItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
    	
    	// ���������ꂽ�A�C�e�����u�b�N�}�[�N����폜.
    	removeBookmark(item);	// removeBookmark��item���폜.
    	
    	// �ʏ�̑I����L���ɂ����Ȃ����߂ɂ�true��Ԃ�.
    	return true;	// return��true��Ԃ�.
    	
    }
    
    // �u�b�N�}�[�N�ꗗ�̎擾.
    public List<BookmarkItem> getAllBookmarks(){
    	
    	// bookmarks�̍쐬
        List<BookmarkItem> bookmarks = new ArrayList<BookmarkItem>();	// �u�b�N�}�[�Nbookmarks�̐���.

        // �S�Ẵu�b�N�}�[�N���擾��, �A�C�e���ɒǉ�.
        String[] projection = new String[]{	// �擾�������J�������̔z��projection.
        		Browser.BookmarkColumns._ID,	// ID.
        		Browser.BookmarkColumns.TITLE,	// �^�C�g��.
        		Browser.BookmarkColumns.URL,	// URL.
        		Browser.BookmarkColumns.BOOKMARK	// �u�b�N�}�[�N�t���O.
        };
        Cursor c = getContentResolver().query(Browser.BOOKMARKS_URI, projection, Browser.BookmarkColumns.BOOKMARK + " = 1", null, Browser.BookmarkColumns.DATE + " desc");	// getContentResolver().query�Ńu�b�N�}�[�N�擾.(Browser.BookmarkColumns.DATE + " desc"�ō~���\�[�g, ��3������Browser.BookmarkColumns.BOOKMARK + " = 1"���w�肷��ƃu�b�N�}�[�N�ƂȂ�.)
        if (c.moveToFirst()){	// �ŏ��̈ʒu�Ɉړ�.
        	do{
        		String title = c.getString(c.getColumnIndex(Browser.BookmarkColumns.TITLE));	// title�̎擾.
        		String url = c.getString(c.getColumnIndex(Browser.BookmarkColumns.URL));	// url�̎擾.
        		BookmarkItem item = new BookmarkItem();	// item�𐶐�.
                item.title = title;	// item.title��title���Z�b�g.
                item.url = url;	// item.url��url���Z�b�g.
                bookmarks.add(item);	// bookmarks.add��item��ǉ�.
        	} while(c.moveToNext());	// ���ֈړ�.
        }
        c.close();	// c.close�ŕ���.
        
        // bookmarks��Ԃ�.
        return bookmarks;	// return��bookmarks��Ԃ�.
        
    }
    
    // �u�b�N�}�[�N�̃��[�h.
    public void loadBookmarks(){
    	
    	// adapter�̐���
        BookmarkAdapter adapter = new BookmarkAdapter(this, R.layout.adapter_bookmark_item, getAllBookmarks());	// �A�_�v�^adapter�̐���.(getAllBookmarks()�Ńu�b�N�}�[�N���X�g���擾��, ��3�����ɃZ�b�g.)
        
        // ListView�̎擾
        ListView lvBookmark = (ListView)findViewById(R.id.listview_bookmark);	// ���X�g�r���[lvBookmark�̎擾.
        
        // ���X�g�r���[�ɃA�_�v�^���Z�b�g.
        lvBookmark.setAdapter(adapter);	//  lvBookmark.setAdapter��adapter���Z�b�g.
        
        // �X�V.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
        // AdapterView.OnItemClickListener�̃Z�b�g.
        lvBookmark.setOnItemClickListener(this);	// this(���g)���Z�b�g.
        
        // AdapterView.OnItemLongClickListener�̃Z�b�g.
        lvBookmark.setOnItemLongClickListener(this);	// this(���g)���Z�b�g.
        
    }
    
    // ����Ԃ��C���e���g�Ƀ^�C�g����URL���Z�b�g.
    public void setReturnItem(String title, String url){
    	
    	// ����Ԃ��C���e���g��������, finish���邱�ƂŖ߂�����Ƀf�[�^���Ԃ�.
       	Intent data = new Intent();	// Intent�I�u�W�F�N�gdata�̍쐬.
   	    Bundle bundle = new Bundle();	// Bundle�I�u�W�F�N�gbundle�̍쐬.
   	    bundle.putString("title", title);	// bundle.putString�L�["title", �ltitle��o�^.
   	    bundle.putString("url", url);	// bundle.putString�ŃL�["url", �lurl��o�^.
   	    data.putExtras(bundle);	// data.putExtras��bundle��o�^.
   	    setResult(RESULT_OK, data);	// setResult��RESULT_OK��data���Z�b�g.
    	    	
    }
    
    // �u�b�N�}�[�N�̍폜.
    public void removeBookmark(BookmarkItem item){
    	
    	// ContentResolver����̍폜.
    	int row = getContentResolver().delete(Browser.BOOKMARKS_URI, Browser.BookmarkColumns.URL + "=?", new String[]{item.url});

    	// ListView�̎擾
        ListView lvBookmark = (ListView)findViewById(R.id.listview_bookmark);	// ���X�g�r���[lvBookmark�̎擾.
        
        // adapter�̎擾.
        BookmarkAdapter adapter = (BookmarkAdapter)lvBookmark.getAdapter();	// lvBookmark.getAdapter��adapter���擾.        
        
        // �폜.
        adapter.remove(item);	// adapter.remove��item���폜.
        
        // �X�V.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
    }
    
}