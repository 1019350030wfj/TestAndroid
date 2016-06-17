package com.test.hosta;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class MainActivity2 extends AppCompatActivity {

    private TextView invokeTv;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BundleClassLoaderManager.install(getApplicationContext());
//        AssetsMultiDexLoader.install(getApplicationContext());
        invokeTv = (TextView) findViewById(R.id.invoke_tv);
        invokeTv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                loadApk();
            }
        });
//        loadClass();

        //加载插件中的资源
        loadPluginRes();
    }

    /**
     * 我们使用Resources对象，获取资源时，传递的ID必须是离线apk中R文件对应的资源的ID。
     * 如果使用getIdentifier方法:
     * 第一个参数是资源名称，第二个参数是资源类型，第三个参数是离线apk的包名，切记第三个参数。
     */
    private void loadPluginRes() {
        imageView = (ImageView)findViewById(R.id.image_view_iv);

        Resources resources = BundlerResourceLoader.getBundleResources(getApplicationContext());
        if (resources != null) {
            String str = resources.getString(resources.getIdentifier("test_str","string","com.test.plugina"));
            Log.d("jayden","getIdentifier str " + str);

            String strById = resources.getString(0x7f060015);
            invokeTv.setText(strById);

            Drawable drawable = resources.getDrawable(0x7f030000);
            imageView.setImageDrawable(drawable);
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
