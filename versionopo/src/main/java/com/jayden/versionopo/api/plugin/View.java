package com.jayden.versionopo.api.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jayden.modifyclassesplugin.HookClickListener;
import com.jayden.versionopo.R;

/**
 * Created by Jayden on 2017/7/21.
 */

public class View extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_third);
        findViewById(R.id.btn_openBT).setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                HookClickListener.onClick(v);
            }
        });
    }
}
