package com.jayden.officialandroiddemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("jaydenService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        int count = 0;
        while (count < 100){
            Log.e("jayden","count = " + count);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
        Log.e("jayden","count end ");
    }
}
