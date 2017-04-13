package com.jayden.testandroid.api.concurent.awaitandsignal;

import android.util.Log;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock 需要显示上锁（lock（））和开锁（unlock（））
 * Created by Jayden on 2017/4/13.
 */
public class Person {
    private int mFoodNum = 0;
    private ReentrantLock mReentrantLock = new ReentrantLock();
    private Condition mCondition = mReentrantLock.newCondition();

    private final int MAX_NUM = 5;

    public void produce(String name) {
        mReentrantLock.lock();//一个线程进来，禁止其它线程再进来，锁住
        try {
            while (mFoodNum == MAX_NUM) {
                Log.e("jayden", name + "box is full,size = " + mFoodNum);
                mCondition.await();
            }
            mFoodNum++;
            Log.e("jayden", name + " produce success foodNum" + mFoodNum);
            mCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.e("jayden", name + " produce unlock " + mFoodNum);
            mReentrantLock.unlock();//开锁
        }
    }

    public void consume(String name) {
        mReentrantLock.lock();
        try {
            while (mFoodNum == 0) {
                Log.e("jayden", name + " box is empty,size = " + mFoodNum);
                mCondition.await();
            }
            mFoodNum--;
            Log.e("jayden", name + " consume success foodNum" + mFoodNum);
            mCondition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Log.e("jayden", name + " consume unlock " + mFoodNum);
            mReentrantLock.unlock();//开锁
        }
    }
}
