package com.jayden.jaydenrich.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.jayden.jaydenrich.utils.LogUtils;

/**
 * 只要是Activity总要初始化view
 * initContentView
 * Created by Jayden on 2016/7/23.
 */
public abstract class JAbsActivity extends AppCompatActivity {

    public static final String TAG = JAbsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "activity_onCreate");
        initContentView();
        initData(savedInstanceState);
    }

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initContentView();

    @Override
    protected void onPause() {
        super.onPause();
        LogUtils.d(TAG, "activity_onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, "activity_onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.d(TAG, "activity_onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "activity_onDestroy");
    }
}
