package com.jayden.testandroid.framework.ui.xhsguide.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;

import com.jayden.testandroid.R;
import com.jayden.testandroid.framework.ui.xhsguide.activity.fragment.outlayer.LoginAnimFragment;
import com.jayden.testandroid.framework.ui.xhsguide.activity.fragment.outlayer.WelcomAnimFragment;
import com.jayden.testandroid.framework.ui.xhsguide.view.ParentViewPager;
import com.jayden.testandroid.utils.ConvertUtils;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * 小红书欢迎引导界面第二版
 * XhsWelcomeAnim
 * Created by Jayden on 2016/4/7.
 * Email : 1570713698@qq.com
 */
public class WelcomeActivity extends FragmentActivity{

    private final int FRAGMENT_WELCOMEANIM = 0;
    private final int FRAGMENT_LOGINANIM = 1;

    private ImageView iv_logo;
    private ParentViewPager vp_parent;

    private float mLogoY;
    private AnimatorSet mAnimatorSet;

    private WelcomAnimFragment mWelcomAnimFragment;
    private LoginAnimFragment mLoginAnimFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xhs_welcome);
        findView();
        setClick();
        init();
    }

    private void findView() {
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        vp_parent = (ParentViewPager) findViewById(R.id.vp_parent);
    }

    private void setClick() {
        vp_parent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case FRAGMENT_WELCOMEANIM:
                        break;
                    case FRAGMENT_LOGINANIM:
                        vp_parent.mLoginPageLock = true;
                        iv_logo.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if(mLogoY == 0){
                                    mLogoY = ViewHelper.getY(iv_logo);
                                }
                                playLogoInAnim();
                            }
                        },500);
                        vp_parent.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mLoginAnimFragment.playInAnim();
                            }
                        }, 300);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    private void init() {
        ParentFragmentStatePagerAdapter adapter = new ParentFragmentStatePagerAdapter(getSupportFragmentManager());
        vp_parent.setAdapter(adapter);
    }

    private void playLogoInAnim(){
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(iv_logo, "scaleX", 1.0f, 0.5f);
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(iv_logo, "scaleY", 1.0f, 0.5f);
        ObjectAnimator anim3 = ObjectAnimator.ofFloat(iv_logo, "y", mLogoY, ConvertUtils.dipToPx(WelcomeActivity.this, 15));

        if(mAnimatorSet != null && mAnimatorSet.isRunning()){
            mAnimatorSet.cancel();
            mAnimatorSet = null;
        }
        mAnimatorSet = new AnimatorSet();
        mAnimatorSet.play(anim1).with(anim2);
        mAnimatorSet.play(anim2).with(anim3);
        mAnimatorSet.setDuration(1000);
        mAnimatorSet.start();
    }

    public class ParentFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        public ParentFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FRAGMENT_WELCOMEANIM:
                    mWelcomAnimFragment = new WelcomAnimFragment();
                    vp_parent.setWelcomAnimFragment(mWelcomAnimFragment);
                    mWelcomAnimFragment.setWelcomAnimFragmentInterface(new WelcomAnimFragment.WelcomAnimFragmentInterface() {
                        @Override
                        public void onSkip() {
                            vp_parent.setCurrentItem(1);
                        }
                    });
                    return mWelcomAnimFragment;
                case FRAGMENT_LOGINANIM:
                    mLoginAnimFragment = new LoginAnimFragment();
                    return mLoginAnimFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
