package com.jayden.versionopo.api.bluetooth.receiveevent;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebView;

import com.jayden.versionopo.R;

/**
 * 获取以配对的蓝牙设备，然后判断特定蓝牙设备是否配对且连接
 * 没有的话，提示去配对和连接
 * Created by Jayden on 2017/4/20.
 */
public class MainActivity extends Activity {


    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_receive_event);
        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("http:www.baidu.com");
//        mWebView.loadUrl("http://192.168.3.157:8000/100vr/");

    }
    public boolean isDpadDevice(InputEvent event) {
        // Check that input comes from a device with directional pads.
        if ((event.getSource() & InputDevice.SOURCE_DPAD)
                != InputDevice.SOURCE_DPAD) {
            return true;
        } else {
            return false;
        }
    }
    public boolean isSOURCE_KEYBOARDDevice(InputEvent event) {
        // Check that input comes from a device with directional pads.
        if ((event.getSource() & InputDevice.SOURCE_KEYBOARD)
                != InputDevice.SOURCE_KEYBOARD) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isJosytickDevice(InputEvent event) {
        // Check that input comes from a device with directional pads.
        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) ==
                InputDevice.SOURCE_JOYSTICK) {
            return true;
        } else {
            return false;
        }
    }

    private void processJoystickInput(MotionEvent event,
                                      int historyPos) {

        InputDevice mInputDevice = event.getDevice();

        // Calculate the horizontal distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat axis, or the right control stick.
        float x = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_X, historyPos);
        if (x == 0) {
            x = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_HAT_X, historyPos);
        }
        if (x == 0) {
            x = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_Z, historyPos);
        }

        // Calculate the vertical distance to move by
        // using the input value from one of these physical controls:
        // the left control stick, hat switch, or the right control stick.
        float y = getCenteredAxis(event, mInputDevice,
                MotionEvent.AXIS_Y, historyPos);
        if (y == 0) {
            y = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_HAT_Y, historyPos);
        }
        if (y == 0) {
            y = getCenteredAxis(event, mInputDevice,
                    MotionEvent.AXIS_RZ, historyPos);
        }
        Log.e("jayden", "x = " +  x);
        Log.e("jayden", "y = " +  y);
        // Update the ship object based on the new x and y values
    }
    private static float getCenteredAxis(MotionEvent event,
                                         InputDevice device, int axis, int historyPos) {
        final InputDevice.MotionRange range =
                device.getMotionRange(axis, event.getSource());

        // A joystick at rest does not always report an absolute position of
        // (0,0). Use the getFlat() method to determine the range of values
        // bounding the joystick axis center.
        if (range != null) {
            final float flat = range.getFlat();
            final float value =
                    historyPos < 0 ? event.getAxisValue(axis):
                            event.getHistoricalAxisValue(axis, historyPos);

            // Ignore axis values that are within the 'flat' region of the
            // joystick axis center.
            if (Math.abs(value) > flat) {
                return value;
            }
        }
        return 0;
    }
    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {

//        Log.e("jayden", "isSOURCE_KEYBOARDDevice = " +  isSOURCE_KEYBOARDDevice(ev));
//        Log.e("jayden", "isDpadDevice = " +  isDpadDevice(ev));
//        Log.e("jayden", "isJosytickDevice = " +  isJosytickDevice(ev));
//        Log.e("jayden", " ev.getDevice() = " +  ev.getDevice().getName());
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_HAT_X) = " + ev.getAxisValue(MotionEvent.AXIS_HAT_X));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_HAT_Y) = " + ev.getAxisValue(MotionEvent.AXIS_HAT_Y));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_X) = " + ev.getAxisValue(MotionEvent.AXIS_X));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_Y) = " + ev.getAxisValue(MotionEvent.AXIS_Y));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_Z) = " + ev.getAxisValue(MotionEvent.AXIS_Z));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_RZ) = " + ev.getAxisValue(MotionEvent.AXIS_RZ));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_RX) = " + ev.getAxisValue(MotionEvent.AXIS_RX));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_RY) = " + ev.getAxisValue(MotionEvent.AXIS_RY));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_LTRIGGER) = " + ev.getAxisValue(MotionEvent.AXIS_LTRIGGER));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_RTRIGGER) = " + ev.getAxisValue(MotionEvent.AXIS_RTRIGGER));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_RUDDER) = " + ev.getAxisValue(MotionEvent.AXIS_RUDDER));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_RELATIVE_Y) = " + ev.getAxisValue(MotionEvent.AXIS_RELATIVE_Y));
//        Log.e("jayden", "event.getAxisValue(MotionEvent.AXIS_RELATIVE_X) = " + ev.getAxisValue(MotionEvent.AXIS_RELATIVE_X));
        // Check that the event came from a game controller
        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) ==
                InputDevice.SOURCE_JOYSTICK &&
                event.getAction() == MotionEvent.ACTION_MOVE) {

            // Process all historical movement samples in the batch
            final int historySize = event.getHistorySize();
            Log.e("jayden", "historySize = " + historySize);
            // Process the movements starting from the
            // earliest historical position in the batch
            for (int i = 0; i < historySize; i++) {
                // Process the event at historical position i
                Log.e("jayden", "i = " + i);
                processJoystickInput(event, i);
            }

            // Process the current movement sample in the batch (position -1)
            processJoystickInput(event, -1);
            return true;
        }
        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("jayden", "onKeyDown = " + keyCode);
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP: {//上
                Log.e("jayden", "Up = " + event.getAction());
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_DOWN: {//下
                Log.e("jayden", "down = " + event.getAction());
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_LEFT: {//左
                Log.e("jayden", "left = " + event.getAction());
                return true;
            }
            case KeyEvent.KEYCODE_DPAD_RIGHT: {//右
                Log.e("jayden", "right = " + event.getAction());
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
