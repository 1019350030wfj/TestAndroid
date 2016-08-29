package com.jayden.testandroid.customview.progressdialog;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/7/25.
 */
public class TestProgressDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_progressdialog);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog mProgress = new ProgressDialog(TestProgressDialogActivity.this);
                mProgress.incrementProgressBy(1);
                mProgress.setMessage("jayden test");
                mProgress.setCancelable(true);
                mProgress.show();
            }
        });
    }
}
