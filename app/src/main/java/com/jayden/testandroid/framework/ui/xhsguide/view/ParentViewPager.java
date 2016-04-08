package com.jayden.testandroid.framework.ui.xhsguide.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jayden.testandroid.framework.ui.xhsguide.activity.fragment.outlayer.WelcomAnimFragment;
import com.jayden.testandroid.utils.ConvertUtils;

/**
 * 顶层ViewPager，包含2个Fragment childView
 * 处于第一个fragment，所有事件将被拦截在本层view，手动分发到指定子View
 * 处于第二个fragment，释放拦截
 * Created by Jayden on 2016/4/7.
 * Email : 1570713698@qq.com
 */
public class ParentViewPager extends ViewPager {

    public static boolean mLoginPageLock = false;

    /**
     * 跳过按钮相关
     */
    private Boolean mSkipFlag = true;
    private float mCx,mCy;
    private int[] mTvSkipLocation;
    private int margin, left, top, right, bottom;

    private WelcomAnimFragment mWelcomAnimFragment;

    public ParentViewPager(Context context) {
        super(context);
    }

    public ParentViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setWelcomAnimFragment(WelcomAnimFragment welcomAnimFragment) {
        this.mWelcomAnimFragment = welcomAnimFragment;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //登录页不拦截，
        if (mLoginPageLock) {
            requestDisallowInterceptTouchEvent(true);
            return super.onInterceptTouchEvent(ev);
        }
        //在欢迎也则拦截， 拦截然后统一分发给两个子viewpager去处理，可以达到同事滑动两个viewpager的效果
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.getCurrentItem() == 1 || mLoginPageLock) {
            return true;
        }
        if (mWelcomAnimFragment != null) {
            mCx = ev.getX();
            mCy = ev.getY();

            /**
             * 因为在欢迎页，事件被拦截了，所有跳过的点击按钮，需要在这边处理
             * 由于子View被拦截,这里通过计算跳过按钮的坐标手动处理跳过click事件
             */
            if (mTvSkipLocation == null) {
                mTvSkipLocation = new int[2];
                mWelcomAnimFragment.tv_skip.getLocationOnScreen(mTvSkipLocation);
            }
            if (left == 0) {
                margin = (int) ConvertUtils.dipToPx(getContext(), 10);
                left = mTvSkipLocation[0] - margin;
                top = mTvSkipLocation[1] - margin;
                right = mTvSkipLocation[0] + mWelcomAnimFragment.tv_skip.getWidth() + margin;
                bottom = mTvSkipLocation[1] + mWelcomAnimFragment.tv_skip.getHeight() + margin;
            }
            if (mCx - left > 0 && right - mCx > 0 && mCy - top > 0 && bottom - mCy > 0 && !mLoginPageLock) {
            } else {
                mSkipFlag = false;
            }
            if (ev.getAction() == MotionEvent.ACTION_UP) {
                if (mCx - left > 0 && right - mCx > 0 && mCy - top > 0 && bottom - mCy > 0 && !mLoginPageLock && mSkipFlag) {
                    if (mWelcomAnimFragment.mWelcomAnimFragmentInterface != null) {
                        mWelcomAnimFragment.mWelcomAnimFragmentInterface.onSkip();
                    }
                }
                mSkipFlag = true;
                mCx = 0;
                mCy = 0;
            }

            /**
             * 这边统一分发事件给两个子ViewPager，可以达到同时滚动两个Viewpager的效果
             * touch事件由顶层viewpager捕捉,手动分发到两个子viewpager
             */
            if (mWelcomAnimFragment.imageViewPager != null && !mWelcomAnimFragment.imageViewPager.mIsLockScoll) {
                mWelcomAnimFragment.imageViewPager.onTouchEvent(ev);
            }
            if (mWelcomAnimFragment.textViewPager != null) {
                mWelcomAnimFragment.textViewPager.onTouchEvent(ev);
            }

            //到达子Viewpager的最后一页， 则一切照旧。 也就是事件都给父的viewpager处理
            if (mWelcomAnimFragment.mIsMoveParent) {
                return super.onTouchEvent(ev);
            }
        }
        return true;
    }
}
