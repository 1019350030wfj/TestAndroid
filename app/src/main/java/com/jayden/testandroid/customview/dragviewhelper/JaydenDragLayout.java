package com.jayden.testandroid.customview.dragviewhelper;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.jayden.testandroid.customview.MyWebView;

/**
 * Created by Jayden on 2016/8/10.
 */

public class JaydenDragLayout extends FrameLayout {

    private ViewDragHelper mDragger;
    private View mDragView;

    public JaydenDragLayout(Context context) {
        super(context);
        initDrag();
    }

    public JaydenDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDrag();
    }

    private void initDrag() {
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return child == mDragView;
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int leftBound = getPaddingLeft();
                int rightBound = getWidth() - mDragView.getWidth();
                return Math.min(Math.max(left, leftBound), rightBound);
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                int topBound = getPaddingTop();
                int bottomBound = getHeight() - mDragView.getHeight();
                return Math.min(Math.max(top, topBound), bottomBound);
            }

            @Override
            public void onViewReleased(final View releasedChild, float xvel, float yvel) {
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mDragView == null) {
            mDragView = getChildAt(1);
        }
    }
    private MyWebView mWebview;

    public void setWebView(MyWebView webView) {
        this.mWebview = webView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        if (x > mDragView.getLeft() && x < mDragView.getRight() &&
                y > mDragView.getTop() && y < mDragView.getBottom() && mWebview != null) {
            mWebview.setHandle(false);
        } else {
            mWebview.setHandle(true);
        }
        boolean shouldInterceptTouchEvent = mDragger.shouldInterceptTouchEvent(ev);
        return shouldInterceptTouchEvent;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }


    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }
}
