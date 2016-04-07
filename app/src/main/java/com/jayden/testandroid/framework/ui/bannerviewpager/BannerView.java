package com.jayden.testandroid.framework.ui.bannerviewpager;

import android.content.Context;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.jayden.testandroid.R;
import com.jayden.testandroid.utils.ConvertUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Jayden on 2016/4/6.
 * Email : 1570713698@qq.com
 */
public class BannerView extends FrameLayout implements ViewPager.OnPageChangeListener {

    private Context context;
    private static final int MSTG = 0x100;

    /**
     * 轮播图最大数
     */
    private int totalCount = Integer.MAX_VALUE;

    /**
     * 当前banner需要显式的数量
     */
    private int showCount;
    private int currentPosition = 0;
    private ViewPager viewPager;
    private LinearLayout carouselLayout;
    private Adapter adapter;

    /**
     * 轮播切换小圆点宽度默认宽度
     */
    private static final int DOT_DEFAULT_W = 5;
    /**
     * 轮播切换小圆点宽度
     */
    private int IndicatorDotWidth = DOT_DEFAULT_W;
    /**
     * 用户是否干预
     */
    private boolean isUserTouched = false;

    /**
     * 默认的轮播时间
     */
    private static final int DEFAULT_TIME = 3000;

    /**
     * 设置轮播时间
     */
    private int switchTime = DEFAULT_TIME;

    /**
     * 轮播图定时器
     */
    private Timer timer;

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onDetachedFromWindow() {
        cancelTimer();
        super.onDetachedFromWindow();
    }

    private void init() {
        viewPager.setAdapter(null);
        carouselLayout.removeAllViews();
        if (adapter.isEmpty()) {
            return;
        }

        int count = adapter.getCount();
        showCount = adapter.getCount();

        for (int i = 0; i < count; i++) {
            View view = new View(context);
            if (currentPosition == i) {
                view.setPressed(true);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        IndicatorDotWidth + (int) ConvertUtils.dipToPx(context, 3),
                        IndicatorDotWidth + (int) ConvertUtils.dipToPx(context, 3)
                );
                params.setMargins(IndicatorDotWidth, 0, 0, 0);
                view.setLayoutParams(params);
            } else {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(IndicatorDotWidth, IndicatorDotWidth);
                params.setMargins(IndicatorDotWidth, 0, 0, 0);
                view.setLayoutParams(params);
            }
            view.setBackgroundResource(R.drawable.carousel_layout_dot);
            carouselLayout.addView(view);
        }

        viewPager.setAdapter(new ViewPagerAdapter());
        viewPager.setCurrentItem(0);
        viewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP: {
                        isUserTouched = false;
                        break;
                    }
                    case MotionEvent.ACTION_MOVE:
                    case MotionEvent.ACTION_DOWN: {
                        isUserTouched = true;
                        break;
                    }
                }
                return false; // false so onClick can perform
            }
        });

        startTimer(switchTime);
    }

    //设置adapter，这个方法需要再使用时设置
    public void setAdapter(Adapter adapter) {
        this.adapter = adapter;
        if (adapter != null) {
            init();
        }
    }

    private boolean isSwitch;//标记timer是否已经启动

    public void startTimer(long delay) {
        //制定周期和延时开启一个定时任务
        if (!isSwitch) {
            isSwitch = true;
            timer = timer == null ? new Timer() : timer;
            mTimeTask = mTimeTask == null ? new TimerTaskImpl() : mTimeTask;
            timer.schedule(mTimeTask, delay, switchTime);
        }
    }

    public void cancelTimer() {
        if (this.timer != null) {
            this.timer.cancel();
            timer = null;
            mTimeTask.cancel();
            mTimeTask = null;
            isSwitch = false;
        }
        totalCount = Integer.MAX_VALUE;
        Log.d("jayden","totalCount = " + totalCount);
    }

    /**
     * 可自定义设置轮播图切换时间，单位毫秒
     *
     * @param switchTime millseconds
     */
    public void setSwitchTime(int switchTime) {
        this.switchTime = switchTime;
    }

    /**
     * 可自定义设置轮播图,小圆圈的宽度
     *
     * @param indicatorDotWidth
     */
    public void setIndicatorDotWidth(int indicatorDotWidth) {
        IndicatorDotWidth = indicatorDotWidth;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        View view = LayoutInflater.from(context).inflate(R.layout.carousel_layout, null);
        viewPager = (ViewPager) view.findViewById(R.id.gallery);
        carouselLayout = (LinearLayout) view.findViewById(R.id.CarouselLayoutPage);
        IndicatorDotWidth = (int) ConvertUtils.dipToPx(context, IndicatorDotWidth);
        viewPager.addOnPageChangeListener(this);
        addView(view);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentPosition = position;
        int count = carouselLayout.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = carouselLayout.getChildAt(i);
            if (position % showCount == i) {
                view.setSelected(true);
                //当前位置的点要绘制的较大一点，高亮显示
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        IndicatorDotWidth + (int) ConvertUtils.dipToPx(context, 3),
                        IndicatorDotWidth + (int) ConvertUtils.dipToPx(context, 3));
                params.setMargins(IndicatorDotWidth, 0, 0, 0);
                view.setLayoutParams(params);
            } else {
                view.setSelected(false);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(IndicatorDotWidth, IndicatorDotWidth);
                params.setMargins(IndicatorDotWidth, 0, 0, 0);
                view.setLayoutParams(params);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return totalCount;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            position %= showCount;
            View view = adapter.getView(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            int position = viewPager.getCurrentItem();
            if (position == 0) {
                position = showCount;
                viewPager.setCurrentItem(position, false);
            } else if (position == totalCount - 1) {
                position = showCount - 1;
                viewPager.setCurrentItem(position, false);
            }
        }
    }

    private class TimerTaskImpl extends TimerTask {
        @Override
        public void run() {
            //用户滑动时，定时任务不响应
            if (!isUserTouched) {
                currentPosition = (currentPosition + 1) % totalCount;
                handler.sendEmptyMessage(MSTG);
            }
        }
    }

    private TimerTaskImpl mTimeTask;

    private android.os.Handler handler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSTG) {
                if (currentPosition == totalCount - 1) {
                    viewPager.setCurrentItem(showCount - 1, false);
                } else {
                    viewPager.setCurrentItem(currentPosition, false);
                }
            }
        }
    };

    public interface Adapter {
        boolean isEmpty();

        View getView(int position);

        int getCount();
    }
}
