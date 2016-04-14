package com.jayden.jaydenrich.utils;

import android.content.Context;
import android.text.TextUtils;

import com.jayden.jaydenrich.utils.image.ImageUtils;

import java.io.File;

/**
 * app缓存目录路径
 * 清除缓存
 *
 * Created by Jayden on 2016/4/14.
 * Email : 1570713698@qq.com
 */
public class FileUtils {
    
    private static final String CACHE_IMG_NAME = "images";
    private static final String CACHE_FILE_NAME = "files";
    private static final String CACHE_LOG_NAME = "logs";
    private static final String CACHE_CROP_NAME = "crop";
    
    public static String mCacheDir;
    public static String mCacheImgDir;
    public static String mCacheFileDir;
    public static String mCacheLogDir;
    public static String mCacheCropDir;
    
    public static void init(Context context) {
        getCacheDir(context);
        getCacheImgDir(context);
        getCacheFileDir(context);
        getCacheLogDir(context);
        getCacheCropDir(context);
    }

    /**
     * 创建缓存目录路径
     */
    public static String getCacheDir(Context context) {
        if (TextUtils.isEmpty(mCacheDir)) {
            mCacheDir = context.getExternalCacheDir().getParentFile().getPath() + "/jayden";
        }
        createDir(mCacheDir);
        checkDir(mCacheDir);
        return mCacheDir;
    }

    /**
     * 创建文件目录
     * @param dir
     */
    private static void createDir(String dir) {
        File file = new File(dir);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 判断是否是文件目录
     * @param dir
     */
    public static void checkDir(String dir) {
        File file = new File(dir);
        if (file.exists() && file.isDirectory()) {
            LogUtils.e(dir + " is Directory");
        } else {
            if (!file.exists()) {
                LogUtils.e(dir + "文件不存在");
            }
            if (!file.isDirectory()) {
                LogUtils.e(dir + "文件不是目录");
            }
        }
    }

    /**
     * 获取缓存图片目录路径
     */
    public static String getCacheImgDir(Context context) {
        if (TextUtils.isEmpty(mCacheImgDir)) {
            mCacheImgDir = mCacheDir + File.separator + CACHE_IMG_NAME;
        }
        createDir(mCacheImgDir);

        checkDir(mCacheImgDir);
        return mCacheImgDir;
    }

    /**
     * 获取缓存文件目录路径
     */
    public static String getCacheFileDir(Context context) {
        if (TextUtils.isEmpty(mCacheFileDir)) {
            if (TextUtils.isEmpty(mCacheDir)) {
                mCacheDir = getCacheDir(context);
            }
            mCacheFileDir = mCacheDir + File.separator + CACHE_FILE_NAME;
        }
        createDir(mCacheFileDir);

        checkDir(mCacheFileDir);
        return mCacheFileDir;
    }

    /**
     * 获取缓存LOG文件目录路径
     */
    public static String getCacheLogDir(Context context) {
        if (TextUtils.isEmpty(mCacheLogDir)) {
            if (TextUtils.isEmpty(mCacheDir)) {
                mCacheDir = getCacheDir(context);
            }
            mCacheLogDir = mCacheDir + File.separator + CACHE_LOG_NAME;
        }
        createDir(mCacheLogDir);
        checkDir(mCacheLogDir);
        return mCacheLogDir;
    }

    /**
     * 获取缓存Crop文件目录路径
     */
    public static String getCacheCropDir(Context context) {
        if (TextUtils.isEmpty(mCacheCropDir)) {
            if (TextUtils.isEmpty(mCacheDir)) {
                mCacheDir = getCacheDir(context);
            }
            mCacheCropDir = mCacheDir + File.separator + CACHE_CROP_NAME;
        }
        createDir(mCacheCropDir);
        checkDir(mCacheCropDir);
        return mCacheCropDir;
    }

    /**
     * 清空目录文件
     */
    public static boolean clearDir(String dirPath) {
        if (TextUtils.isEmpty(dirPath)) {
            return false;
        }
        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                } else if (f.isDirectory()) {
                    deleteFile(f.getAbsolutePath());
                }
            }
        }
        return true;
    }

    /**
     * 根据路径删除文件或文件夹
     * <ul>
     * <li>if path is null or empty, return true</li>
     * <li>if path not exist, return true</li>
     * <li>if path exist, delete recursion. return true</li>
     * <ul>
     *
     * @param path
     * @return
     */
    public static boolean deleteFile(String path) {
        if (TextUtils.isEmpty(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }

    /**
     * 清空缓存
     */
    public static void clearCache() {
        ImageUtils.clearDiskCache();
        clearDir(mCacheFileDir);
        clearDir(mCacheLogDir);
        clearDir(mCacheCropDir);
    }

}
