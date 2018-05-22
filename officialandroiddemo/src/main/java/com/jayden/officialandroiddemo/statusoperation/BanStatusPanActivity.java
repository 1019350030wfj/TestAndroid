package com.jayden.officialandroiddemo.statusoperation;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;

import com.jayden.officialandroiddemo.BaseTextviewActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BanStatusPanActivity extends BaseTextviewActivity {

    public static final int DISABLE_EXPAND = 0x00010000;//4.2以上的整形标识
    public static final int DISABLE_EXPAND_LOW = 0x00000001;//4.2以下的整形标识

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        mTextView.setText("Prohibition of dropdown");
        disableStatusBar();
//        @SuppressLint("WrongConstant")
//        Object service = getSystemService("statusbar");
//
//        try {
//            Class<?> statusBarManager = Class
//                    .forName("android.app.StatusBarManager");
//            Method expand = statusBarManager.getMethod("disable", int.class);
//            //判断版本大小
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//                expand.invoke(service, DISABLE_EXPAND);
//            }else {
//                expand.invoke(service, DISABLE_EXPAND_LOW);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void disableStatusBar() {
        try {
            Object service = getSystemService("statusbar");
            Class<?> claz = Class.forName("android.app.StatusBarManager");
            Method expand = claz.getMethod("collapsePanels");
            expand.invoke(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void collapseNow() {
//
//        // Initialize 'collapseNotificationHandler'
//        if (collapseNotificationHandler == null) {
//            collapseNotificationHandler = new Handler();
//        }
//
//        // If window focus has been lost && activity is not in a paused state
//        // Its a valid check because showing of notification panel
//        // steals the focus from current activity's window, but does not
//        // 'pause' the activity
//        if (!currentFocus && !isPaused) {
//
//            // Post a Runnable with some delay - currently set to 300 ms
//            collapseNotificationHandler.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    // Use reflection to trigger a method from 'StatusBarManager'
//
//                    Object statusBarService = getSystemService("statusbar");
//                    Class<?> statusBarManager = null;
//
//                    try {
//                        statusBarManager = Class.forName("android.app.StatusBarManager");
//                    } catch (ClassNotFoundException e) {
//                        e.printStackTrace();
//                    }
//
//                    Method collapseStatusBar = null;
//
//                    try {
//
//                        // Prior to API 17, the method to call is 'collapse()'
//                        // API 17 onwards, the method to call is `collapsePanels()`
//
//                        if (Build.VERSION.SDK_INT > 16) {
//                            collapseStatusBar = statusBarManager .getMethod("collapsePanels");
//                        } else {
//                            collapseStatusBar = statusBarManager .getMethod("collapse");
//                        }
//                    } catch (NoSuchMethodException e) {
//                        e.printStackTrace();
//                    }
//
//                    collapseStatusBar.setAccessible(true);
//
//                    try {
//                        collapseStatusBar.invoke(statusBarService);
//                    } catch (IllegalArgumentException e) {
//                        e.printStackTrace();
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    }
//
//                    // Check if the window focus has been returned
//                    // If it hasn't been returned, post this Runnable again
//                    // Currently, the delay is 100 ms. You can change this
//                    // value to suit your needs.
//                    if (!currentFocus && !isPaused) {
//                        collapseNotificationHandler.postDelayed(this, 100L);
//                    }
//
//                }
//            }, 300L);
//        }
//    }
}
