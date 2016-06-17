package com.jayden.testandroid.plugin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/6/15.
 * Email : 1570713698@qq.com
 */
public class TestPluginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ClassLoader classLoader = getClassLoader();
        if (classLoader != null) {
            Log.d("jayden","onCreate classLoader = " + classLoader.toString());
            while (classLoader.getParent() != null) {
                classLoader = classLoader.getParent();
                Log.d("jayden","while classLoader = " + classLoader.toString());
            }
        }
    }
}
