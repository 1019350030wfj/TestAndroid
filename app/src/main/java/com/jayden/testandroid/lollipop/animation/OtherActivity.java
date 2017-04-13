package com.jayden.testandroid.lollipop.animation;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jayden.testandroid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jayden on 2016/5/12.
 * Email : 1570713698@qq.com
 */
public class OtherActivity extends AppCompatActivity {

    @BindView(R.id.other_fab_circle)
    FloatingActionButton mFabCircle;
    @BindView(R.id.other_tv_container)
    TextView mTVContainer;
    @BindView(R.id.other_iv_close)
    ImageView mIvClose;
    @BindView(R.id.other_rl_container)
    RelativeLayout mRlContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setupEnterAnimation();//入场动画
            setupExitAnimation();//退出时候的动画
        } else {
            initViews();
        }
    }

    //退出动画
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupExitAnimation() {
        Fade fade = new Fade();
        getWindow().setReturnTransition(fade);
        fade.setDuration(300);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupEnterAnimation() {
        Transition transition = TransitionInflater.from(this)
                .inflateTransition(R.transition.arc_motion);
        getWindow().setSharedElementEnterTransition(transition);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {

            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        });
    }

    //动画展示,整个页面爆炸展示
    private void animateRevealShow() {
        GuiUtils.animateRevealShow(this,
                mRlContainer,
                mFabCircle.getWidth() / 2, R.color.colorAccent,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {

                    }

                    @Override
                    public void onRevealShow() {
                        //动画结束，显示view
                        initViews();
                    }
                });
    }

    //初始化视图
    private void initViews() {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Animation animation = AnimationUtils.loadAnimation(OtherActivity.this,
                        android.R.anim.fade_in);
                animation.setDuration(300);
                mTVContainer.startAnimation(animation);
                mIvClose.startAnimation(animation);
                mTVContainer.setVisibility(View.VISIBLE);
                mIvClose.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * 点击退出按钮
     * @param view
     */
    public void backActivity(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //5.0以后使用退出动画
            onBackPressed();
        } else {
            //默认退出
            defaultBackPressed();
        }
    }

    private void defaultBackPressed() {
        super.onBackPressed();
    }

    //退出事件，整个页面凝聚
    @Override
    public void onBackPressed() {
        GuiUtils.animateRevealHide(this,
                mRlContainer, mFabCircle.getWidth() / 2, R.color.colorAccent,
                new GuiUtils.OnRevealAnimationListener() {
                    @Override
                    public void onRevealHide() {
                        defaultBackPressed();
                    }

                    @Override
                    public void onRevealShow() {

                    }
                });
    }
}
