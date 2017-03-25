package com.jayden.testandroid.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * ========================
 * 当dispatchTouchEvent(ev)直接返回true时，没有调用super.dispatchTouchEvent(ev),则不会调用onTouchEvent
  TestEventActivity dispatchTouchEvent = 0
  EventRelativeLayout dispatchTouchEvent = 0
  EventRelativeLayout onInterceptTouchEvent = 0
  EventRelativeLayout onInterceptTouchEvent = false
  EventTextView dispatchTouchEvent = 0
  EventRelativeLayout dispatchTouchEvent = true
  TestEventActivity dispatchTouchEvent = true
  TestEventActivity dispatchTouchEvent = 1
  EventRelativeLayout dispatchTouchEvent = 1
  EventRelativeLayout onInterceptTouchEvent = 1
  EventRelativeLayout onInterceptTouchEvent = false
  EventTextView dispatchTouchEvent = 1
  EventRelativeLayout dispatchTouchEvent = true
  TestEventActivity dispatchTouchEvent = true

 * ========================
 * 当dispatchTouchEvent(ev)直接返回false时，没有调用super.dispatchTouchEvent(ev),则不会调用onTouchEvent
 * TestEventActivity dispatchTouchEvent = 0
 EventRelativeLayout dispatchTouchEvent = 0
 EventRelativeLayout onInterceptTouchEvent = 0
 EventRelativeLayout onInterceptTouchEvent = false
 EventTextView dispatchTouchEvent = 0
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
 
 
 ======================================
 onTouchEvent 返回false，有调用super.onTouchEvent(ev)
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

 总结：返回false说明它不消费此事件，则后面的事件都不会传递过来

 * Created by Jayden on 2017/2/24.
 */

public class EventTextView extends TextView {

    public EventTextView(Context context) {
        super(context);
    }

    public EventTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EventTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("jayden", "EventTextView dispatchTouchEvent = " + event.getAction());
        boolean dispatch = super.dispatchTouchEvent(event);
        Log.e("jayden", "EventTextView dispatchTouchEvent = " + dispatch);
        return dispatch;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("jayden", "EventTextView onTouchEvent = " + event.getAction());
        boolean onTouchEvent = super.onTouchEvent(event);
        Log.e("jayden", "EventTextView onTouchEvent = " + onTouchEvent);
        return false;
    }
    
}
