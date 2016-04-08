package com.jayden.testandroid.framework.ui.xhsguide.activity.fragment.base;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.jayden.testandroid.utils.ConvertUtils;
import com.jayden.testandroid.utils.DvAppUtil;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * Created by Jayden on 2016/4/7.
 * Email : 1570713698@qq.com
 */
public class LoginAnimImageBaseFragment extends Fragment {

    public static final int BITMAP_SCROLL = 11;
    public static final int BITMAP_SHIELD = 12;

    public float mAnimStartY = -1;
    public int mScrollBitmapHeight = 0;
    public int mNewScrollBitmapHeight= 0;
    public int mImageViewWidth = 0;

    public AnimatorSet mAnimatorSet;
    public ObjectAnimator mObjectAnimator;

    float mScaleWidth = 0;
    float mScaleHeight = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int parentViewWidth = (int) (DvAppUtil.getDisplayMetrics(getActivity()).widthPixels - ConvertUtils.dipToPx(getActivity(),40));
        mImageViewWidth = (int) (parentViewWidth - ConvertUtils.dipToPx(getActivity(),60));
    }

    //对bitmap进行缩放
    public Bitmap bitmapScale(int ivWidth,Bitmap bitmap,int type) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = ivWidth;
        int newHeight = (int) ((float)height) * newWidth / width;

        if(type == BITMAP_SCROLL){
            mNewScrollBitmapHeight = newHeight;
        }

        mScaleWidth = ((float) newWidth) / width;
        mScaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(mScaleWidth, mScaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,height, matrix, true);
        return resizedBitmap;
    }

    public Bitmap bitmapScale(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(mScaleWidth, mScaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width,height, matrix, true);
        return resizedBitmap;
    }

    public void playInAnim(){}
    public void playOutAnim(){}
    public void reset(){}
}
