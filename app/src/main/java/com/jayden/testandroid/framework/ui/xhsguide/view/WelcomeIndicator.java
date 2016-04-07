package com.jayden.testandroid.framework.ui.xhsguide.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jayden.testandroid.R;
import com.jayden.testandroid.utils.ConvertUtils;

import java.util.ArrayList;

/**
 * Created by Jayden on 2016/4/7.
 * Email : 1570713698@qq.com
 */
public class WelcomeIndicator extends LinearLayout {

    private Context mContext;
    private ArrayList<ImageView> mImageViews;

    int heightSelect;
    Bitmap bmpSelect;
    Bitmap bmpNormal;

    AnimatorSet mOutAnimatorSet;
    AnimatorSet mInAnimatorSet;

    public WelcomeIndicator(Context context) {
        this(context,null);
    }

    public WelcomeIndicator(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WelcomeIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        this.setOrientation(HORIZONTAL);

        heightSelect = (int) ConvertUtils.dipToPx(context,24);
        bmpNormal = BitmapFactory.decodeResource(getResources(), R.drawable.welcome_indicator_point_nomal);
        bmpSelect = BitmapFactory.decodeResource(getResources(),R.drawable.welcome_indicator_point_select);

    }

    public void init(int count) {
        mImageViews = new ArrayList<>();
        for (int i=0; i<count; i++) {
            RelativeLayout rl = new RelativeLayout(mContext);
            LayoutParams params = new LayoutParams(heightSelect,heightSelect);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
            ImageView imageView = new ImageView(mContext);

            if (i == 0) {
                imageView.setImageBitmap(bmpSelect);
                rl.addView(imageView,layoutParams);
            } else {
                imageView.setImageBitmap(bmpNormal);
                rl.addView(imageView, layoutParams);
            }

            this.addView(rl,params);
            mImageViews.add(imageView);
        }
    }

    public void play(int startPosition, int nextPosition) {
        if (startPosition < 0 || nextPosition < 0 || startPosition == nextPosition) {
            return;
        }

        final ImageView imageViewStart = mImageViews.get(startPosition);
        final ImageView imageViewNext = mImageViews.get(nextPosition);

        //对对象进行缩放
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(imageViewStart,"scaleX",1.0f,0.25f);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(imageViewStart,"scaleY",1.0f,0.25f);

        if (mOutAnimatorSet != null && mOutAnimatorSet.isRunning()) {
            mOutAnimatorSet.cancel();
            mOutAnimatorSet = null;
        }
        mOutAnimatorSet = new AnimatorSet();
        mOutAnimatorSet.play(animator1).with(animator2);
        mOutAnimatorSet.setDuration(100);

        ObjectAnimator animatorIn1 = ObjectAnimator.ofFloat(imageViewNext,"scaleX",0.25f,1f);
        ObjectAnimator animatorIn2 = ObjectAnimator.ofFloat(imageViewNext,"scaleY",0.25f,1f);
        if (mInAnimatorSet != null && mInAnimatorSet.isRunning()) {
            mInAnimatorSet.cancel();
            mInAnimatorSet = null;
        }
        mInAnimatorSet = new AnimatorSet();
        mInAnimatorSet.play(animatorIn1).with(animatorIn2);
        mInAnimatorSet.setDuration(100);

        animator1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束，指示器原先点变为正常，指示器选中
                imageViewStart.setImageBitmap(bmpNormal);
                ObjectAnimator animatorFill1 = ObjectAnimator.ofFloat(imageViewStart,"scaleY",1.0f);
                ObjectAnimator animatorFill2 = ObjectAnimator.ofFloat(imageViewStart,"scaleX",1.0f);
                AnimatorSet mFillAnimatorSet = new AnimatorSet();
                mFillAnimatorSet.play(animatorFill1).with(animatorFill2);
                mFillAnimatorSet.setDuration(100);
                mFillAnimatorSet.start();
                imageViewNext.setImageBitmap(bmpSelect);
                mInAnimatorSet.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mOutAnimatorSet.start();
    }
}
