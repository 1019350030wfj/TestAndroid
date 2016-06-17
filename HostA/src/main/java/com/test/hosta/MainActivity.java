package com.test.hosta;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private TextView invokeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        AssetsMultiDexLoader.install(getApplicationContext());
        BundleClassLoaderManager.install(getApplicationContext());
        invokeTv = (TextView) findViewById(R.id.invoke_tv);
        invokeTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                loadApk();
                loadApkByCustomClassLoader();
            }
        });
//        loadClass();
    }

    /**
     * 通过BundleDexClassLoader，加载插件中类
     */
    private void loadApkByCustomClassLoader() {
        try {
            Class<?> clazz = BundleClassLoaderManager.loadClass(getApplicationContext(),"com.test.plugina.Utils");
            Constructor<?> constructor = clazz.getConstructor();
            Object bundleUtil = constructor.newInstance();
            Method printSum = clazz.getMethod("printSum",Context.class,int.class,int.class,String.class);
            printSum.setAccessible(true);
            int sum = (int) printSum.invoke(bundleUtil,getApplicationContext(),5,6,"total = ");
            Log.d("jayden","sum = " + sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //反射调用
    private void loadClass() {
        try {
            Class<?> clazz = Class.forName("com.test.plugina.FileUtils");

            //构造函数
            Constructor<?> constructor = clazz.getConstructor();
            //构建实例
            Object bundleUtils = constructor.newInstance();
            //获取方法
            Method printMethod = clazz.getMethod("print",Context.class,String.class);
            //设置方法可以访问
            printMethod.setAccessible(true);

            //调用方法
            printMethod.invoke(bundleUtils,getApplicationContext(),"Jayden invoke fileUtil");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //通过classload调用
    private void loadApk() {
        try {
            Class<?> clazz = getClassLoader().loadClass("com.test.plugina.Utils");
            Constructor<?> constructor = clazz.getConstructor();
            Object bundleUtil = constructor.newInstance();
            Method printSum = clazz.getMethod("printSum",Context.class,int.class,int.class,String.class);
            printSum.setAccessible(true);
            int sum = (int) printSum.invoke(bundleUtil,getApplicationContext(),5,6,"total = ");
            Log.d("jayden","sum = " + sum);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
