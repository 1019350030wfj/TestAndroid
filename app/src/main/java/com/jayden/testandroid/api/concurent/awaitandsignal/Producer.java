package com.jayden.testandroid.api.concurent.awaitandsignal;

import android.util.Log;

/**
 * Created by Jayden on 2017/4/13.
 */

public class Producer implements Runnable {
    private Person mPerson;
    private String mName;

    public Producer(Person person, String name) {
        mPerson = person;
        mName = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Log.e("jayden", mName + " producer run i = " + i);
            mPerson.produce(mName);
            Log.e("jayden", mName + " producer run i = " + i);
        }
    }
}
