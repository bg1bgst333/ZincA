// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

// �u�b�N�}�[�N�A�N�e�B�r�e�B�N���XBookmarkActivity
public class BookmarkActivity extends Activity implements OnItemClickListener, OnItemLongClickListener{	// AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener�C���^�[�t�F�[�X�̒ǉ�.

	// �����o�t�B�[���h�̏�����.
	public static final int DIALOG_ID_CONFIRM_BOOKMARK_REMOVE = 0;	// �u�b�N�}�[�N�폜�m�F�̃_�C�A���OID.
	public static final int DIALOG_ID_CONFIRM_ALL_BOOKMARKS_REMOVE = 1;	// �u�b�N�}�[�N�S�폜�m�F�̃_�C�A���OID.
	
	// �A�N�e�B�r�e�B���쐬���ꂽ��.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	// �r���[�̃Z�b�g
        super.onCreate(savedInstanceState);	// �e�N���X��onCreate���Ă�.
        setContentView(R.layout.activity_bookmark);	// setContentView��R.layout.activity_bookmark���Z�b�g.
        
        // �u�b�N�}�[�N�̃��[�h.
        loadBookmarks();	// loadBookmarks�Ńu�b�N�}�[�N�����[�h���ĕ\��.
        
    }
    
    // ���j���[���쐬���ꂽ��.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
    	
    	// ���\�[�X���烁�j���[���쐬.
    	getMenuInflater().inflate(R.menu.menu_bookmark, menu);	// getMenuInflater().inflate��R.menu.menu_bookmark���烁�j���[���쐬.
    	return true;	// true��Ԃ�.
    	
    }
    
    // ���j���[���I�����ꂽ��.
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	
    	// �I�����ꂽ���j���[�A�C�e�����ƂɐU�蕪����.
    	int id = item.getItemId();	// item.getItemId�őI�����ꂽ���j���[�A�C�e����id���擾.
    	if (id == R.id.menu_item_remove_all_bookmarks){	// R.id.menu_item_remove_all_bookmarks("�S�Ẵu�b�N�}�[�N���폜")�̎�.
    		
    		// �u�b�N�}�[�N�̑S�폜.
    		showConfirmAllBookmarksRemove();	// showConfirmAllBookmarksRemove�őS�폜���邩�m�F����.
    		
    	}
    	
    	// ���Ƃ͊���̏����ɔC����.
    	return super.onOptionsItemSelected(item);	// �e�N���X��onOptionsItemSelected���Ă�.
    	
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
    	
    	// �폜�m�F�_�C�A���O
    	showConfirmBookmarkRemove(position);	// showConfirmBookmarkRemove�ō폜�m�F�_�C�A���O�̕\��.(position��n��.)
    	
    	// �ʏ�̑I����L���ɂ����Ȃ����߂ɂ�true��Ԃ�.
    	return true;	// return��true��Ԃ�.
    	
    }
    
    // �_�C�A���O�̍쐬��.
    @Override
    protected Dialog onCreateDialog(final int id, final Bundle args){
    
    	// id���ƂɐU�蕪��.
    	if (id == DIALOG_ID_CONFIRM_BOOKMARK_REMOVE){	// �u�b�N�}�[�N�폜�m�F.

    		// �A���[�g�_�C�A���O�̍쐬.
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);	// builder�̍쐬.
    		builder.setTitle(getString(R.string.dialog_title_confirm_bookmark_remove));	// �^�C�g���̃Z�b�g.
    		builder.setMessage(getString(R.string.dialog_message_confirm_bookmark_remove));	// ���b�Z�[�W�̃Z�b�g.
    		builder.setPositiveButton(getString(R.string.dialog_button_positive_yes), new DialogInterface.OnClickListener() {
    			
    			// "�͂�"�{�^�����I�����ꂽ��.
				@Override
				public void onClick(DialogInterface dialog, int which) {
					int position = args.getInt("position");	// position���擾.
			    	final ListView lv = (ListView)findViewById(R.id.listview_bookmark);	// ���X�g�r���[lv�̎擾.
			    	final BookmarkItem item = (BookmarkItem)lv.getItemAtPosition(position);	// lv.getItemAtPosition��item���擾.
			    	removeBookmark(item);	// removeBookmark�ō폜.
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
    	else if (id == DIALOG_ID_CONFIRM_ALL_BOOKMARKS_REMOVE){	// �u�b�N�}�[�N�S�폜�m�F.
    		
    		// �A���[�g�_�C�A���O�̍쐬.
    		AlertDialog.Builder builder = new AlertDialog.Builder(this);	// builder�̍쐬.
    		builder.setTitle(getString(R.string.dialog_title_confirm_all_bookmarks_remove));	// �^�C�g���̃Z�b�g.
    		builder.setMessage(getString(R.string.dialog_message_confirm_all_bookmarks_remove));	// ���b�Z�[�W�̃Z�b�g.
    		builder.setPositiveButton(getString(R.string.dialog_button_positive_yes), new DialogInterface.OnClickListener() {
    			
    			// "�͂�"�{�^�����I�����ꂽ��.
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// �S�Ẵu�b�N�}�[�N���폜.
			    	removeAllBookmarks();	// removeAllBookmarks�őS�폜.
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
    	
    	// BOOKMARK�t���O���~�낷.
    	//Cursor c1 = this.getContentResolver().query(Browser.BOOKMARKS_URI, new String[]{Browser.BookmarkColumns.URL}, null, null, null);
    	//Toast.makeText(this, "all1 = "+String.valueOf(c1.getCount()), Toast.LENGTH_LONG).show();
    	ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�̐���.
    	values.put(Browser.BookmarkColumns.BOOKMARK, "0");	// values.put��BOOKMARK�t���O��"0"�Ƃ��ēo�^.
    	int row = getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.URL + "=?", new String[]{item.url});	// getContentResolver().update��URL�������s���X�V.
    	//Cursor c2 = this.getContentResolver().query(Browser.BOOKMARKS_URI, new String[]{Browser.BookmarkColumns.URL}, null, null, null);
    	//Toast.makeText(this, "all2 = "+String.valueOf(c2.getCount()), Toast.LENGTH_LONG).show();
    	
    	// ListView�̎擾
        ListView lvBookmark = (ListView)findViewById(R.id.listview_bookmark);	// ���X�g�r���[lvBookmark�̎擾.
        
        // adapter�̎擾.
        BookmarkAdapter adapter = (BookmarkAdapter)lvBookmark.getAdapter();	// lvBookmark.getAdapter��adapter���擾.
        
        // �폜.
        adapter.remove(item);	// adapter.remove��item���폜.
        
        // �X�V.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
    }
    
    // �u�b�N�}�[�N�̑S�폜.
    public void removeAllBookmarks(){
    	
    	// BOOKMARK�t���O�̗����Ă���s�S�Ă̂�BOOKMARK�t���O���~�낷.
    	ContentValues values = new ContentValues();	// ContentValues�I�u�W�F�N�gvalues�̐���.
    	values.put(Browser.BookmarkColumns.BOOKMARK, "0");	// values.put��BOOKMARK�t���O��"0"�Ƃ��ēo�^.
    	int row = getContentResolver().update(Browser.BOOKMARKS_URI, values, Browser.BookmarkColumns.BOOKMARK + "=1", null);	// getContentResolver().update��BOOKMARK��1�̍s���X�V.
    	//Toast.makeText(this, String.valueOf("delete row = " + row), Toast.LENGTH_LONG).show();	// row��Toast�ŕ\��.
    	
    	// ListView�̎擾
        ListView lvBookmark = (ListView)findViewById(R.id.listview_bookmark);	// ���X�g�r���[lvBookmark�̎擾.
        
        // adapter�̎擾.
        BookmarkAdapter adapter = (BookmarkAdapter)lvBookmark.getAdapter();	// lvBookmark.getAdapter��adapter���擾.
        
        // �S�폜.
        adapter.clear();	// adapter.clear�őS�폜.
        
        // �X�V.
        adapter.notifyDataSetChanged();	// adapter.notifyDataSetChanged�ōX�V.
        
    }
    
    // �u�b�N�}�[�N�̍폜�m�F�_�C�A���O.
    private void showConfirmBookmarkRemove(int position){
    
    	// �_�C�A���O�̕\��.
    	Bundle bundle = new Bundle();	// bundle�쐬.
    	bundle.putInt("position", position);	// position��o�^.
    	showDialog(DIALOG_ID_CONFIRM_BOOKMARK_REMOVE, bundle);	// showDialog��DIALOG_ID_CONFIRM_BOOKMARK_REMOVE��bundle��n��.
    	
    }
    
    // �u�b�N�}�[�N�̑S�폜�m�F�_�C�A���O.
    private void showConfirmAllBookmarksRemove(){
    	
    	// �_�C�A���O�̕\��.
    	showDialog(DIALOG_ID_CONFIRM_ALL_BOOKMARKS_REMOVE);	// showDialog��DIALOG_ID_CONFIRM_ALL_BOOKMARKS_REMOVE��n��.
    	
    }
    
}