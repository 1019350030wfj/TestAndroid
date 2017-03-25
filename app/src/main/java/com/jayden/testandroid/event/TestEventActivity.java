package com.jayden.testandroid.event;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2017/2/24.
 */

public class TestEventActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("jayden", "TestEventActivity dispatchTouchEvent = " + ev.getAction());
        boolean dispatch = super.dispatchTouchEvent(ev);
        Log.e("jayden", "TestEventActivity dispatchTouchEvent = " + dispatch);
        return dispatch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("jayden", "TestEventActivity onTouchEvent = " + event.getAction());
        boolean onTouchEvent = super.onTouchEvent(event);
        Log.e("jayden", "TestEventActivity onTouchEvent = " + onTouchEvent);
        return onTouchEvent;
    }
}
