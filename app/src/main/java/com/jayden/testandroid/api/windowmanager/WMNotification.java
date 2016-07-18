package com.jayden.testandroid.api.windowmanager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayden.testandroid.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jayden on 2016/6/14.
 * Email : 1570713698@qq.com
 */
public class WMNotification implements View.OnTouchListener {

    private Context mContext;

    private TextView mTvTitle;
    private TextView mTvContent;
    private ImageView mIvIcon;
    private TextView mTvTime ;

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;
    private int mStatusBarHeight;
    private int mScreenWidth;

    public WMNotification(Builder builder) {
        this.mContext = builder.getContext();

        mStatusBarHeight = getStatusBarHeight();
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;

        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.type = WindowManager.LayoutParams.TYPE_TOAST;//系统提示window
        mWindowParams.gravity = Gravity.LEFT | Gravity.TOP;//左上角
        mWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        //让window能够全屏，且延伸至状态栏
        //让window不阻塞事件传递， 能够传递给window后面的窗口
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        //window进入和退出的动画
        mWindowParams.windowAnimations = R.style.NotificationAnim;
        mWindowParams.x = 0;
        mWindowParams.y = -mStatusBarHeight;

        setContentView(mContext,builder);
    }

    private View mContentView;
    private void setContentView(Context context, Builder builder) {
        mContentView = LayoutInflater.from(context).inflate(R.layout.layout_notification,null);

        //动态设置view的高度
        View statusBar = mContentView.findViewById(R.id.v_state_bar);
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = mStatusBarHeight;
        statusBar.setLayoutParams(layoutParams);

        mIvIcon = (ImageView) mContentView.findViewById(R.id.iv_icon);
        mTvTitle = (TextView) mContentView.findViewById(R.id.tv_title);
        mTvContent = (TextView) mContentView.findViewById(R.id.tv_content);
        mTvTime = (TextView) mContentView.findViewById(R.id.tv_time);

        setIcon(builder.mImgRes);
        setTitle(builder.mTitle);
        setContent(builder.mContent);
        setTime(builder.time);

        mContentView.setOnTouchListener(this);
    }

