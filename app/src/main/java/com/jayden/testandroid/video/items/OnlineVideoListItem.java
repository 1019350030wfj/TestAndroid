package com.jayden.testandroid.video.items;

import android.support.annotation.DrawableRes;

import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

/**
 * Created by Jayden on 2016/5/11.
 * Email : 1570713698@qq.com
 */
public class OnlineVideoListItem extends VideoListItem {
    private final String mOnlineUrl; // 资源文件描述

    public OnlineVideoListItem(
            VideoPlayerManager<MetaData> videoPlayerManager,
            String title,
            @DrawableRes int imageResource,
            String onlineUrl
    ) {
        super(videoPlayerManager, title, imageResource);

        mOnlineUrl = onlineUrl;
    }

    @Override
    public void playNewVideo(MetaData currentItemMetaData, VideoPlayerView player, VideoPlayerManager<MetaData> videoPlayerManager) {
        videoPlayerManager.playNewVideo(currentItemMetaData, player, mOnlineUrl);
    }

    @Override
    public String getVideoResource() {
        return mOnlineUrl;
    }
}
