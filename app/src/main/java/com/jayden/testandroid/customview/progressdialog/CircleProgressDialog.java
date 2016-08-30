package com.jayden.testandroid.customview.progressdialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.jayden.testandroid.R;


/**
 * Created by Jayden on 2016/8/25.
 */

public class CircleProgressDialog extends Dialog{

    private CircleProgress circleProgress;

    public CircleProgressDialog(Context context) {
        super(context, R.style.alert_dialog);
        setCancelable(true);
        setCanceledOnTouchOutside(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_circle_progress);
        circleProgress = (CircleProgress) findViewById(R.id.circle_progress);
    }

    public void setProgress() {
        circleProgress.setProgress(circleProgress.getProgress() + 1);
    }
}
