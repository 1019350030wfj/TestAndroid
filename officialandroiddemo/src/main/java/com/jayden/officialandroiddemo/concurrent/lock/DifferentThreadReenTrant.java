package com.jayden.officialandroiddemo.concurrent.lock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jayden.officialandroiddemo.BaseTextviewActivity;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 不同线程是不能重入锁
 * Created by Jayden on 2018/5/14.
 */
public class DifferentThreadReenTrant extends BaseTextviewActivity{

    private static Lock mLock = new ReentrantLock();

    private static class T1 extends Thread{
        @Override
        public void run() {
            super.run();
            mLock.lock();
            try {
                Log.e("jayden","T1 start and get lock");
                //do something
                Thread.sleep(10*1000);
                Log.e("jayden","T1 start doing someting");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mLock.unlock();
                Log.e("jayden","T1 end and release lock");
            }
        }
    }

    private static class T2 extends Thread{
        @Override
        public void run() {
            super.run();
            Log.e("jayden","T2 start and get lock");
            mLock.lock();
            try {
                Log.e("jayden","T2 beginning doing something");
            } finally {
                mLock.unlock();
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTextView.setText("Different Thread canot reentrant");

        new T1().start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new T2().start();
    }
}
