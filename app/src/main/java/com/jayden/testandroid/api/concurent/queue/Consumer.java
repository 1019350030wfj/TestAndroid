package com.jayden.testandroid.api.concurent.queue;

import android.util.Log;

import java.util.concurrent.BlockingQueue;

/**
 * Created by Jayden on 2017/4/13.
 */

public class Consumer implements Runnable {
    private BlockingQueue<String> mQueue;

    public Consumer(BlockingQueue<String> queue) {
        mQueue = queue;
    }

    @Override
    public void run() {
        try {
            String temp = mQueue.take();//如果队列为空，会阻塞当前线程
            Log.e("jayden","queue take temp = " + temp);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
