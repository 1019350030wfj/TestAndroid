package com.jayden.testandroid.api.concurent.awaitandsignal;

import android.util.Log;

/**
 * Created by Jayden on 2017/4/13.
 */

public class Consumer implements Runnable {

    private String name;
    private Person mPerson;

    public Consumer( Person person,String name) {
        this.name = name;
        mPerson = person;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Log.e("jayden",name + " Consumer run i = " + i);
            mPerson.consume(name);
            Log.e("jayden",name + " Consumer run i = " + i);
        }
    }
}
