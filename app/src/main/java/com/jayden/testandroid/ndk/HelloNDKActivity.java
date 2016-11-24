package com.jayden.testandroid.ndk;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/11/10.
 * 1、生成.h文件：javah -jni 路径
 * 1、生成.h文件：javah -d jni -classpath I:\adt\sdk\platforms\android-24\android.jar;..\..\build\intermedi
 ates\classes\debug com.jayden.testandroid.ndk.HelloNDKActivity
 LOCAL_PATH := &(call my-dir)//指定编译路径
 include $(CLEAR_VARS) //每个新模块的开始处，清理所有的LOCAL_XXX
 LOCAL_MODULE    := hello //定义模块名称
 LOCAL_SRC_FILES := com_jayden_testandroid_ndk_HelloNDKActivity.c //源代码文件
 include $(BUILD_SHARED_LIBRARY)//说明编译的是共享库及动态链接库


 遇到问题：
 1、不能链接通过，（contains C++的error） 解决： jni.srcDirs = []
 2、UnsatisLink 解决： jniLibs.srcDirs = ['src/main/libs']， 找不到so文件
 3、javah -jni 类路径  这条命令需要在git bash也就是linux环境中生成，不能在cmd中执行
 */

public class HelloNDKActivity extends Activity {

    private TextView mTxtNDk;

    static {
        System.loadLibrary("hello");
    }

    public native String getStringFromNative();

    public native void writeDataToFile(String path);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk_hello);
        mTxtNDk = (TextView) findViewById(R.id.txt_ndk_hello);
        mTxtNDk.setText(getStringFromNative());
        writeDataToFile(getExternalCacheDir().getAbsolutePath() + "ndk.txt");
    }
}
