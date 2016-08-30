package com.jayden.testandroid.customview.floatview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/5/10.
 * Email : 1570713698@qq.com
 */
public class TestFloatServiceActivity extends AppCompatActivity {

    private Intent service;
    private TopFloatService bindService;

    private ServiceConnection conn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("jayden", "onServiceDisconnected()");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d("jayden", "onServiceConnected()");
            TopFloatService.MyBinder binder = (TopFloatService.MyBinder)service;
            bindService = binder.getService();
            bindService.show();
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_view);
//        startService(service);
        bind();
    }

    private void bind() {
        service = new Intent(this,TopFloatService.class);
        bindService(service,conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bindService != null) {
            bindService.show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bindService != null) {
            bindService.hide();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
