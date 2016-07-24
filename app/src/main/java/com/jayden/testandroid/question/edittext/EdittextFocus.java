package com.jayden.testandroid.question.edittext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jayden.testandroid.R;
import com.jayden.testandroid.lifecycle.BActivity;

/**
 * Created by Jayden on 2016/7/22.
 */
public class EdittextFocus extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edittext_focus);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EdittextFocus.this,BActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("jayden"," EdittextFocus onDestroy");
    }
}
