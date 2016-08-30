package com.jayden.testandroid.customview.progressdialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.jayden.testandroid.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jayden on 2016/7/25.
 */
public class TestProgressDialogActivity extends Activity {
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_progressdialog);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ProgressDialog mProgress = new ProgressDialog(TestProgressDialogActivity.this);
//                mProgress.incrementProgressBy(1);
//                mProgress.setMessage("jayden test");
//                mProgress.setCancelable(true);
//                mProgress.show();

                final CircleProgressDialog dialog = new CircleProgressDialog(TestProgressDialogActivity.this);
                dialog.show();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dialog.setProgress();
                            }
                        });
                    }
                }, 1000, 100);
            }
        });
    }
}
