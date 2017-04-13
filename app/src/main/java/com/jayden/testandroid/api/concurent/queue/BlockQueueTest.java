package com.jayden.testandroid.api.concurent.queue;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jayden.testandroid.R;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Jayden on 2017/4/13.
 */

public class BlockQueueTest extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(2);//不设置的话，LinkedBlockingQueue默认大小为Integer.MAX_VALUE

        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);

        for (int i = 0; i < 5; i++) {
            new Thread(producer,"Producer +" +(i+1)).start();
            new Thread(producer,"consumer +" +(i+1)).start();
        }
    }
}
