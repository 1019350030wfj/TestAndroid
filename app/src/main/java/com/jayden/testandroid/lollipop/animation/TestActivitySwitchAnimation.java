package com.jayden.testandroid.lollipop.animation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/5/12.
 * Email : 1570713698@qq.com
 */
public class TestActivitySwitchAnimation extends AppCompatActivity {

    private FloatingActionButton mFab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swicth_anim);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void startOtherActivity(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //共享的view ： mFab，共享的名称：getTransitionName
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this,mFab,mFab.getTransitionName());
            startActivity(new Intent(this,OtherActivity.class),options.toBundle());
        } else {
            startActivity(new Intent(this,OtherActivity.class));
        }
    }
}
