package com.jayden.testandroid.customview.dragviewhelper;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by Jayden on 2016/5/17.
 * Email : 1570713698@qq.com
 */
public class DragLayout extends LinearLayout {

    private ViewDragHelper mViewDragHelper;
    private View mDragView1;
    private View mDragView2;

    private boolean mDragHorizontal;
    private boolean mDragVertical;
    private boolean mDragEage;

    public DragLayout(Context context) {
        this(context,null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this,1.0f,new DragCallbakc());
        mDragEage = true;
    }

    @Override
    protected void onFinishInflate() {
        mDragView1 = getChildAt(0);
        mDragView2 = getChildAt(1);
        setDragHorizontal(true);
    }

    public void setDragHorizontal(boolean dragHorizontal) {
        mDragHorizontal = dragHorizontal;
        mDragView2.setVisibility(View.GONE);
    }

    public void setDragVertical(boolean dragVertical) {
        mDragVertical = dragVertical;
        mDragView2.setVisibility(View.GONE);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mViewDragHelper.cancel();
            return false;
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    private class DragCallbakc extends ViewDragHelper.Callback {

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
//            invalidate();
            Log.d("jayden","left = " + left);
            Log.d("jayden","dx = " + dx);
        }

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mDragView1 == child;
        }

        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            if (mDragEage) {
                mViewDragHelper.captureChildView(mDragView1,pointerId);
            }
        }

        /**
         * 约束 在水平方向上，可以拖动的距离
         * @param child
         * @param left
         * @param dx
         * @return
         */
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            if (mDragHorizontal) {
                int leftBound = getPaddingLeft();
                int rightBound = getWidth() - mDragView1.getWidth();
                return Math.min(Math.max(left, leftBound), rightBound);
            }
            return super.clampViewPositionHorizontal(child,left,dx);
        }

        /**
         * 约束在垂直方向可以拖动的距离
         * @param child
         * @param top
         * @param dy
         * @return
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            if (mDragVertical) {
                int topBound = getPaddingTop();
                int bottomBound = getHeight() - mDragView1.getHeight();
                return Math.min(Math.max(top,topBound),bottomBound);
            }
            return super.clampViewPositionVertical(child, top, dy);
        }
    }
}
