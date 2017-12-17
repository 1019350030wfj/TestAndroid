package com.jayden.versionopo.api.plugin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jayden.versionopo.R;

/**
 *
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);
        findViewById(R.id.jayden).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("jayden","wfj onclick");
            }
        });
//        show();
    }

//    @Cost
//    public void show() {
//        for (int i = 0; i < 100; i++) {
//
//        }
//    }
}
