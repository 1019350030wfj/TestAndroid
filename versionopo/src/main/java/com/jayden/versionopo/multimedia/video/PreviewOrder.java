package com.jayden.versionopo.multimedia.video;

import android.util.Log;

import java.util.SortedSet;

/**
 * 1、获取相机open(id)
 * 2、获取相机默认设置参数getParameters(), 也可以配置参数，比如 相机预览大小，格式，方向、对角方式、
 * 3、设置预览的view
 * 4、开启预览
 *
 * Created by Administrator on 2017/12/9.
 */

public class PreviewOrder {

    void adjustCameraParameters() {
//        SortedSet<Size> sizes = mPreviewSizes.sizes(mAspectRatio);
//        if (sizes == null) { // Not supported
//            mAspectRatio = chooseAspectRatio();
//            sizes = mPreviewSizes.sizes(mAspectRatio);
//        }
//        Size size = chooseOptimalSize(sizes);
//
//        // Always re-apply camera parameters
//        // Largest picture size in this ratio
//        final Size pictureSize = mPictureSizes.sizes(mAspectRatio).last();
//        if (mShowingPreview) {
//            mCamera.stopPreview();
//        }
//        mCameraParameters.setPreviewSize(size.getWidth(), size.getHeight());
//        mCameraParameters.setPictureSize(pictureSize.getWidth(), pictureSize.getHeight());
//        int rotation = calcCameraRotation(mDisplayOrientation);
//        Log.e("jayden","rotation = " + rotation);
//        Log.e("jayden","mDisplayOrientation = " + mDisplayOrientation);
//        mCameraParameters.setRotation(rotation);
//        setAutoFocusInternal(mAutoFocus);
//        setFlashInternal(mFlash);
//        mCamera.setParameters(mCameraParameters);
//        if (mShowingPreview) {
//            mCamera.startPreview();
//        }
    }
}
