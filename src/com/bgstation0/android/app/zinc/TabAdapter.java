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

// �^�u�A�_�v�^�N���XTabAdapter
public class TabAdapter extends ArrayAdapter<TabItem> {

	// �����o�t�B�[���h�̒�`
	private LayoutInflater inflater;	// LayoutInflater�I�u�W�F�N�ginflater
		
	// �R���X�g���N�^
	public TabAdapter(Context context, int resource, List<TabItem> objects){
			
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
			convertView = inflater.inflate(R.layout.adapter_tab_item, null);	// inflater.inflate��R.layout.adapter_tab_item����convertView�𐶐�.
		}
		TextView tvTabName = (TextView)convertView.findViewById(R.id.textview_tab_item_tabname);	// findViewById��R.id.textview_tab_item_tabname����tvTabName���擾.
		TextView tvTitle = (TextView)convertView.findViewById(R.id.textview_tab_item_title);	// findViewById��R.id.textview_tab_item_title����tvTitle���擾.
		TextView tvUrl = (TextView)convertView.findViewById(R.id.textview_tab_item_url);	// findViewById��R.id.textview_tab_item_url����tvUrl���擾.
		tvTabName.setText(getItem(position).tabName);	// getItem(position)�Ŏ擾����TabItem��tabname��tvTabName.setText�ŃZ�b�g.
		tvTitle.setText(getItem(position).title);	// getItem(position)�Ŏ擾����TabItem��title��tvTitle.setText�ŃZ�b�g.
		tvUrl.setText(getItem(position).url);	// getItem(position)�Ŏ擾����TabItem��url��tvUrl.setText�ŃZ�b�g.
		return convertView;	// convertView��Ԃ�.
			
	}
	
}