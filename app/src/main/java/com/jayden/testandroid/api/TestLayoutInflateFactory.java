package com.jayden.testandroid.api;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/5/27.
 * Email : 1570713698@qq.com
 */
public class TestLayoutInflateFactory extends AppCompatActivity {

    public static final String TAG = "jayden";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
                Log.d(TAG,name);
                int count = attrs.getAttributeCount();
                for (int i=0; i<count;i++) {
                    Log.d(TAG,"attr name = " + attrs.getAttributeName(i) + " value = " + attrs.getAttributeValue(i));
                }
                return null;
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_inflate);
    }
}
