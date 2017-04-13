package com.jayden.testandroid.api.concurent;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2017/4/13.
 */

public class ConsumerAndProducer extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);

        Person person = new Person();
        new Thread(new Consumer(person,"消费者一" )).start();
        new Thread(new Consumer(person,"消费者二" )).start();
        new Thread(new Consumer(person,"消费者三" )).start();

        new Thread(new Producer(person,"生产者一" )).start();
        new Thread(new Producer(person,"生产者二" )).start();
        new Thread(new Producer(person,"生产者三" )).start();
    }
}
