package com.jayden.testandroid.customview.dragviewhelper;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jayden.testandroid.R;

/**
 * 移动的是内容
 * Created by Jayden on 2016/5/18.
 * Email : 1570713698@qq.com
 */
public class YoutubeLayout extends ViewGroup {

    private ViewDragHelper mViewDragHelper;
    private View mHeaderView;
    private View mDescView;

    private float mInitialMotionX;
    private float mInitialMotionY;

    private float mDragOffset;//mHeaderView在指定mDragRange中的占比。 也就是拖动了多少
    private int mDragRange;
    private int mTop;

    public YoutubeLayout(Context context) {
        this(context, null);
    }

    public YoutubeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YoutubeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new DragHelperCallback());
    }

    public void maximize() {
        smoothSlideTo(0f);
    }

    public void minimize() {
        smoothSlideTo(1f);
    }

    @Override
    protected void onFinishInflate() {
        mHeaderView = findViewById(R.id.header);
        mDescView = findViewById(R.id.desc);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int maxWidth = MeasureSpec.getSize(widthMeasureSpec);
        int maxHeight = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, 0),
                resolveSizeAndState(maxHeight, heightMeasureSpec, 0));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mDragRange = getHeight() - mHeaderView.getHeight();

        mHeaderView.layout(0, mTop, r, mTop + mHeaderView.getMeasuredHeight());
        mDescView.layout(0, mTop + mHeaderView.getMeasuredHeight(), r, mTop + b);
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        final int action = MotionEventCompat.getActionMasked(ev);
//        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
//            mViewDragHelper.cancel();
//            return false;
//        }
//        return mViewDragHelper.shouldInterceptTouchEvent(ev);

        //获取动作，可以多个手指点这个就是与getAction的区别（只能单个手指）
        final int action = MotionEventCompat.getActionMasked(ev);
        if (action != MotionEvent.ACTION_DOWN) {
            mViewDragHelper.cancel();
            return super.onInterceptTouchEvent(ev);
        }

        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mViewDragHelper.cancel();
            return false;
        }

        float x = ev.getX();
        float y = ev.getY();
        boolean interceptTag = false;

        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionY = y;
                mInitialMotionX = x;
                interceptTag = mViewDragHelper.isViewUnder(mHeaderView,(int)x,(int)y);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                float adx = Math.abs(x - mInitialMotionX);
                float ady = Math.abs(y - mInitialMotionY);
                int slop = mViewDragHelper.getTouchSlop();
                if (ady > slop && adx > ady) {
                    mViewDragHelper.cancel();
                    return false;
                }
            }
        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev) || interceptTag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        mViewDragHelper.processTouchEvent(event);
//        return true;
        mViewDragHelper.processTouchEvent(event);

        final int action = event.getAction();
        final float x = event.getX();
        final float y = event.getY();

        boolean isHeaderViewUnder = mViewDragHelper.isViewUnder(mHeaderView, (int) x, (int) y);
        switch (action & MotionEventCompat.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mInitialMotionX = x;
                mInitialMotionY = y;
                break;
            }
            case MotionEvent.ACTION_UP: {
                final float dx = x - mInitialMotionX;
                final float dy = y - mInitialMotionY;
                final int slop = mViewDragHelper.getTouchSlop();
                if (dx * dx + dy * dy < slop * slop && isHeaderViewUnder) {
                    if (mDragOffset == 0) {
                        smoothSlideTo(1f);
                    } else {
                        smoothSlideTo(0f);
                    }
                }
                break;
            }
        }
        return isHeaderViewUnder && isViewHit(mHeaderView, (int) x, (int) y) || isViewHit(mDescView, (int) x, (int) y);
    }

    private boolean isViewHit(View view, int x, int y) {
        int[] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        int[] parentLocation = new int[2];
        this.getLocationOnScreen(parentLocation);
        int screenX = parentLocation[0] + x;
        int screenY = parentLocation[1] + y;

        return screenX >= viewLocation[0] && screenX <= viewLocation[0] + view.getWidth() &&
                screenY >= viewLocation[1] && screenY <= viewLocation[1] + view.getHeight();
    }

    private boolean smoothSlideTo(float slideOffset) {
        final int topBound = getPaddingTop();
        int y = (int) (topBound + slideOffset * mDragRange);

        //滑动header view
        if (mViewDragHelper.smoothSlideViewTo(mHeaderView,mHeaderView.getLeft(),y)) {
            ViewCompat.postInvalidateOnAnimation(this);
            return true;
        }
        return false;
    }


    private class DragHelperCallback extends ViewDragHelper.Callback {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return child == mHeaderView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //top 被拖动view的top
            Log.d("jayden","top = " + top);
            Log.d("jayden","left = " + left);

            //当headerView被拖动的同时， 也进行缩放
            mTop = top;
            mDragOffset = (float) top / mDragRange;
            Log.d("jayden","mDragOffset = " + mDragOffset);

            mHeaderView.setPivotX(mHeaderView.getWidth());
            mHeaderView.setPivotY(mHeaderView.getHeight());
            mHeaderView.setScaleX(1 - mDragOffset / 2);
            mHeaderView.setScaleY(1 - mDragOffset / 2);

            mDescView.setPivotX(mDescView.getWidth());
            mDescView.setPivotY(0);
            mDescView.setScaleX(1 - mDragOffset / 2);
            mDescView.setScaleY(1 - mDragOffset / 2);

//            mHeaderView.setAlpha(1-mDragOffset);
            mDescView.setAlpha(1-mDragOffset); //0完全透明
            requestLayout();
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            int top = getPaddingTop();
            if (yvel > 0 || (yvel == 0 && mDragOffset > 0.5f)) {
                top += mDragRange;
            }
            mViewDragHelper.settleCapturedViewAt(releasedChild.getLeft(),top);
            invalidate();
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mDragRange;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            int topBound = getPaddingTop();
            int bottomBound = getHeight() - mHeaderView.getHeight() - mHeaderView.getPaddingBottom();

            return Math.min(Math.max(top,topBound),bottomBound);
        }
    }
}
