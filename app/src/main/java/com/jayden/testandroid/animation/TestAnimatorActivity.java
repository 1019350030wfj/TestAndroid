package com.jayden.testandroid.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2017/2/27.
 */

public class TestAnimatorActivity extends Activity {

    private ImageView mImageView;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_animator);
        mImageView = (ImageView) findViewById(R.id.imageView);
        mTextView = (TextView) findViewById(R.id.text);
    }

    public void scale(View view){
        mTextView.setText("height = " + mImageView.getHeight() + " width = " + mImageView.getWidth());
        Animator animator = AnimatorInflater.loadAnimator(this,R.animator.scale);
        animator.setTarget(mImageView);
        animator.start();
        mTextView.postDelayed(new Runnable() {
            @Override
            public void run() {//缩放后，宽高还是没有变化
                mTextView.setText("height = " + mImageView.getHeight() + " width = " + mImageView.getWidth());
            }
        },5000);
    }
}
