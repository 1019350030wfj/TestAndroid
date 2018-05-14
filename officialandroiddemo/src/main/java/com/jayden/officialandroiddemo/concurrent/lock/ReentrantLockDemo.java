package com.jayden.officialandroiddemo.concurrent.lock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jayden.officialandroiddemo.BaseTextviewActivity;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo extends BaseTextviewActivity {

    private ReentrantLock lock = new ReentrantLock();

    public void unTimed(){
        boolean captured = lock.tryLock();
        try {
            Log.d("jayden","tryLock(): " + captured);
        } finally {
            if (captured){
                lock.unlock();
                Log.d("jayden","unlock(): " + captured);
            }
        }
    }

    public void timed(){
        boolean captured = false;
        try{
            captured = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.d("jayden","tryLock(2, TimeUnit.SECONDS): " + captured);
            if (captured){
                lock.unlock();
                Log.d("jayden","unlock(): " + captured);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unTimed();// True -- 可以成功获得锁
        timed();// True -- 可以成功获得锁
        //新建一个i额线程获得锁并且不释放
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                Log.d("jayden","lock.lock(): " );
            }
        });
        thread.setDaemon(true);
        thread.start();
        try {
            Thread.sleep(1000);//保证新线程可以执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        unTimed();//false 马上中断放弃
        timed();//false 等两秒超时后中断放弃
    }
}
