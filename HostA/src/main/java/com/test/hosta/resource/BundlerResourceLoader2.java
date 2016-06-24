package com.test.hosta.resource;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.test.hosta.AssetsManager;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * 动态加载资源的管理器
 * <p/>
 * Created by Jayden on 2016/6/17.
 * Email : 1570713698@qq.com
 */
public class BundlerResourceLoader2 {

    public static Resources getAppResource(Context context) {
        System.out.println("debug: getAppResources ...");
        AssetsManager.copyAllAssetsApk(context);
        //获取dex文件列表
        File dexDir = context.getDir(AssetsManager.APK_DIR, Context.MODE_PRIVATE);
        File[] szFiles = dexDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(AssetsManager.FILE_FILTER);
            }
        });
        if (szFiles == null || szFiles.length == 0) {
            return context.getResources();
        }
        System.out.println("debug: dex File size = " + szFiles.length);
        List<String> apkPaths = new ArrayList<>();
        for (File file : szFiles) {
            System.out.println("debug: dex File name = " + file.getName());
            System.out.println("debug: dex File path = " + file.getAbsolutePath());
            apkPaths.add(file.getAbsolutePath());
        }

        AssetManager assetsManager = modifyAssetManager(context.getAssets(), apkPaths);
        return new AppResource(assetsManager, context.getResources().getDisplayMetrics(), context.getResources().getConfiguration());
    }

    /**
     * 修改AssetManager
     *
     * @param assets
     * @param apkPaths
     * @return
     */
    private static AssetManager modifyAssetManager(AssetManager assets, List<String> apkPaths) {
        if (apkPaths == null || apkPaths.size() == 0) {
            return null;
        }
        try {
            for (String apkPath : apkPaths) {
                try {
                    AssetManager.class.getDeclaredMethod("addAssetPath",
                            String.class).invoke(assets, apkPath);
                } catch (Exception e) {
                    System.out.println("debug modifyAssetManager Exception");
                    e.printStackTrace();
                }
            }
            return assets;
        } catch (Throwable th) {
            System.out.println("debug modifyAssetManager Throwable");
            th.printStackTrace();
        }
        return null;
    }

    /**
     * 创建AssetManager
     *
     * @param apkPaths
     * @return
     */
    public static AssetManager createAssetManager(List<String> apkPaths) {
        if (apkPaths == null || apkPaths.size() == 0) {
            return null;
        }
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            return modifyAssetManager(assetManager, apkPaths);
        } catch (Throwable th) {
            System.out.println("debug:createAssetManager :" + th.getMessage());
            th.printStackTrace();
        }
        return null;
    }
}
