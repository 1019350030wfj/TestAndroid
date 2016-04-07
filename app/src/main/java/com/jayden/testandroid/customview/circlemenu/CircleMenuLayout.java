package com.jayden.testandroid.customview.circlemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Jayden on 2016/3/21.
 */
public class CircleMenuLayout extends ViewGroup {
    public CircleMenuLayout(Context context) {
        this(context, null);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // Child sizes
    private int maxChildWidth = 0;
    private int maxChildHeight = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //measure child first
        maxChildWidth = 0;
        maxChildHeight = 0;

        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.AT_MOST);
        int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                MeasureSpec.getSize(heightMeasureSpec),MeasureSpec.AT_MOST);
        final int childCount = getChildCount();
        for (int i=0; i<childCount;i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }
            child.measure(childWidthMeasureSpec,childHeightMeasureSpec);

            maxChildHeight = Math.max(maxChildHeight,child.getMeasuredHeight());
            maxChildWidth = Math.max(maxChildWidth,child.getMeasuredWidth());
        }

        //measure self
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int hopeWidthSize = 0;
        int hopeHeightSize = 0;

        switch (widthMode) {
            case MeasureSpec.EXACTLY: {
                hopeWidthSize = widthSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                hopeWidthSize = Math.min(widthSize,heightSize);
                break;
            }
            case MeasureSpec.UNSPECIFIED: {
                hopeWidthSize = maxChildWidth * 3;
                break;
            }
        }

        switch (heightMode) {
            case MeasureSpec.EXACTLY: {
                hopeHeightSize = heightSize;
                break;
            }
            case MeasureSpec.AT_MOST: {
                hopeHeightSize = Math.min(widthSize,heightSize);
                break;
            }
            case MeasureSpec.UNSPECIFIED: {
                hopeHeightSize = maxChildHeight * 3;
                break;
            }
        }

        //decide to what want
        setMeasuredDimension(resolveSize(hopeWidthSize,widthMeasureSpec),
                resolveSize(hopeHeightSize,heightMeasureSpec));
    }

    // Sizes of the ViewGroup
    private int circleWidth, circleHeight;
    private float radius = 0;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int layoutWidth = r - l;
        int layoutHeight = b - t;

        radius = (layoutWidth <= layoutHeight) ? layoutWidth / 3
                : layoutHeight / 3;

        circleHeight = getHeight();
        circleWidth = getWidth();
        setChildAngles();
    }

    private float angle = 90;
    private void setChildAngles() {
        int left, top, childWidth, childHeight, childCount = getChildCount();
        float angleDelay = 360.0f / childCount;
        float halfAngle = angleDelay / 2;
        float localAngle = angle;

        //遍历所有孩子
        for (int i=0;i<childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE) {
                continue;
            }

            if (localAngle > 360) {
                localAngle -= 360;
            } else if (localAngle < 0) {
                localAngle += 360;
            }

            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            left = Math.round((float) (((circleWidth / 2.0) - childWidth / 2.0) + radius
                            * Math.cos(Math.toRadians(localAngle))));
            top = Math
                    .round((float) (((circleHeight / 2.0) - childHeight / 2.0) + radius
                            * Math.sin(Math.toRadians(localAngle))));

            child.layout(left, top, left + childWidth, top + childHeight);
            localAngle += angleDelay;
        }
    }
}
