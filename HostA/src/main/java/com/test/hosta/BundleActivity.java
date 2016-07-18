package com.test.hosta;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * 这边有个问题，就是只能获取一个apk中的资源
 * 不能自由切换使用任一个apk的资源，求解
 *
 * Created by Jayden on 2016/6/17.
 * Email : 1570713698@qq.com
 */
public class BundleActivity extends AppCompatActivity {

    private Resources mBundleResources;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("jayden","onCreate");
        super.onCreate(savedInstanceState);
        int bundleLayoutId = 0x7f04001a;
        View bundleView = LayoutInflater.from(this).inflate(bundleLayoutId,null);
        setContentView(bundleView);
        TextView textView = (TextView) findViewById(0x7f0c0050);


        //这边获取的是插件中的资源， 而不是宿主apk的资源
        textView.setText(getResources().getString(R.string.app_name) + "Bundle");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.d("jayden","attachBaseContext");
        replaceContextResource(newBase);
        super.attachBaseContext(newBase);
    }

    /**
     * 将当前的上下文设置给插件，作为插件资源的上下文
     * @param context
     */
    private void replaceContextResource(Context context) {
        try {
            Field field = context.getClass().getDeclaredField("mResources");
            field.setAccessible(true);
            if (mBundleResources == null) {
                mBundleResources = BundlerResourceLoader.bundleResMap.get("bundle_apk");
            }
            field.set(context,mBundleResources);
        } catch (NoSuchFieldException e) {
            Log.d("jayden","getDeclaredField(\"mResources\") fail");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.d("jayden","apply new context to Resources fail");
            e.printStackTrace();
        }
    }
}
