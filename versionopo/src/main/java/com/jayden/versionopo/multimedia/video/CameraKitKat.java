package com.jayden.versionopo.multimedia.video;

import android.content.Context;
import android.hardware.Camera;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.FrameLayout;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Administrator on 2017/12/11.
 */
public class CameraKitKat implements ACamera {

    private static final String TAG = "CameraKitKat";
    private Camera mCamera;
    private SurfaceHolder holder;
    private float displayScale;
    private SurfaceView displayView;

    private float radio = 1.0f;//相机预览大小和预览视图的比例
    private Handler mARHandler;

    private  Camera.CameraInfo cameraInfo;

    public CameraKitKat(SurfaceView surfaceView, Handler handler) {
        this.displayView = surfaceView;
        holder = displayView.getHolder();
        this.mARHandler = handler;
    }

    @Override
    public void open(int type) {
        if (!openCamera(type)) return;
        cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(type, cameraInfo);

        setParameters(mCamera);
        setDisplayOrientation();
        setPreviewDisplay(mCamera, holder);
        mCamera.startPreview();
    }

    @Override
    public void close() {
        mCamera.stopPreview();
        mCamera.release();
    }

    //调整SurfaceView的大小
    private void resizeDisplayView() {
        Camera.Parameters parameters = mCamera.getParameters();
        Camera.Size size = parameters.getPreviewSize();
        FrameLayout.LayoutParams p = (FrameLayout.LayoutParams) displayView.getLayoutParams();
        float scale = size.width / (float) size.height;
        displayScale = displayView.getHeight() / (float) displayView.getWidth();
        if (scale > displayScale) {
            p.height = (int) (scale * displayView.getWidth());
            p.width = displayView.getWidth();
        } else {
            p.width = (int) (displayView.getHeight() / scale);
            p.height = displayView.getHeight();
        }
        displayView.setLayoutParams(p);
        displayView.invalidate();
    }

    //调整预览view的大小,避免预览图像变形
    public void resizeDisplayView(Camera.Size size, int hopeWidth, int hopeHeight){
        int surfaceViewWidth = size.height;//surfaceView 的宽度
        int surfaceViewHeight = size.width;//surfaceView的高度

        if (surfaceViewWidth < hopeWidth && surfaceViewHeight < hopeHeight) {
            //两个都小，取比率较大
            float radioWidth = (float) hopeWidth / surfaceViewWidth;
            float radioHeight = (float) hopeHeight / surfaceViewHeight;
            if (radioWidth >= radioHeight) {//宽的比例较大
                surfaceViewWidth = hopeWidth;
                surfaceViewHeight *= radioWidth;
                radio = radioWidth;
            } else {//高的比例较大
                surfaceViewHeight = hopeHeight;
                surfaceViewWidth *= radioHeight;
                radio = radioHeight;
            }
        } else if (surfaceViewWidth < hopeWidth) {//宽比较小
            float radioWidth = (float) hopeWidth / surfaceViewWidth;
            surfaceViewWidth = hopeWidth;
            surfaceViewHeight *= radioWidth;
            radio = radioWidth;
        } else if (surfaceViewHeight < hopeHeight) {//高比较小
            float radioHeight = (float) hopeHeight / surfaceViewHeight;
            surfaceViewWidth *= radioHeight;
            surfaceViewHeight = hopeHeight;
            radio = radioHeight;
        }
        FrameLayout.LayoutParams layoutParams = new
                FrameLayout.LayoutParams(surfaceViewWidth, surfaceViewHeight);
        displayView.setLayoutParams(layoutParams);
    }

    private boolean checkCameraId(int cameraId) {
        return cameraId >= 0 && cameraId < Camera.getNumberOfCameras();
    }

    //相机使用第一步，打开相机，获得相机实例
    private boolean openCamera(int cameraId) {
        if (!checkCameraId(cameraId)) return false;
        mCamera = Camera.open(cameraId);
        return true;
    }

