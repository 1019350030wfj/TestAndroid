package com.jayden.testandroid.customview.circlemenu;

import android.app.Activity;
import android.os.Bundle;

import com.jayden.testandroid.R;

public class ScratchMainActivity extends Activity {

    private ScratchWheel scratchWheel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch);

        scratchWheel = (ScratchWheel) findViewById(R.id.scratchWheel);
        scratchWheel.setRotateState(false);
    }
}
