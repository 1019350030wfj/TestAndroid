package com.jayden.officialandroiddemo.concurrent.lock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jayden.officialandroiddemo.BaseTextviewActivity;

/**
 * Created by Jayden on 2018/5/14.
 */
public class SynchronizedReenTrainDemo extends BaseTextviewActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextView.setText("Synchronized ReenTrant");
        method1();
    }

    private void method1(){
        synchronized(SynchronizedReenTrainDemo.class){
            Log.e("jayden", "method1");
            method2();
        }
    }

    private void method2(){
        synchronized (SynchronizedReenTrainDemo.class){
            Log.e("jayden", "method1 call method2");
        }
    }
}
