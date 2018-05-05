package com.jayden.officialandroiddemo.concurrent.lock;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

/**
 * 死锁例子https://github.com/francistao/LearningNotes
 */
public class DeadLock extends Activity {

    private static final String TAG = "jayden";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        textView.setText("Dead Lock Demo");
        setContentView(textView);

        testDeadLock();
    }

    private void testDeadLock() {
        //构造两个锁
        final DeadLockDemo obj1 = new DeadLockDemo("obj1");
        final DeadLockDemo obj2 = new DeadLockDemo("obj2");

        //构造两个线程
        Runnable runA = new Runnable() {
            @Override
            public void run() {
                obj1.checkOther(obj2);
            }
        };

        Thread threadA = new Thread(runA, "threadA");
        threadA.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException x) {
        }


        Runnable runB = new Runnable() {
            public void run() {
                obj2.checkOther(obj1);
            }
        };

        Thread threadB = new Thread(runB, "threadB");
        threadB.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException x) {
        }

        threadPrint("finished sleeping");

        threadPrint("about to interrupt() threadA");

        threadA.interrupt();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException x) {
        }

        threadPrint("about to interrupt() threadB");
        threadB.interrupt();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException x) {
        }

        threadPrint("did that break the deadlock?");
    }

    public static void threadPrint(String msg) {
        String threadName = Thread.currentThread().getName();
        System.out.println(threadName + ": " + msg);
    }

    private class DeadLockDemo {
        private String objID;

        public DeadLockDemo(String id) {
            this.objID = id;
        }

        public synchronized void checkOther(DeadLockDemo other) {
            printLog("entering checkOther");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                printLog("in checkOther() - about to invoke 'other.action()'");
                //调用other对象的action方法，由于该方法是同步方法，因此会试图获取other对象的对象锁
                other.action();
                printLog("leaving checkOther()");
            }
        }

        public synchronized void action() {
            printLog("entering action()");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            printLog("leaving action()");
        }

        private void printLog(String log) {
            String threadName = Thread.currentThread().getName();
            Log.e(TAG, threadName + " : objID=" + objID + " - " + log);
        }
    }
}