    public void setIcon(int imgRes) {
        if (-1 != imgRes) {
            mIvIcon.setVisibility(View.VISIBLE);
            mIvIcon.setImageResource(imgRes);
        }
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(title);
        }
    }

    public void setContent(String content) {
        mTvContent.setText(content);
    }

    public void setTime(long time) {
        SimpleDateFormat formatDateTime = new SimpleDateFormat("HH:mm", Locale.getDefault());
        mTvTime.setText(formatDateTime.format(new Date(time)));
    }

    /**
     * 获取状态栏高度
     * @return
     */
    private int getStatusBarHeight() {
        int height = 0;
        int resId = mContext.getResources().getIdentifier("status_bar_height","dimen","android");
        if (resId > 0) {
            height = mContext.getResources().getDimensionPixelSize(resId);
        }
        return height;
    }

    private ValueAnimator restoreAnimator = null;
    private ValueAnimator dismissAnimator = null;
    private static final int DIRECTION_LEFT = -1;
    private static final int DIRECTION_NONE = 0;
    private static final int DIRECTION_RIGHT = 1;

    private static final int DISMISS_INTERVAL = 3000;

    private static final int HIDE_WINDOW = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HIDE_WINDOW: {
                    dismiss();
                    break;
                }
            }
        }
    };

    /**
     * 显示通知栏
     */
    public void show() {
        if(!isShowing) {
            isShowing = true;
            mWindowManager.addView(mContentView,mWindowParams);
            autoDismiss();
        }
    }

    /**
     * 关闭通知栏
     */
    public void dismiss() {
        if (isShowing) {
            resetState();
            mWindowManager.removeView(mContentView);
        }
    }

    private int downX;
    private int direction = DIRECTION_NONE;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (isAnimatorRunning()) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = (int) event.getRawX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //滑动取消自动消失
                handler.removeMessages(HIDE_WINDOW);
                int deltaX = (int)event.getRawX() - downX;

                //判断移动方向
                direction = deltaX > 0 ? DIRECTION_RIGHT : DIRECTION_LEFT;

                updateLocation(deltaX,mWindowParams.y);
                break;
            }

            case MotionEvent.ACTION_UP: {
                //判断移动的距离是否超过屏幕的一半
                //如果超过，则根据移动的方向，动画消失
                if (Math.abs(mWindowParams.x) > mScreenWidth / 2) {
                    startDismissAnimator(direction);
                } else {
                    startRestoreAnimator();
                }
                break;
            }
        }
        return false;
    }

    private void resetState() {
       isShowing = false;
        mWindowParams.x = 0;
        mWindowParams.y = -mStatusBarHeight;
    }

    /**
     * 恢复到原来的位置
     */
    private void startRestoreAnimator() {
        restoreAnimator = ValueAnimator.ofInt(mWindowParams.x,0);
        restoreAnimator.setDuration(ANIMATOR_DURATION);
        restoreAnimator.setEvaluator(new IntEvaluator());
        restoreAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateLocation((Integer) animation.getAnimatedValue(),-mStatusBarHeight);
            }
        });

        restoreAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                restoreAnimator = null;
                autoDismiss();
            }
        });
        restoreAnimator.start();
    }

    /**
     * 自动消失,通过handler实现延时发送消息
     */
    private void autoDismiss() {
        handler.removeMessages(HIDE_WINDOW);
        handler.sendEmptyMessageDelayed(HIDE_WINDOW,DISMISS_INTERVAL);
    }

    private final static int ANIMATOR_DURATION = 300;

    /**
     * 根据方向移动消失
     * @param direction
     */
    private void startDismissAnimator(int direction) {
        if (direction == DIRECTION_LEFT) {
            dismissAnimator = ValueAnimator.ofInt(mWindowParams.x,-mScreenWidth);
        } else {
            dismissAnimator = ValueAnimator.ofInt(mWindowParams.x,mScreenWidth);
        }
        dismissAnimator.setDuration(ANIMATOR_DURATION);
        dismissAnimator.setEvaluator(new IntEvaluator());

        dismissAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateLocation((Integer) animation.getAnimatedValue(),-mStatusBarHeight);
            }
        });

        dismissAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                dismissAnimator = null;
                dismiss();
            }
        });
        dismissAnimator.start();
    }

    private boolean isShowing = false;

    /**
     * 移动通知栏
     * @param x
     * @param y
     */
    private void updateLocation(int x, int y) {
        if (isShowing) {
            mWindowParams.x = x;
            mWindowParams.y = y;
            mWindowManager.updateViewLayout(mContentView,mWindowParams);
        }
    }

    /**
     * 判断是否有动画正在执行
     * @return
     */
    private boolean isAnimatorRunning() {
        return (restoreAnimator != null && restoreAnimator.isRunning()) || (dismissAnimator != null && dismissAnimator.isRunning());
    }

    public static class Builder {
        private Context mContext;

        private String mTitle;
        private String mContent;
        private int mImgRes = -1;
        private long time = System.currentTimeMillis();

        public Context getContext() {
            return mContext;
        }

        public Builder setContext(Context context) {
            this.mContext = context;
            return this;
        }

        public Builder setContent(String mContent) {
            this.mContent = mContent;
            return this;
        }

        public Builder setTitle(String mTitle) {
            this.mTitle = mTitle;
            return this;
        }

        public Builder setImgRes(int imgRes) {
            this.mImgRes = imgRes;
            return this;
        }

        public Builder setTime(long time) {
            this.time = time;
            return this;
        }

        public WMNotification build() {
            if (mContext == null) {
                throw new IllegalArgumentException("mContext must be not null");
            }
            return new WMNotification(this);
        }
    }
}
