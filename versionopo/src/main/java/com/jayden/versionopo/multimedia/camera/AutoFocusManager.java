package com.jayden.versionopo.multimedia.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * 
 * Created by Administrator on 2017/12/12.
 */

public class AutoFocusManager {

    private Camera mCamera;
    private Handler mARHandler;

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
