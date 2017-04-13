package com.jayden.testandroid.api.concurent;

/**
 * Created by Jayden on 2017/4/13.
 */

public class Producer implements Runnable{
    private Person mPerson;
    private String mProducerName;

    public Producer(Person person, String producerName) {
        mPerson = person;
        mProducerName = producerName;
    }

    @Override
    public void run() {
        while (true){
            try {
                mPerson.produce(mProducerName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
