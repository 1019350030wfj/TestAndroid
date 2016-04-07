package com.jayden.testandroid.framework.ui.appbar;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Jayden on 2016/3/31.
 * Email : 1570713698@qq.com
 */
public class PagerChangeListener implements ViewPager.OnPageChangeListener {

    private ImageAinmator mImageAnimator;
    private int mCurrentPosition;
    private int mFinalPosition;
    private boolean mIsScrolling = false;

    public PagerChangeListener(ImageAinmator imageAinmator) {
        this.mImageAnimator = imageAinmator;
    }

    public static PagerChangeListener newInstance(SimpleAdapter adapter, ImageView targetImge,ImageView outgoingImage) {
        ImageAinmator ainmator = new ImageAinmator(adapter,targetImge,outgoingImage);
        return new PagerChangeListener(ainmator);
    }

    /**
     * 滑动监听
     *
     * @param position             当前位置
     * @param positionOffset       偏移[当前值+-1]
     * @param positionOffsetPixels 偏移像素
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        Log.e("jayden", "position: " + position + ", positionOffset: " + positionOffset);

        // 以前滑动, 现在终止
        if (isFinishScrolling(position, positionOffset)) {
            finishScroll(position);
        }

        // 判断前后滑动
        if (isStartingScrollToPrevious(position, positionOffset)) {
            startScroll(position);
        } else if (isStartingScrollToNext(position, positionOffset)) {
            startScroll(position + 1); // 向后滚动需要加1
        }

        // 向后滚动
        if (isScrollingToNext(position, positionOffset)) {
            mImageAnimator.forward(positionOffset);
        } else if (isScrollingToPrevious(position, positionOffset)) { // 向前滚动
            mImageAnimator.backwards(positionOffset);
        }
    }

    /**
     * 终止滑动
     * 滑动 && [偏移是0&&滑动终点] || 动画之中
     *
     * @param position       位置
     * @param positionOffset 偏移量
     * @return 终止滑动
     */
    public boolean isFinishScrolling(int position, float positionOffset) {
        return mIsScrolling && (positionOffset == 0f && position == mFinalPosition) || !mImageAnimator.isWithin(position);
    }

    /**
     * 从静止到开始滑动, 下一个
     * 未滑动 && 位置是当前位置 && 偏移量不是0
     *
     * @param position       位置
     * @param positionOffset 偏移量
     * @return 是否
     */
    private boolean isStartingScrollToNext(int position, float positionOffset) {
        return !mIsScrolling && position == mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 从静止到开始滑动, 前一个[position会-1]
     *
     * @param position       位置
     * @param positionOffset 偏移量
     * @return 是否
     */
    private boolean isStartingScrollToPrevious(int position, float positionOffset) {
        return !mIsScrolling && position != mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 开始滚动, 向后
     *
     * @param position       位置
     * @param positionOffset 偏移
     * @return 是否
     */
    private boolean isScrollingToNext(int position, float positionOffset) {
        return mIsScrolling && position == mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 开始滚动, 向前
     *
     * @param position       位置
     * @param positionOffset 偏移
     * @return 是否
     */
    private boolean isScrollingToPrevious(int position, float positionOffset) {
        return mIsScrolling && position != mCurrentPosition && positionOffset != 0f;
    }

    /**
     * 开始滑动
     * 滚动开始, 结束位置是position[前滚时position会自动减一], 动画从当前位置到结束位置.
     *
     * @param position 滚动结束之后的位置
     */
    private void startScroll(int position) {
        mIsScrolling = true;
        mFinalPosition = position;

        // 开始滚动动画
        mImageAnimator.start(mCurrentPosition, position);
    }

    /**
     * 如果正在滚动, 结束时, 固定position位置, 停止滚动, 调动截止动画
     *
     * @param position 位置
     */
    private void finishScroll(int position) {
        if (mIsScrolling) {
            mCurrentPosition = position;
            mIsScrolling = false;
            mImageAnimator.end(position);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (!mIsScrolling) {
            mIsScrolling = true;
            mFinalPosition = position;
            mImageAnimator.start(mCurrentPosition, position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if (!mIsScrolling) {
            mIsScrolling = true;
        }
    }

    /**
     * 终止滑动
     * 滑动 && [偏移是0&&滑动终点] || 动画之中
     *
     * @param position       位置
     * @param positionOffset 偏移量
     * @return 终止滑动
     */
    public boolean isFinishedScrolling(int position, float positionOffset) {
        return mIsScrolling && (positionOffset == 0f && position == mFinalPosition) || !mImageAnimator.isWithin(position);
    }
}
