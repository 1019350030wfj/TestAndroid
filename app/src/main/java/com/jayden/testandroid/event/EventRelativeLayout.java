package com.jayden.testandroid.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * 当dispatchTouchEvent直接返回true，没有调用super.dispatchTouchEvent(ev)时，
 * TestEventActivity dispatchTouchEvent = 0
 : EventRelativeLayout dispatchTouchEvent = 0
 : TestEventActivity dispatchTouchEvent = true
 : TestEventActivity dispatchTouchEvent = 1
 : EventRelativeLayout dispatchTouchEvent = 1
 : TestEventActivity dispatchTouchEvent = true

 总结： activity也直接返回true，不会调用onTouchEvent(ev)
 
 
 ===================
 当dispatchTouchEvent直接返回true，有调用super.dispatchTouchEvent(ev)时，
 TestEventActivity dispatchTouchEvent = 0
  EventRelativeLayout dispatchTouchEvent = 0
  EventRelativeLayout onInterceptTouchEvent = 0
  EventRelativeLayout onInterceptTouchEvent = false
  EventTextView dispatchTouchEvent = 0
  EventTextView onTouchEvent = 0
  EventTextView onTouchEvent = false
  EventTextView dispatchTouchEvent = false
  EventRelativeLayout onTouchEvent = 0
  EventRelativeLayout onTouchEvent = false
  EventRelativeLayout dispatchTouchEvent = false
  TestEventActivity dispatchTouchEvent = true
  TestEventActivity dispatchTouchEvent = 1
  EventRelativeLayout dispatchTouchEvent = 1
  EventRelativeLayout onTouchEvent = 1
  EventRelativeLayout onTouchEvent = false
  EventRelativeLayout dispatchTouchEvent = false
  TestEventActivity dispatchTouchEvent = true

 总结： 只要有调用super.dispatchTouchEvent(ev),整个流程都会走，如果返回true，则activity的onTouchEvent不会执行
 ==============================
 当dispatchTouchEvent直接返回false，有调用super.dispatchTouchEvent(ev)时，
 TestEventActivity dispatchTouchEvent = 0
  EventRelativeLayout dispatchTouchEvent = 0
  EventRelativeLayout onInterceptTouchEvent = 0
  EventRelativeLayout onInterceptTouchEvent = false
  EventTextView dispatchTouchEvent = 0
  EventTextView onTouchEvent = 0
  EventTextView onTouchEvent = false
  EventTextView dispatchTouchEvent = false
  EventRelativeLayout onTouchEvent = 0
  EventRelativeLayout onTouchEvent = false
  EventRelativeLayout dispatchTouchEvent = false
  TestEventActivity onTouchEvent = 0
  TestEventActivity onTouchEvent = false
  TestEventActivity dispatchTouchEvent = false
  TestEventActivity dispatchTouchEvent = 1
  TestEventActivity onTouchEvent = 1
  TestEventActivity onTouchEvent = false
  TestEventActivity dispatchTouchEvent = false

 总结： 只要有调用super.dispatchTouchEvent(ev),整个流程都会走，如果返回false，则activity的onTouchEvent会执行
 * Created by Jayden on 2017/2/24.
 */

public class EventRelativeLayout extends RelativeLayout {
    public EventRelativeLayout(Context context) {
        super(context);
    }

    public EventRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("jayden", "EventRelativeLayout dispatchTouchEvent = " + event.getAction());
        boolean dispatch = super.dispatchTouchEvent(event);
        Log.e("jayden", "EventRelativeLayout dispatchTouchEvent = " + dispatch);
        return dispatch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.e("jayden", "EventRelativeLayout onInterceptTouchEvent = " + event.getAction());
        boolean intercept = super.onInterceptTouchEvent(event);
        Log.e("jayden", "EventRelativeLayout onInterceptTouchEvent = " + intercept);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("jayden", "EventRelativeLayout onTouchEvent = " + event.getAction());
        boolean onTouchEvent = super.onTouchEvent(event);
        Log.e("jayden", "EventRelativeLayout onTouchEvent = " + onTouchEvent);
        return onTouchEvent;
    }
}
