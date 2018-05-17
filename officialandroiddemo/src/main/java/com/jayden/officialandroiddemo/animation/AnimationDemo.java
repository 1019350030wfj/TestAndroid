package com.jayden.officialandroiddemo.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import com.jayden.officialandroiddemo.BaseTextviewActivity;

public class AnimationDemo extends BaseTextviewActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TranslateAnimation translateAnimation = new TranslateAnimation(0,100,0,100);
        translateAnimation.setDuration(2000);
        translateAnimation.setFillAfter(true);
        translateAnimation.setRepeatMode(Animation.REVERSE);//Reverse
        translateAnimation.setRepeatCount(3);//重复次数3次，总共就是四次
        mTextView.startAnimation(translateAnimation);
    }
}
