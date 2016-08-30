package com.jayden.testandroid.customview.viewflipper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/7/29.
 */

public class TestViewFlipperActivity extends AppCompatActivity {

    private ViewFlipper viewFlipper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_viewflip);

        viewFlipper = (ViewFlipper) findViewById(R.id.flipper);
        Animation animIn = AnimationUtils.loadAnimation(this,R.anim.push_up_in);
        viewFlipper.setInAnimation(animIn);
        Animation animOut = AnimationUtils.loadAnimation(this,R.anim.push_up_out);
        viewFlipper.setOutAnimation(animOut);
        viewFlipper.startFlipping();
    }
}
