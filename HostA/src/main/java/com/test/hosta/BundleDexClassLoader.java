package com.test.hosta;

import dalvik.system.DexClassLoader;

/**
 * Bundle 类加载器
 * Created by Jayden on 2016/6/16.
 * Email : 1570713698@qq.com
 */
public class BundleDexClassLoader extends DexClassLoader {

    public BundleDexClassLoader(String dexPath, String optimizedDirectory, String libraryPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, libraryPath, parent);
    }
}
