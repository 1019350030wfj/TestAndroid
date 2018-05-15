package com.jayden.officialandroiddemo.concurrent.lock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jayden.officialandroiddemo.BaseTextviewActivity;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jayden on 2018/5/14.
 */
public class LockReentrantDemo extends BaseTextviewActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextView.setText("ReenTrantLock");
        method1();
    }

    private Lock mLock = new ReentrantLock();// default unfail

    private void method1(){
        try{
            mLock.lock();
            Log.e("jayden","method1 get lock");
            method2();
        } finally {
            mLock.unlock();
        }
    }

    private void method2() {
        try{
            mLock.lock();
            Log.e("jayden","method1 call method2, method2 get the lock");
        } finally {
            mLock.unlock();
        }
    }
}
