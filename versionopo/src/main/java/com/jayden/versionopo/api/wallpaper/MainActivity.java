package com.jayden.versionopo.api.wallpaper;

import android.Manifest;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.jayden.versionopo.R;

/**
 * 1、在res文件夹下先建xml文件夹，并创建livewallpaper.xml文件
 * 2、在AndroidManifest.xml添加权限
 * 3、继承WallpaperService，这个是使桌面能够一直处于动态或者透明（相机摄像头采集的视频），后台服务
 * 4、在WallpaperService中，创建Engine（其内部类）， 实现动态壁纸或者透明壁纸，就是在这个Engine类的各种回调里面实现的（onCreate、onDestroy、onSurfaceViewCreate
 * onSurfaceViewChanged、onSurfaceViewDestroyed、onVisibilityChanged）。
 * 5、在AndroidManifes.xml注册WallpaperService
 *
 * Created by Jayden on 2017/5/22.
 */

public class MainActivity extends Activity {
    private CheckBox mCbVoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper);

        mCbVoice = (CheckBox) findViewById(R.id.id_cb_voice);

        mCbVoice.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(
                            CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            // 静音
                            CameraWallpaperService.voiceSilence(getApplicationContext());
                        } else {
                            CameraWallpaperService.voiceNormal(getApplicationContext());
                        }
                    }
                });
    }

    public void setCameraToWallPaper(View view) {
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},454);
        } else {
            startWallpaper();
        }
    }

    public void setVideoToWallPaper(View view) {
        CameraWallpaperService.setToWallPaper(this);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 454:{
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startWallpaper();
                } else {
                    Toast.makeText(MainActivity.this,"no camera permission",Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    private void startWallpaper() {
//        final Intent pickWallpaper = new Intent(Intent.ACTION_SET_WALLPAPER);
//        Intent chooser = Intent.createChooser(pickWallpaper, "设置壁纸");
//        startActivity(chooser);
        final Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(this, CameraWallpaperService.class));
        startActivity(intent);
    }
}
