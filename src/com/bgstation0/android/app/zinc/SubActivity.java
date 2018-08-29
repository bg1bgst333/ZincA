package com.bgstation0.android.app.zinc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SubActivity extends Activity {

	// �����o�t�B�[���h�̒�`.
	MainApplication mApp = null;	// mApp��null�ɂ���.
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        
        // ���C���A�v���P�[�V�����̎擾.
    	mApp = (MainApplication)getApplicationContext();	// getApplicationContext�Ŏ擾����MainApplication�I�u�W�F�N�g��mApp�Ɋi�[.
    	
        // Bundle���`�F�b�N.
        Bundle args = getIntent().getExtras();	// args�擾.
        if (args != null){	// null�łȂ�.
	        String tag = args.getString("tag");	// tag�擾.
	        TextView tv = (TextView)findViewById(R.id.textview_sub);	// tv�擾.
        	tv.setText(tag);	// tag���Z�b�g.
        	TabInfo tabInfo = mApp.mHlpr.getTabInfo(tag);
        	tabInfo.title = tag;
        	mApp.mHlpr.updateTabInfo(tag, tabInfo);
        }
    }
    
}