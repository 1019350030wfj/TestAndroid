package com.jayden.testandroid.lollipop.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * 动画效果
 * Created by Jayden on 2016/5/12.
 * Email : 1570713698@qq.com
 */
public class GuiUtils {

    public interface OnRevealAnimationListener {
        void onRevealHide();

        void onRevealShow();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealShow(final Context context, final View view,
                                         int startRadius, final int color,
                                         final OnRevealAnimationListener listener) {
        //view的中心坐标
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        float finalRadius = (float) Math.hypot(view.getWidth(), view.getHeight());
        Log.d("jayden","startRadius = " + startRadius);
        Log.d("jayden","finalRadius = " + finalRadius);

        /*
            设置圆形显示动画
            view 要被缩放的视图
            startRadius 开始的半径
            finalRadius 结束的半径
            cx  view的中心 x 坐标
            cy view的中心 y 坐标
         */
        Animator anim = ViewAnimationUtils.createCircularReveal(view,cx,cy,startRadius,finalRadius);
        anim.setDuration(300);
        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.VISIBLE);
                listener.onRevealShow();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setBackgroundColor(ContextCompat.getColor(context,color));
            }
        });
        anim.start();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void animateRevealHide(final Context context, final View view, final int finalRadius,
                                         final int color, final OnRevealAnimationListener listener) {
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getBottom() + view.getTop()) / 2 ;

        int initRadius = view.getWidth();
        Log.d("jayden","initRadius = " + initRadius);
        Log.d("jayden","finalRadius = " + finalRadius);

        Animator animator = ViewAnimationUtils.createCircularReveal(view,cx,cy,initRadius,finalRadius);
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                view.setBackgroundColor(ContextCompat.getColor(context,color));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                listener.onRevealHide();
                view.setVisibility(View.INVISIBLE);
            }
        });
        animator.start();
    }
}
