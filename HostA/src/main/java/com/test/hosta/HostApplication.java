package com.test.hosta;

import android.app.Application;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import com.test.hosta.resource.BundlerResourceLoader2;

/**
 * Created by Jayden on 2016/6/17.
 * Email : 1570713698@qq.com
 */
public class HostApplication extends Application {

    private Resources mAppResources = null;
    private Resources mOldResources = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mOldResources = super.getResources();

        //加载assets中的apk
        AssetsMultiDexLoader.install(this);

        Log.d("jayden","Application onCreate");
        installResources();
    }

    private void installResources() {
        if (mAppResources == null) {
            //加载assets中的资源对象
            mAppResources = BundlerResourceLoader2.getAppResource(this);
        }
    }

    @Override
    public Resources getResources() {
        if (mAppResources == null) {
            return mOldResources;
        }
        return this.mAppResources;
    }

    @Override
    public AssetManager getAssets() {
        if (mAppResources == null) {
            return super.getAssets();
        }
        return this.mAppResources.getAssets();
    }
}
