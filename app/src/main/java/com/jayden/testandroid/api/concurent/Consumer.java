package com.jayden.testandroid.api.concurent;

/**
 * Created by Jayden on 2017/4/13.
 */

public class Consumer implements Runnable {

    private Person mPerson;
    private String mConsumerName;

    public Consumer(Person person, String consumerName) {
        mPerson = person;
        mConsumerName = consumerName;
    }

    @Override
    public void run() {
        while (true){
            try {
                mPerson.consume(mConsumerName);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
