// �p�b�P�[�W�̖��O���
package com.bgstation0.android.app.zinc;

//�p�b�P�[�W�̃C���|�[�g
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.Toast;

// �_�E�����[�h���V�[�o�[�N���XDownloadReceiver
public class DownloadReceiver extends BroadcastReceiver {

	// �����o�t�B�[���h�̒�`.
	private Context mContext = null;	// Context�^mContext��null�ɏ�����.
	
	// �����t���R���X�g���N�^
	DownloadReceiver(Context context){
				
		// �����������o�ɃZ�b�g.
		mContext = context;	// mContext��context���Z�b�g.
			
	}
		
	// �u���[�h�L���X�g�C���e���g���󂯎������.
	@Override
	public void onReceive(Context context, Intent intent) {
	
		// �A�N�V���������񂲂Ƃɏ������s��.
		String action = intent.getAction();	// intent.getAction��action���擾.
		if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){	// �_�E�����[�h�����̎�.
			long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,  -1);	// intent����_�E�����[�hID���擾.
			Query query = new Query();	// Query�I�u�W�F�N�gquery���쐬.
			query.setFilterById(id);	// query.setFilterById��id���Z�b�g.
			if (mContext != null){	// mContext��null�łȂ����.
				MainActivity mainActivity = (MainActivity)mContext;	// mContext��mainActivity�ɃL���X�g.
				if (mainActivity.mDownloadManager != null){	// mDownloadManager��null�łȂ����.
					Cursor c = mainActivity.mDownloadManager.query(query);	// mainActivity.mDownloadManager.query��query��n��, Cursor��c���擾.
					if (c.moveToFirst()){	// �擪�Ɉړ�.
						int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));	// �X�e�[�^�X�擾.
						if (status == DownloadManager.STATUS_SUCCESSFUL){	// ����.
							Toast.makeText(mainActivity, mainActivity.getString(R.string.toast_message_download_success), Toast.LENGTH_LONG).show();	// R.string.toast_message_download_success�Œ�`���ꂽ���b�Z�[�W��Toast�ŕ\��.
						}
					}
					c.close();	// c.close�ŕ���.
				}
			}
		}
		
	}

}
