package com.jayden.testandroid.api.windowmanager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/6/14.
 * Email : 1570713698@qq.com
 */
public class TestWMWithNotification extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_wm_with_wm);

        final WMNotification notification = new WMNotification.Builder()
                .setContext(this)
                .setContent("jayden content")
                .setTime(System.currentTimeMillis())
                .setTitle("jayden title")
                .setImgRes(R.drawable.icon_logo)
                .build();

        findViewById(R.id.btn_show_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification.show();

            }
        });

        findViewById(R.id.btn_hide_window).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification.dismiss();
            }
        });
    }
}
