package com.jayden.testandroid.api.concurent.queue;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Jayden on 2017/4/13.
 */

public class Producer implements Runnable {
    private BlockingQueue<String> mQueue;

    public Producer(BlockingQueue<String> deque) {
        mQueue = deque;
    }

    @Override
    public void run() {
        try {
            String temp = "A Product,生产线程："+Thread.currentThread().getName();
            Log.e("jayden","I have made a product:"
                    + Thread.currentThread().getName());
            mQueue.put(temp);//如果队列是满的话，会阻塞当前线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
