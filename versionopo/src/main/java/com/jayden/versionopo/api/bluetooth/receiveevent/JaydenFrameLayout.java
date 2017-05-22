package com.jayden.versionopo.api.bluetooth.receiveevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by Jayden on 2016/9/1.
 */

public class JaydenFrameLayout extends FrameLayout {

    public JaydenFrameLayout(Context context) {
        super(context);
    }

    public JaydenFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JaydenFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //解决抢占焦点问题
    private boolean isHandle = true;

    public void setHandle(boolean handle) {
        isHandle = handle;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isHandle) {
            return true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isHandle) {
            return false;
        }
        return super.onTouchEvent(event);
    }
}
