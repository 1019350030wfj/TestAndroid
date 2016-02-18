package com.jayden.testandroid.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * 自己实现measure，layout， 还有执行子view的measure和layout
 * Created by Jayden on 2016/2/17.
 */
public class HorizontalScrollViewEx extends ViewGroup {

    private Scroller mScroller;//滑动
    private VelocityTracker mVelocityTracker;//速度追踪

    //记录之前滑动的x，y左边
    private int mLastX;
    private int mLastY;

    private int mChildIndex;
    private int mChildCount;
    private int mChildWidth;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        if (mScroller == null) {
            mScroller = new Scroller(getContext());
            mVelocityTracker = VelocityTracker.obtain();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth = 0;
        int measureHeight = 0;
        final int childCount = getChildCount();
        //测量所有子view
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        //宽和高是否采用wrap_content
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (childCount == 0) {
            //如果没有孩子view，直接设置宽和高为0
            setMeasuredDimension(0,0);
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            //宽和高都是wrap_content，那么宽就是所有子View的宽之和，高就是第一个子View的高
            int width = getChildAt(0).getMeasuredWidth() * childCount;
            int height = getChildAt(0).getMeasuredHeight();
            setMeasuredDimension(width,height);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //宽是wrap_content，那么宽就是所有子View的宽之和，高就是父View建议的高
            setMeasuredDimension(getChildAt(0).getMeasuredWidth() * childCount,heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //高是wrap_content，那么宽就是父View建议的宽，高就是第一个子View的高
            setMeasuredDimension(widthSize,getChildAt(0).getMeasuredHeight());
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childLeft = 0;
        final int childCount = getChildCount();
        mChildCount = childCount;
        //遍历所有子View，调用其layout
        for (int i = 0; i < childCount; i++) {
            final View childView = getChildAt(i);
            if (childView.getVisibility() != View.GONE) {
                final int childWidth = childView.getMeasuredWidth();
                mChildWidth = childWidth;
                childView.layout(childLeft,0,childLeft+childWidth,childView.getMeasuredHeight());
                childLeft += childWidth;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                //down事件不拦截，否则子View将接受不到任何的触摸事件
                intercepted = false;
                if (! mScroller.isFinished()) {
                    mScroller.abortAnimation();
                    intercepted = true;
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //判断如果是水平方向的滑动，就拦截事件
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;
                if (Math.abs(deltaX) > Math.abs(deltaY)) {
                    //拦截，交由自己的onTouchEvent函数处理
                    intercepted = true;
                } else {
                    intercepted = false;
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
        }
        mLastY = y;
        mLastX = x;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mVelocityTracker.addMovement(event);
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (! mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                int deltaX = x - mLastX;
                //现在相对于之前的距离
                scrollBy(-deltaX,0);
                break;
            }
            case MotionEvent.ACTION_UP: {
                int scrollX = getScrollX();
                int scrollToChildIndex = scrollX / mChildCount;
                mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = mVelocityTracker.getXVelocity();
                if (Math.abs(xVelocity) >= 50) {
                    mChildIndex = xVelocity > 0 ? mChildIndex + 1 : mChildIndex - 1;
                } else {
                    mChildIndex = (scrollX + mChildWidth / 2) / (mChildCount - 1);
                }
                mChildIndex = Math.max(0,Math.min(mChildIndex,(mChildCount - 1)));
                int dx = mChildIndex * mChildWidth - scrollX;
                smoothScollBy(dx,0);
                mVelocityTracker.clear();
                break;
            }
        }
        mLastX = x;
        mLastY = y;
        return true;
    }

    private void smoothScollBy(int dx, int dy) {
        mScroller.startScroll(getScrollX(),0,dx,dy,500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}
