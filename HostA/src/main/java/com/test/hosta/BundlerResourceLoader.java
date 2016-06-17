package com.test.hosta;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态加载资源的管理器
 * Created by Jayden on 2016/6/16.
 * Email : 1570713698@qq.com
 */
public class BundlerResourceLoader {

    private static AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager assetsManager = AssetManager.class.newInstance();
            try {
                AssetManager.class.getDeclaredMethod("addAssetPath",String.class).invoke(assetsManager,apkPath);
            } catch (InvocationTargetException e) {
                System.out.println("ebug:createAssetManager : Exception");
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                System.out.println("ebug:createAssetManager : Exception");
                e.printStackTrace();
            }
            return assetsManager;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String,Resources> bundleResMap = new HashMap<String,Resources>();

    /**
     * 获取bundle中的资源
     * @param context
     * @return
     */
    public static Resources getBundleResources(Context context) {
        AssetsManager.copyAllAssetsApk(context);
        File file = context.getDir(AssetsManager.APK_DIR,Context.MODE_PRIVATE);
        String apkPath = file.getAbsolutePath() + "/PluginA-debug.apk";
        AssetManager assetManager = createAssetManager(apkPath);
        Resources resources = new Resources(assetManager,context.getResources().getDisplayMetrics(),context.getResources().getConfiguration());
        bundleResMap.put("bundle_apk",resources);
        return resources;
    }
}
