package com.jayden.jaydenrich.app;

import android.content.Context;
import android.text.TextUtils;

import com.jayden.jaydenrich.utils.AppUtils;
import com.jayden.jaydenrich.utils.FileUtils;

/**
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class BaseAppConfig {

    /**
     * TODO
     *
     * 1、在HttpHandler的postAsync（）方法中有一个添加请求头的参数要设置
     * 这个可以在ApiRequest中统一写一个请求方法，在那个方法中判断是否要
     * 设置请求头
     *
     * 2、在HttpHandler的buildMultipartFormRequest（）方法中也是有一个添加
     * 请求头的参数要设置
     *
     * 3、在buildMultipartFormRequest（）中可以设置上传其他文件，这边是上传图片
     *
     * 4、这个类getCacheDir（）缓存目录记得改
     *
     * 5、在app的Application的onCreate方法中调用这个类的initConfig（）
     *
     * 6、ImageUtils的onPause 和 onResume 两个方法必须在BaseActivity中调用
     *
     * 7、替换ImageUtils里面的默认图
     */

    /**
     * 是否处于调试模式
     */
    public static volatile boolean IS_DEBUG = true;
    /**
     * Log TAG
     */
    public static final String TAG = "jayden";

    /**
     * 上传裁剪头像的宽高
     */
    public static final float CROP_AVATER_RATIO = 0.7f;
    public static final int UPLOAD_CROP_AVATER = 276;
    /**
     * 上传图片的最小像素值
     */
    public static final int UPLOAD_IMG_MIN = 960;
    /**
     * 上传图片的最大像素值
     */
    public static final int UPLOAD_IMG_MAX = 1706;

    private static int mVersionCode;
    private static String mVersionName;
    private static String mDeviceId;


    /**
     * 初始化项目配置信息
     */
    public static void initConfig(Context context) {
        getVersionCode(context);
        getVersionName(context);
        getDeviceId(context);
        FileUtils.init(context);
    }

    /**
     * 获取当前APP版本号
     */
    public static int getVersionCode(Context context) {
        if (mVersionCode <= 0) {
            mVersionCode = AppUtils.getVersionCode(context);
        }
        return mVersionCode;
    }

    /**
     * 获取当前APP版本名
     */
    public static String getVersionName(Context context) {
        if (TextUtils.isEmpty(mVersionName)) {
            mVersionName = AppUtils.getVersionName(context);
        }
        return mVersionName;
    }

    /**
     * 获取设备的IMEI号
     */
    public static String getDeviceId(Context context) {
        if (TextUtils.isEmpty(mDeviceId)) {
            mDeviceId = AppUtils.getSystemImei(context);
        }
        return mDeviceId;
    }
}