    //相机使用第二步，设置相机实例参数 处理投标项目事情
    //TODO :里面还存在问题，需要修改
    private void setParameters(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();

        //PreviewSize设置为设备支持的最高分辨率
        final Camera.Size size = Collections.max(camera.getParameters().getSupportedPreviewSizes(), new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                return lhs.width * lhs.height - rhs.width * rhs.height;
            }
        });
        parameters.setPreviewSize(size.width, size.height);

        //PictureSize设置为和预览大小最近的
        Camera.Size picSize = Collections.max(parameters.getSupportedPictureSizes(), new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                return (int) (Math.sqrt(Math.pow(size.width - rhs.width, 2) + Math.pow(size.height - rhs.height, 2)) -
                        Math.sqrt(Math.pow(size.width - lhs.width, 2) + Math.pow(size.height - lhs.height, 2)));
            }
        });
        parameters.setPictureSize(picSize.width, picSize.height);
        //如果相机支持自动聚焦，则设置相机自动聚焦，否则不设置
        if (parameters.getSupportedFocusModes().contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        }
        //设置颜色效果
//        parameters.setColorEffect(Camera.Parameters.EFFECT_MONO);

        camera.setParameters(parameters);
        resizeDisplayView();
    }

    public void setDisplayOrientation(){
        WindowManager manager = (WindowManager) displayView.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int displayRotation = display.getRotation();
        int cwRotationFromNaturalToDisplay;
        switch (displayRotation) {
            case Surface.ROTATION_0:
                cwRotationFromNaturalToDisplay = 0;
                break;
            case Surface.ROTATION_90:
                cwRotationFromNaturalToDisplay = 90;
                break;
            case Surface.ROTATION_180:
                cwRotationFromNaturalToDisplay = 180;
                break;
            case Surface.ROTATION_270:
                cwRotationFromNaturalToDisplay = 270;
                break;
            default:
                // Have seen this return incorrect values like -90
                if (displayRotation % 90 == 0) {
                    cwRotationFromNaturalToDisplay = (360 + displayRotation) % 360;
                } else {
                    throw new IllegalArgumentException("Bad rotation: " + displayRotation);
                }
        }
        int cwRotationFromNaturalToCamera = cameraInfo.orientation;
        // Still not 100% sure about this. But acts like we need to flip this:
//        if (camera.getFacing() == CameraFacing.FRONT) {
//            cwRotationFromNaturalToCamera = (360 - cwRotationFromNaturalToCamera) % 360;
//            Log.i(TAG, "Front camera overriden to: " + cwRotationFromNaturalToCamera);
//        }

//        cwRotationFromDisplayToCamera =
//                (360 + cwRotationFromNaturalToCamera - cwRotationFromNaturalToDisplay) % 360;
//        Log.i(TAG, "Final display orientation: " + cwRotationFromDisplayToCamera);
//        if (camera.getFacing() == CameraFacing.FRONT) {
//            Log.i(TAG, "Compensating rotation for front camera");
//            cwNeededRotation = (360 - cwRotationFromDisplayToCamera) % 360;
//        } else {
//            cwNeededRotation = cwRotationFromDisplayToCamera;
//        }
//        Log.i(TAG, "Clockwise rotation from display to camera: " + cwNeededRotation);
    }

    //相机使用第三步，设置相机预览方向
    public void setDisplayOrientation(Camera camera, int rotation) {
        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
            camera.setDisplayOrientation(90);
        } else {
            camera.setDisplayOrientation(0);
        }
    }

    //相机使用第四步，设置相机预览载体SurfaceHolder
    private void setPreviewDisplay(Camera camera, SurfaceHolder holder) {
        try {
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final long AUTO_FOCUS_INTERVAL_MS = 1000L;
    /**
     * 自动对焦回调
     */
    Camera.AutoFocusCallback autoFocusCallback = new Camera.AutoFocusCallback() {
        public void onAutoFocus(boolean success, Camera camera) {
            mARHandler.postDelayed(doAutoFocus, AUTO_FOCUS_INTERVAL_MS);
            focusing = success;
        }
    };
    private boolean focusing;

    public boolean getFocusing() {
        return focusing;
    }

    // 自动对焦
    private Runnable doAutoFocus = new Runnable() {
        public void run() {
            if (null == mCamera || null == autoFocusCallback) {
                return;
            }
            mCamera.autoFocus(autoFocusCallback);
            focusing = false;
        }
    };
}
