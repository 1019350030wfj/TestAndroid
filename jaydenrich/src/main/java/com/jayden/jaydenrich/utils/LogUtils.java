package com.jayden.jaydenrich.utils;

import com.jayden.jaydenrich.app.BaseAppConfig;

/**
 * 打印log工具类
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class LogUtils {

    public static void d(String msg) {
        d(BaseAppConfig.TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (BaseAppConfig.IS_DEBUG) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable tr) {
        if (BaseAppConfig.IS_DEBUG) {
            android.util.Log.d(tag, msg, tr);
        }
    }

    public static void e(String msg) {
        e(BaseAppConfig.TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (BaseAppConfig.IS_DEBUG) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (BaseAppConfig.IS_DEBUG) {
            android.util.Log.e(tag, msg, tr);
        }
    }

    public static void i(String msg) {
        i(BaseAppConfig.TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (BaseAppConfig.IS_DEBUG) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (BaseAppConfig.IS_DEBUG) {
            android.util.Log.i(tag, msg, tr);
        }
    }

    public static void v(String msg) {
        v(BaseAppConfig.TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (BaseAppConfig.IS_DEBUG) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (BaseAppConfig.IS_DEBUG) {
            android.util.Log.v(tag, msg, tr);
        }
    }

    public static void w(String msg) {
        w(BaseAppConfig.TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (BaseAppConfig.IS_DEBUG) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (BaseAppConfig.IS_DEBUG) {
            android.util.Log.w(tag, msg, tr);
        }
    }
}
