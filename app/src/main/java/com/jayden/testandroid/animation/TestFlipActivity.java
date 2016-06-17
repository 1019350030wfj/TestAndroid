package com.jayden.testandroid.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.jayden.testandroid.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jayden on 2016/5/13.
 * Email : 1570713698@qq.com
 */
public class TestFlipActivity extends AppCompatActivity{

    @Bind(R.id.main_fl_container)
    FrameLayout mFlContainer;
    @Bind(R.id.main_fl_card_back)
    FrameLayout mFlCardBack;
    @Bind(R.id.main_fl_card_front)
    FrameLayout mFlCardFront;

    private boolean mIsShowBack;//标记是否显示反面
    private AnimatorSet mRightOutSet;//右出动画
    private AnimatorSet mLeftInSet; //左入动画

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip);
        ButterKnife.bind(this);

        setAnimators(); //设置动画
        setCameraDistance();//设置镜头距离
    }

    private void setCameraDistance() {
        float distance = 16000f;
        float scale = getResources().getDisplayMetrics().density * distance;
        mFlCardBack.setCameraDistance(scale);
        mFlCardFront.setCameraDistance(scale);
    }

    private void setAnimators() {
        mRightOutSet = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.anim_out);
        mLeftInSet = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.animator.anim_in);

        // 设置点击事件
        mRightOutSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                mFlContainer.setClickable(false);
            }
        });
        mLeftInSet.addListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mFlContainer.setClickable(true);
            }
        });
    }

    public void flipCard(View view) {
        if (!mIsShowBack) {//正面朝上
            mRightOutSet.setTarget(mFlCardFront);
            mLeftInSet.setTarget(mFlCardBack);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = true;
        } else {
            //背面朝上
            mRightOutSet.setTarget(mFlCardBack);
            mLeftInSet.setTarget(mFlCardFront);
            mRightOutSet.start();
            mLeftInSet.start();
            mIsShowBack = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
