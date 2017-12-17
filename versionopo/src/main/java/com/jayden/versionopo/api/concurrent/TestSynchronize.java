package com.jayden.versionopo.api.concurrent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jayden on 2017/11/16.
 */

public class TestSynchronize extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 通信：与synchronized配套使用的通信方法通常有wait()，notify（）
         * wait()方法会立即释放当前锁，并进入等待状态，等到到相应的notify并重新获得锁过后才能继续执行；
         * notify()不会立刻释放锁，必须要等到notify()所在线程执行完synchronized块中的所有代码才会释放。
         *
         * 1、内置锁在进入同步块时，采取的是无限等待的策略，一旦开始等待，就既不能中断也不能取消，容易产生饥饿与死锁的问题
         * 2、在线程调用notify方法时，会随机选择相应对象的等待队列的一个线程将其唤醒，而不是按照FIFO的方式，如果有强烈的公平性要求，比如FIFO就无法满足
         *
         * ReentrantLock 是显示锁，需要显示进行lock以及unlock操作
         *
         */

        List list = new LinkedList();
        Thread read = new Thread(new ReadList(list));
        Thread write = new Thread(new WriteList(list));
        read.start();
        write.start();
    }

    class ReadList implements Runnable{

        private List list;

        public ReadList(List list){
            this.list = list;
        }

        @Override
        public void run() {
            Log.e("jayden","ReadList begin at : " + System.currentTimeMillis());
            synchronized (list){
                try {
                    Thread.sleep(1000);
                    Log.e("jayden","list.wait() begin at : " + System.currentTimeMillis());
                    list.wait();
                    Log.e("jayden","list.wait() end at : " + System.currentTimeMillis());

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.e("jayden","ReadList end at : " + System.currentTimeMillis());
        }
    }

    class WriteList implements Runnable{

        private List list;

        public WriteList(List list){
            this.list = list;
        }

        @Override
        public void run() {
            Log.e("jayden","WriteList begin at : " + System.currentTimeMillis());
            synchronized (list){
                Log.e("jayden","get lock at : " + System.currentTimeMillis());
                list.notify();
                Log.e("jayden","list.notify() at : " + System.currentTimeMillis());
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("jayden","get out of lock at : " + System.currentTimeMillis());
            }
            Log.e("jayden","WriteList end at : " + System.currentTimeMillis());
        }
    }
}
