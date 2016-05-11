package com.jayden.testandroid.customview.scrollview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;

import com.jayden.testandroid.R;

/**
 * Created by Jayden on 2016/5/6.
 * Email : 1570713698@qq.com
 */
public class ElasticScrollView extends ScrollView {

    //ScrollView的子view（ScrollView只能有一个子Veiw）
    private View mInnerView;
    private View elasticView;

    private int elasticId;
    private int originHeight;

    private static final int ELASTIC_DELAY = 200;
    /**
     * 回弹延迟
     */
    private int resetDelay = ELASTIC_DELAY;

    public ElasticScrollView(Context context) {
        this(context,null);
    }

    public ElasticScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ElasticScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readStyleAttributes(context,attrs,0);
    }

    private void readStyleAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ElasticScrollView,0,defStyleAttr);
        elasticId = a.getResourceId(R.styleable.ElasticScrollView_elasticId,0);
        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() == 0) {
            return;
        }

        mInnerView = getChildAt(0);
        if (elasticId != 0) {
            View viewById = findViewById(elasticId);
            setElasticView(viewById);
        }
    }

    /**
     * 设置弹性view
     * @param view
     */
    private void setElasticView(View view) {
        refreshOriginHeight(view);
        elasticView = view;
    }

    //恢复弹性view原来的高度
    private void refreshOriginHeight(View view) {
        if (elasticView != null) {
            ViewGroup.LayoutParams layoutParams = elasticView.getLayoutParams();
            layoutParams.height = originHeight;
            elasticView.setLayoutParams(layoutParams);
        }

        if (view != null) {
            originHeight = view.getLayoutParams().height;
        }
    }

    private static final int SHAKE_THRESHOLD_VALUE = 3;
    private float startY;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                startY = event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //判断是否拦截
                float currentY = event.getY();
                float scrollY = currentY - startY;
                return Math.abs(scrollY) > SHAKE_THRESHOLD_VALUE;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mInnerView != null) {
            //事件拦截了，自己处理
            computeMove(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void computeMove(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP: {
                doReset();
                break;
            }

            case MotionEvent.ACTION_MOVE:{
                doMove(ev);
                break;
            }
        }
    }

    private void doMove(MotionEvent ev) {
        int deltaY = computeDeltaY(ev);

        if (!isNeetMove(deltaY)){
            return;
        }

        //只调用一次
        refreshNormalRect();

        if (elasticView != null) {
            //只滑动一个view
            moveElasticView(deltaY);
        } else {
            //整体滑动
            moveInnerView(deltaY);
        }
    }

    /**
     * 使ScrollView在最两端，也可以滑动
     * @param deltaY
     */
    private void moveInnerView(int deltaY) {
        mInnerView.layout(mInnerView.getLeft(),mInnerView.getTop() - deltaY,mInnerView.getRight(),mInnerView.getBottom() - deltaY);
    }

    /**
     * 通过布局参数来改变弹性view的高度,达到view的弹性效果
     * @param deltaY
     */
    private void moveElasticView(int deltaY) {
        ViewGroup.LayoutParams layoutParams = elasticView.getLayoutParams();
        Log.d("jayden","origin height = " + layoutParams.height);
        layoutParams.height = Math.max(0,layoutParams.height - deltaY);
        Log.d("jayden","current height = " + layoutParams.height);
        elasticView.setLayoutParams(layoutParams);
    }

    private Rect normalRect = new Rect();

    private void refreshNormalRect() {
        if (!normalRect.isEmpty()) {
            //保存正常的布局位置，也就是原先的位置
            return;
        }

        normalRect.set(mInnerView.getLeft(),mInnerView.getTop(),mInnerView.getRight(),mInnerView.getBottom());
    }

    /**
     * 判断是否需要移动
     * @param deltaY
     * @return
     */
    private boolean isNeetMove(int deltaY) {
        return deltaY == 0 ? false : (deltaY < 0 ? isNeedMoveTop() : isNeedMoveBottom());
    }

    /**
     * ScrollView内容向下移动
     * 内容是否滑动到最底部
     * ScrollView的内容如果超过全屏， 则ScrollView.getHeight（）不是内容的高度，而是屏幕可以看到的高度
     * @return
     */
    private boolean isNeedMoveBottom() {
        int offset = mInnerView.getMeasuredHeight() - getHeight();
        offset = offset < 0 ? 0 : offset;
        int scrollY = getScrollY();
        return (scrollY == offset);
    }


    private static final int TOP_Y = 0;
    /**
     * ScrollView内容向上移动
     * 当内容已经置顶了就可以滑动，形成阻尼效果
     * 如果内容还可以向上滑动，就滑动内容
     * @return
     */
    private boolean isNeedMoveTop() {
        int scrollY = getScrollY();
        return scrollY == TOP_Y;
    }

    private static final int DAMP_COEFFICIENT = 2;
    /**
     * 阻力
     */
    private float damk = DAMP_COEFFICIENT;

    /**
     * 计算当前y的偏移量
     * @param ev
     * @return
     */
    private int computeDeltaY(MotionEvent ev) {
        float currentY = ev.getY();

        int deltaY = (int) ((startY - currentY) / damk);
        startY = currentY;
        return deltaY;
    }

    /**
     * 手指放开，view重置，（回到原来的状态）
     */
    private void doReset() {
        boolean needReset = isNeedReset();

        if (!needReset) {
            return;
        }

        if (elasticView != null) {
            resetElasticView();
        } else {
            resetInnerView();
        }
    }

    private void resetInnerView() {
        int moveY = mInnerView.getTop() - normalRect.top;
        ValueAnimator animator = ObjectAnimator.ofInt(moveY,0);
        animator.setDuration(resetDelay);
        animator.setInterpolator(new OvershootInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int deltY = (int) animation.getAnimatedValue();
                mInnerView.layout(normalRect.left,normalRect.top + deltY,normalRect.right,normalRect.bottom + deltY);
            }
        });

        animator.start();
    }

    /**
     * 通过属性动画，插入插值器达到阻尼效果
     */
    private void resetElasticView() {
        //1、当前高度，原先高度
        ValueAnimator animator = ObjectAnimator.ofInt(elasticView.getLayoutParams().height,originHeight);
        animator.setDuration(resetDelay);
        //2、插值器
        animator.setInterpolator(new OvershootInterpolator());

        //3、不断获取中间值，改变弹性view的高度
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = elasticView.getLayoutParams();
                layoutParams.height = value;
                elasticView.setLayoutParams(layoutParams);
            }
        });
        animator.start();
    }

    //是否需要还原
    private boolean isNeedReset() {
        if (elasticView != null) {
            return originHeight != elasticView.getLayoutParams().height;
        } else {
            return !normalRect.isEmpty();
        }
    }
}
