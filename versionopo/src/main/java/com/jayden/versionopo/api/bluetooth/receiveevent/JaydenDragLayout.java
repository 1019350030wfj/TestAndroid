package com.jayden.versionopo.api.bluetooth.receiveevent;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;


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
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
                //这个主要是为了让可移动的view布局参数真正被修改，不然当可移动view中的内容发生变化时，就会导致可移动view复位
                LayoutParams layoutParams = (LayoutParams) mDragView.getLayoutParams();
                layoutParams.topMargin = top;
                layoutParams.leftMargin = left;
                mDragView.setLayoutParams(layoutParams);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                int leftBound = getPaddingLeft() - mDragView.getWidth() / 2;
                int rightBound = getWidth() - mDragView.getWidth() / 2;
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

    private JaydenFrameLayout mWebView;

    public void setDragContentContainer(JaydenFrameLayout mWebView) {
        this.mWebView = mWebView;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        if (mWebView != null) {
            if (x > mDragView.getLeft() && x < mDragView.getRight() &&
                    y > mDragView.getTop() && y < mDragView.getBottom()) {
                mWebView.setHandle(false);
            } else {
                mWebView.setHandle(true);
            }
        }
        return mDragger.shouldInterceptTouchEvent(ev);
    }

    private long thisEventTime;
    private long lastDownTime;
    private boolean mIsLongPressed;//是否是长按事件
    private float mLastX;
    private float mLastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                mLastX = x;
                mLastY = y;
                lastDownTime = System.currentTimeMillis();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                thisEventTime = System.currentTimeMillis();
                //检测是否长按,在非长按时检测
                mIsLongPressed = ViewUtils.isLongPressed(mLastX, mLastY,
                        x, y, lastDownTime, thisEventTime);
                if (mIsLongPressed && mIFncLayout != null){//长按
                    mIFncLayout.onAction(0,0);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                mIsLongPressed = false;
                break;
            }
        }

        mDragger.processTouchEvent(event);
        return true;
    }

    public boolean getIsLongPressed() {
        return mIsLongPressed;
    }

    @Override
    public void computeScroll() {
        if (mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    private IJaydenDragLayout mIFncLayout;

    public void setIFncLayout(IJaydenDragLayout iFncLayout) {
        this.mIFncLayout = iFncLayout;
    }

    public interface IJaydenDragLayout {
        void onAction(int action, Object object);
    }
}
