package com.test.hosta;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/6/16.
 * Email : 1570713698@qq.com
 */
public class BundleClassLoaderManager {

    public static List<BundleDexClassLoader> bundleDexClassLoaderList = new ArrayList<>();

    /**
     * 加载assets的apk
     * @param context
     */
    public static void install(Context context) {
        AssetsManager.copyAllAssetsApk(context);

        //打开从assets文件转存apk到目标目录的文件夹
        File dexDir = context.getDir(AssetsManager.APK_DIR,Context.MODE_PRIVATE);

        //获取该文件夹中的所有apk文件
        File[] apkFiles = dexDir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String filename) {
                return filename.endsWith(AssetsManager.FILE_FILTER);
            }
        });

        //每个apk创建对应的DexClassLoader
        for (File file : apkFiles) {
            Log.d("jayden","debug:load file:" + file.getName());
            BundleDexClassLoader bundleDexClassLoader = new BundleDexClassLoader(
                    file.getAbsolutePath(),dexDir.getAbsolutePath(),null,context.getClassLoader());
            bundleDexClassLoaderList.add(bundleDexClassLoader);
        }
    }

    /**
     * 查找类
      * @param context
     *  @param className
     * @return
     */
    public static Class<?> loadClass(Context context,String className) {
        //首先从父的ClassLoader中查找
        try {
            Class<?> clazz = context.getClassLoader().loadClass(className);
            if (clazz != null) {
                System.out.println("Debug Load Class from main ClassLoader");
                return clazz;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //父的ClassLoader没有，才从我们自己定义的BundleDexClassLoader
        for (BundleDexClassLoader bundleDexClassLoader : bundleDexClassLoaderList) {
            try {
                Class<?> clazz = bundleDexClassLoader.loadClass(className);
                if (clazz != null) {
                    System.out.println("Debug Load Class from Bundle ClassLoader");
                    return clazz;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        throw  new ClassCastException(className + "not found Exception");
    }
}
