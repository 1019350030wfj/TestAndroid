package com.jayden.jaydenrich.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class AppUtils {

    /**
     * 获取当前版本号
     */
    public static int getVersionCode(Context context) {
        int mVersionCode = 1;
        try {// 获取内部版本号
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            mVersionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        } finally {
            return mVersionCode;
        }
    }

    /**
     * 获取当前版本名
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        String mVersionName = "1.0.0";
        try {// 获取内部版本号
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            mVersionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
        } finally {
            return mVersionName;
        }
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        final WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        boolean isPortrait = displayMetrics.widthPixels < displayMetrics.heightPixels;
        return isPortrait ? displayMetrics.widthPixels
                : displayMetrics.heightPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight(Context context) {
        final WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        boolean isPortrait = displayMetrics.widthPixels < displayMetrics.heightPixels;
        return isPortrait ? displayMetrics.heightPixels
                : displayMetrics.widthPixels;
    }

    /**
     * 获取IMEI
     */
    public static String getSystemImei(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    /**
     * dip转px
     *
     * @param context 设备上下文环境
     * @param dpValue dp数值
     * @return px数值
     */
    public static float dipToPx(Context context, float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                context.getResources().getDisplayMetrics());
    }

    /**
     * px 转dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 检测Sdcard是否存在
     *
     * @return
     */
    public static boolean isExitsSdcard() {
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 判断sd卡是否处于可以读写状态
     *
     * @return
     */
    public static boolean isSdcardReadable() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)
                || Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /**
     * 判断sd卡是否处于可以写入状态
     */
    public static boolean isSdcardWritable() {
        final String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
