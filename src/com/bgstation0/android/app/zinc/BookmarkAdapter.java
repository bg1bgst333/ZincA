// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

// �u�b�N�}�[�N�A�_�v�^�N���XBookmarkAdapter
public class BookmarkAdapter extends ArrayAdapter<BookmarkItem> {

	// �����o�t�B�[���h�̒�`
	private LayoutInflater inflater;	// LayoutInflater�I�u�W�F�N�ginflater
	
	// �R���X�g���N�^
	public BookmarkAdapter(Context context, int resource){
		
		// �C���t���[�^�̎擾
		super(context, resource);	// �e�R���X�g���N�^���Ă�.
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);	// context.getSystemService��LAYOUT_INFLATER_SERVICE���擾��, inflater�Ɋi�[.
				
	}
		
	// �R���X�g���N�^
	public BookmarkAdapter(Context context, int resource, List<BookmarkItem> objects){
		
		// �C���t���[�^�̎擾
		super(context, resource, objects);	// �e�R���X�g���N�^���Ă�.
		inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);	// context.getSystemService��LAYOUT_INFLATER_SERVICE���擾��, inflater�Ɋi�[.
		
	}
	
	// �r���[�̎擾
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		
		// convertView����擾�������ꂼ��̃r���[�ɃA�C�e���̊e�v�f���Z�b�g����.
		if (convertView == null){	// convertView��null�̎�.
			// �C���t���[�^�����\�[�X���琶��.
			convertView = inflater.inflate(R.layout.adapter_bookmark_item, null);	// inflater.inflate��R.layout.adapter_bookmark_item����convertView�𐶐�.
		}
		TextView tvTitle = (TextView)convertView.findViewById(R.id.textview_bookmark_item_title);	// findViewById��R.id.textview_bookmark_item_title����tvTitle���擾.
		TextView tvUrl = (TextView)convertView.findViewById(R.id.textview_bookmark_item_url);	// findViewById��R.id.textview_bookmark_item_url����tvUrl���擾.
		tvTitle.setText(getItem(position).title);	// getItem(position)�Ŏ擾����BookmarkItem��title��tvTitle.setText�ŃZ�b�g.
		tvUrl.setText(getItem(position).url);	// getItem(position)�Ŏ擾����BookmarkItem��url��tvUrl.setText�ŃZ�b�g.
		return convertView;	// convertView��Ԃ�.
		
	}
	
}