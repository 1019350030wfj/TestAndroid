package com.jayden.versionopo.api.wallpaper;

import android.app.WallpaperManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import java.io.IOException;

/**
 * Created by Jayden on 2017/5/22.
 */

public class CameraWallpaperService extends WallpaperService {

    @Override
    public Engine onCreateEngine() {
//        return new CameraEngine();
        return new VideoEngine();
    }

    class CameraEngine extends Engine implements Camera.PreviewCallback {
        private Camera mCamera;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            startPreview();
            setTouchEventsEnabled(true);
        }

        @Override
        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            stopPreview();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                startPreview();
            } else {
                stopPreview();
            }
        }

        public void startPreview() {
            if (mCamera == null) {
                Log.i("aaa", "wallpager startPreview " + System.currentTimeMillis());

                try {
                    mCamera = Camera.open(0);
                    if (mCamera != null) {
                        mCamera.setDisplayOrientation(90);
                        mCamera.setPreviewDisplay(getSurfaceHolder());

                        mCamera.startPreview();
                    }
                } catch (Exception e) {
                    Log.i("aaa", "wallpager " + e.getMessage());
                }
            }

        }

        public void stopPreview() {
            if (mCamera != null) {
                try {
                    mCamera.stopPreview();
                    mCamera.setPreviewCallback(null);

                } catch (Exception e) {
                    Log.i("aaa", "Exception " + System.currentTimeMillis());
                } finally {
                    mCamera.release();
                    mCamera = null;
                }
            }
        }


        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
            mCamera.addCallbackBuffer(data);
        }
    }

    public static final String VIDEO_PARAMS_CONTROL_ACTION = "com.zhy.livewallpaper";
    public static final String KEY_ACTION = "action";
    public static final int ACTION_VOICE_SILENCE = 110;
    public static final int ACTION_VOICE_NORMAL = 111;

    public static void voiceSilence(Context context) {
        Intent intent = new Intent(CameraWallpaperService.VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(CameraWallpaperService.KEY_ACTION, CameraWallpaperService.ACTION_VOICE_SILENCE);
        context.sendBroadcast(intent);
    }

    public static void voiceNormal(Context context) {
        Intent intent = new Intent(CameraWallpaperService.VIDEO_PARAMS_CONTROL_ACTION);
        intent.putExtra(CameraWallpaperService.KEY_ACTION, CameraWallpaperService.ACTION_VOICE_NORMAL);
        context.sendBroadcast(intent);
    }

    public static void setToWallPaper(Context context) {
        final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(context, CameraWallpaperService.class));
        context.startActivity(intent);
    }

    class VideoEngine extends Engine {

        private MediaPlayer mMediaPlayer;

        private BroadcastReceiver mVideoParamsControlReceiver;

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);

            IntentFilter intentFilter = new IntentFilter(VIDEO_PARAMS_CONTROL_ACTION);
            registerReceiver(mVideoParamsControlReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    int action = intent.getIntExtra(KEY_ACTION, -1);

                    switch (action) {
                        case ACTION_VOICE_NORMAL:
                            mMediaPlayer.setVolume(1.0f, 1.0f);
                            break;
                        case ACTION_VOICE_SILENCE:
                            mMediaPlayer.setVolume(0, 0);
                            break;

                    }
                }
            }, intentFilter);


        }

        @Override
        public void onDestroy() {
            unregisterReceiver(mVideoParamsControlReceiver);
            super.onDestroy();

        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                mMediaPlayer.start();
            } else {
                mMediaPlayer.pause();
            }
        }


        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setSurface(holder.getSurface());
            try {
                AssetManager assetMg = getApplicationContext().getAssets();
                AssetFileDescriptor fileDescriptor = assetMg.openFd("test1.mp4");
                mMediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),
                        fileDescriptor.getStartOffset(), fileDescriptor.getLength());
                mMediaPlayer.setLooping(true);
                mMediaPlayer.setVolume(0, 0);
                mMediaPlayer.prepare();
                mMediaPlayer.start();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            mMediaPlayer.release();
            mMediaPlayer = null;

        }
    }
}
