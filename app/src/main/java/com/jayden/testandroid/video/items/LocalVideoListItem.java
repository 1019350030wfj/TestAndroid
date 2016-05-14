package com.jayden.testandroid.video.items;

import android.content.res.AssetFileDescriptor;
import android.support.annotation.DrawableRes;

import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

/**
 * 本地视频项
 * Created by Jayden on 2016/5/11.
 * Email : 1570713698@qq.com
 */
public class LocalVideoListItem extends VideoListItem {

    private final AssetFileDescriptor mAssetFileDescriptor; // 资源文件描述

    public LocalVideoListItem(
            VideoPlayerManager<MetaData> videoPlayerManager,
            String title,
            @DrawableRes int imageResource,
            AssetFileDescriptor assetFileDescriptor
    ) {
        super(videoPlayerManager, title, imageResource);
        mAssetFileDescriptor = assetFileDescriptor;
    }

    @Override
    public void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player,
                             VideoPlayerManager<MetaData> videoPlayerManager) {
        videoPlayerManager.playNewVideo(currentItemMetaData, player, mAssetFileDescriptor);
    }

    @Override
    public AssetFileDescriptor getVideoResource() {
        return mAssetFileDescriptor;
    }
}
