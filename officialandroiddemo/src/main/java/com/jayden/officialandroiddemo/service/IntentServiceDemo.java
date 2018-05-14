package com.jayden.officialandroiddemo.service;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jayden.officialandroiddemo.BaseTextviewActivity;

public class IntentServiceDemo extends BaseTextviewActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this,MyIntentService.class);
        startService(intent);
    }
}
