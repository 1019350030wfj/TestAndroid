package com.jayden.testandroid.api.concurent;

import android.util.Log;

/**
 * synchronized 同步保证了原子性（也就是操作能够一次性执行完）
 * Created by Jayden on 2017/4/13.
 */
public class Person {
    private int foodNum = 0;
    private Object mObject = new Object();

    private final int MAX_NUM = 5;

    public void produce(String name) throws InterruptedException {
        synchronized (mObject) {
            while (foodNum == MAX_NUM) {
                Log.e("jayden", name + "box is full,size = " + foodNum);
                mObject.wait();//wait一下说明synchronized锁已经打开，其它线程可以进来；还有说明本线程只能等待，直到被唤醒
            }
            foodNum++;
            Log.e("jayden", name + " produce success foodNum" + foodNum);
            mObject.notifyAll();
        }
    }

    public void consume(String name) throws InterruptedException {
        synchronized (mObject) {
            while (foodNum == 0) {
                Log.e("jayden", name + "box is empty,size = " + foodNum);
                mObject.wait();
            }
            foodNum--;
            Log.e("jayden", name + " consume success foodNum = " + foodNum);
            mObject.notifyAll();
        }
    }
}
