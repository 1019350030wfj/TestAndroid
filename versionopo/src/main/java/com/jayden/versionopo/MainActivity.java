package com.jayden.versionopo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_third);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                Log.e("jayden","down");
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                Log.e("jayden","move");
                break;
            }
            case MotionEvent.ACTION_UP:{
                Log.e("jayden","up");
                break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
