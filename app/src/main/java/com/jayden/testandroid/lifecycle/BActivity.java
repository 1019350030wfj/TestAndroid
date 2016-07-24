package com.jayden.testandroid.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/7/19.
 */
public class BActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_b);
        Log.d("jayden","B onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("jayden"," B onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("jayden"," B onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("jayden"," B onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("jayden"," B onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("jayden","B onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("jayden"," B onDestroy");
    }
}
