package com.jayden.testandroid.customview.floatview;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * Created by Jayden on 2016/8/29.
 */

public class FloatView extends LinearLayout {

    private WindowManager wm;
    private WindowManager.LayoutParams wml;
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    private IFloatViewClick listener;
    private boolean isAllowTouch = true;

    public FloatView(Context context, int x, int y, int layoutId) {
        super(context);
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        init(view, x, y);
    }

    public FloatView(Context context, int x, int y, View childView) {
        super(context);
        init(childView, x, y);
    }

    /**
     * 初始化参数
     *
     * @param view
     * @param x
     * @param y
     */
    private void init(View view, int x, int y) {
        wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        //设置你要添加控件的类型，TYPE_ALERT需要申明权限，
        // Toast不需要，在某些定制系统中会禁止悬浮框显示，
        // 所以最后用TYPE_TOAST
        //wml.type = WindowManager.LayoutParams.TYPE_TOAST;
        wml = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_TOAST);
        wml.gravity =  Gravity.LEFT | Gravity.TOP;
        wml.format = PixelFormat.RGBA_8888;
        wml.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wml.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wml.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wml.x = x;
        wml.y = y;
        if (view != null) {
            addView(view);
        }
    }

    /**
     * 添加到window
     */
    public boolean addToWindow() {
        if (wm != null) {
            //大于4.4
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (!isAttachedToWindow()) {
                    wm.addView(this, wml);
                    return true;
                } else {
                    return false;
                }
            } else {
                try {
                    if (getParent() == null) {
                        wm.addView(this, wml);
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    /**
     * 从窗口移除
     *
     * @return
     */
    public boolean removeFromWindow() {
        if (wm != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (isAttachedToWindow()) {
                    wm.removeViewImmediate(this);
                    return true;
                } else {
                    return false;
                }
            } else {
                try {
                    if (getParent() != null) {
                        wm.removeViewImmediate(this);
                    }
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }


        } else {
            return false;
        }

    }


    // 此wmParams为获取的全局变量，用以保存悬浮窗口的属性

    // 重写，返回true 拦截触摸事件
//	 @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        return isAllowTouch;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchStartX = (int) event.getRawX() - this.getMeasuredWidth() / 2;
                mTouchStartY = (int) event.getRawY() - this.getMeasuredHeight() / 2 - 25;

                return true;
            case MotionEvent.ACTION_MOVE:
                wml.x = (int) event.getRawX() - this.getMeasuredWidth() / 2;
                // 减25为状态栏的高度
                wml.y = (int) event.getRawY() - this.getMeasuredHeight() / 2 - 25;
                // 刷新
                wm.updateViewLayout(this, wml);
                return true;
            case MotionEvent.ACTION_UP:
                y = (int) event.getRawY() - this.getMeasuredHeight() / 2 - 25;
                x = (int) event.getRawX() - this.getMeasuredWidth() / 2;
                if (Math.abs(y - mTouchStartY) > 10 || Math.abs(x - mTouchStartX) > 10) {
                    wm.updateViewLayout(this, wml);
                } else {
                    if (listener != null) {
                        listener.onFloatViewClick();
                    }

                }
                return true;
            default:
                break;
        }
        return false;

    }


    public void setIsAllowTouch(boolean flag) {
        isAllowTouch = flag;
    }

    /**
     * 更新位置
     *
     * @param x
     * @param y
     */
    public void updateFloatViewPosition(int x, int y) {
        wml.x = x;
        wml.y = y;
        wm.updateViewLayout(this, wml);
    }

    public void setFloatViewClickListener(IFloatViewClick listener) {
        this.listener = listener;
    }

    public interface IFloatViewClick {
        void onFloatViewClick();
    }
}
