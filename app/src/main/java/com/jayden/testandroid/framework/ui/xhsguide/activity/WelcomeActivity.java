package com.jayden.testandroid.framework.ui.xhsguide.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/4/7.
 * Email : 1570713698@qq.com
 */
public class WelcomeActivity extends FragmentActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xhs_welcome);
    }
}
