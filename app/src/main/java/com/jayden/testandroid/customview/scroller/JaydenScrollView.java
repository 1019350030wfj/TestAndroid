package com.jayden.testandroid.customview.scroller;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * 七种移动view的方法
 * 1、layout
 * 2、offsetLeftAndRight offsetTopAndBottom
 * 3、setLayoutParams  LayoutParams 为父布局下面的布局参数
 * 4、属性动画
 * =================== 上面移动的是整个view ===========================
 *
 * 5、补间动画   =》 移动只是内容
 * 6、scrollBy scrollTo 移动的是内容， 所以可以调用((View)getParent()).scrollBy(),来移动view
 * 7、Scroller 是有过度效果的  移动的是内容， 所以可以调用((View)getParent()).scrollBy(),来移动view
 *
 * Created by Jayden on 2016/8/18.
 */

public class JaydenScrollView extends View {

    private Scroller mScroller;

    public JaydenScrollView(Context context) {
        this(context,null);
    }

    public JaydenScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public JaydenScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    private float mCurX;
    private float mLastX;
    private float mCurY;
    private float mLastY;

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            ((View) getParent()).scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            //通过不断的重绘不断的调用computeScroll方法
            invalidate();
        }
    }

    public void smoothScrollTo(int destX,int destY){
        int scrollX=getScrollX();
        Log.e("jayden","scrollX = " + scrollX);
        int delta=destX-scrollX;
        //1000秒内滑向destX
        mScroller.startScroll(scrollX,0,delta,0,2000);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN: {
                mLastX = event.getX();
                mLastY = event.getY();
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                mCurX = event.getX();
                mCurY = event.getY();
                int deltY = (int) (mCurY - mLastY);
                int deltX = (int) (mCurX - mLastX);
//                layout(getLeft()+deltX,getTop()+deltY,getRight()+deltX,getBottom()+deltY);

//                offsetLeftAndRight(deltX);
//                offsetTopAndBottom(deltY);

//                LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + deltX;
//                layoutParams.topMargin = getTop() + deltY;
//                setLayoutParams(layoutParams);

//                ((View)getParent()).scrollBy(-deltX,-deltY);

                break;
            }
            case MotionEvent.ACTION_UP:{
                
                break;
            }
        }
        return super.onTouchEvent(event);
    }
}
