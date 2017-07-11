package com.jayden.jaydenffmpeg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("live_jni");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText(helloFromFFmpeg());
    }

    public void live(View view){
        Intent liveIntent = new Intent(this, LiveActivity.class);
        startActivity(liveIntent);
    }

    public void watch(View view){

    }

    public native String helloFromFFmpeg();
}
