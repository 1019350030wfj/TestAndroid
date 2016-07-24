package com.jayden.testandroid.lifecycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jayden.testandroid.R;
import com.jayden.testandroid.question.edittext.EdittextFocus;

/**
 * Created by Jayden on 2016/7/19.
 */
public class AActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        Log.d("jayden","onCreate");
        findViewById(R.id.circleImageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AActivity.this,EdittextFocus.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("jayden","onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("jayden","onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("jayden","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("jayden","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("jayden","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("jayden","onDestroy");
    }
}
