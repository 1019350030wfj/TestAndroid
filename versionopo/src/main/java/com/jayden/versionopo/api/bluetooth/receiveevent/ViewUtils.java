package com.jayden.versionopo.api.bluetooth.receiveevent;

import android.view.ViewConfiguration;


/**
 * Created by Jayden on 2017/2/14.
 */

public class ViewUtils {


    /**
     * View 是否长按
     * @param lastX
     * @param lastY
     * @param thisX
     * @param thisY
     * @param lastDownTime
     * @param thisEventTime
     * @return
     */
    public static boolean isLongPressed(float lastX, float lastY, float thisX,
                                  float thisY, long lastDownTime, long thisEventTime) {
        float offsetX = Math.abs(thisX - lastX);
        float offsetY = Math.abs(thisY - lastY);
        long intervalTime = thisEventTime - lastDownTime;
        if (offsetX <= 10 && offsetY <= 10 && intervalTime >= ViewConfiguration.getLongPressTimeout()) {
            return true;
        }
        return false;
    }
}
