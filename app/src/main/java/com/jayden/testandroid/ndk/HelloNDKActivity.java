package com.jayden.testandroid.ndk;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/11/10.
 *
 * 1、生成.h文件：javah -d jni -classpath I:\adt\sdk\platforms\android-24\android.jar;..\..\build\intermedi
 ates\classes\debug com.jayden.testandroid.ndk.HelloNDKActivity

 */

public class HelloNDKActivity extends Activity {

    private TextView mTxtNDk;

    static {
        System.loadLibrary("hello");
    }

    public native String getStringFromNative();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndk_hello);
        mTxtNDk = (TextView) findViewById(R.id.txt_ndk_hello);
    }
}
