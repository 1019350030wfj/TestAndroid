package com.jayden.testandroid.customview.rotate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/8/10.
 */

public class TestRotateViewActivity extends AppCompatActivity {

    RotateView rotateView;
    String TAG="jadyen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_rotateview);

        int height,width;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;
        Log.i(TAG, "height:"+height);
        Log.i(TAG, "width:"+width);

        rotateView = (RotateView) findViewById(R.id.rotateView);
    }
}
