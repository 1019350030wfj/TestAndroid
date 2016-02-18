package com.jayden.testandroid.test_aidl;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.jayden.testandroid.aidl.IMyService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/2/17.
 */
public class MyService extends Service {

    private final static String TAG = "MyService";
    private static final String PACKAGE_SAYHI = "com.example.test";

    private NotificationManager mNotificationManager;
    private boolean mCanRun = true;
    private List<Student> mStudents = new ArrayList<Student>();

    //实现了aidl中的抽象函数
    private final IMyService.Stub mBinder = new IMyService.Stub() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
