package com.jayden.versionopo.app;

import android.app.Application;

/**
 * Created by Jayden on 2017/4/13.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
    }
}
