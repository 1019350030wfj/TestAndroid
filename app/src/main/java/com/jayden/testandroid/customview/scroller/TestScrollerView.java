package com.jayden.testandroid.customview.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * Created by Jayden on 2016/5/17.
 * Email : 1570713698@qq.com
 */
public class TestScrollerView extends FrameLayout {

    private Scroller mScroller;

    public TestScrollerView(Context context) {
        this(context,null);
    }

    public TestScrollerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @Override
    public void computeScroll() {
        //判断滚动是否完成，true的话就是未完成
        if (mScroller.computeScrollOffset()) {
            Log.d("jayden","curX = " + mScroller.getCurrX());
            Log.d("jayden","curY = " + mScroller.getCurrY());
            Log.d("jayden","finalX = " + mScroller.getFinalX());
            Log.d("jayden","finalY = " + mScroller.getFinalY());
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());//内容滑动
            // 本案例中 调用postInvalidate和invalidate都可以
            invalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("jayden","onTouchEvent startX = " + mScroller.getStartX());
        Log.d("jayden","onTouchEvent startY = " + mScroller.getStartY());
        Log.d("jayden","onTouchEvent curX = " + mScroller.getCurrX());
        Log.d("jayden","onTouchEvent curY = " + mScroller.getCurrY());
        int dx = mScroller.getFinalX() + 100;
        Log.d("jayden","finalX = " +mScroller.getFinalX());
        smoothScrollTo(dx,0);
        return super.onTouchEvent(event);
    }

    private void smoothScrollTo(int dx, int dy) {
        dx -= mScroller.getFinalX();
        dy -= mScroller.getFinalY();
        smoothScrollBy(dx,dy);
    }

    private void smoothScrollBy(int dx, int dy) {
        mScroller.startScroll(mScroller.getFinalX(),mScroller.getFinalY(),dx,dy,3000);
        invalidate();
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }
}
