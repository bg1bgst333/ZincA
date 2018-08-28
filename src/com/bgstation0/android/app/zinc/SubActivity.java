package com.bgstation0.android.app.zinc;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SubActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        
        // Bundle���`�F�b�N.
        Bundle args = getIntent().getExtras();	// args�擾.
        if (args != null){	// null�łȂ�.
	        String tag = args.getString("tag");	// tag�擾.
	        if (tag.equals("Activity3")){	// "Activity3"�̎�.
	        	TextView tv = (TextView)findViewById(R.id.textview_sub);	// tv�擾.
	        	tv.setText("Activity3 Text.");	// "Activity3 Text."���Z�b�g.
	        }
        }
    }
    
}