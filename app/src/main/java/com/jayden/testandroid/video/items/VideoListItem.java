package com.jayden.testandroid.video.items;

import android.graphics.Rect;
import android.support.annotation.DrawableRes;
import android.view.View;

import com.jayden.testandroid.video.VideoListAdapter;
import com.volokh.danylo.video_player_manager.manager.VideoItem;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.CurrentItemMetaData;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.items.ListItem;

/**
 * 基本视频项, 实现适配项和列表项
 * Created by Jayden on 2016/5/11.
 * Email : 1570713698@qq.com
 */
public abstract class VideoListItem implements VideoItem,ListItem {

    private final Rect mCurrentViewRect; //当前视图的方框
    private final VideoPlayerManager<MetaData> mVideoPlayerManager;//视频播放管理器
    private final String mTitle; //标题

    @DrawableRes
    private final int mImageResource; //图片资源

    // 构造器, 输入视频播放管理器
    public VideoListItem(
            VideoPlayerManager<MetaData> videoPlayerManager,
            String title,
            @DrawableRes int imageResource) {
        mVideoPlayerManager = videoPlayerManager;
        mTitle = title;
        mImageResource = imageResource;

        mCurrentViewRect = new Rect();
    }

    // 显示百分比
    private void setVisibilityPercentsText(View currentView, int percents) {
        VideoListAdapter.VideoViewHolder vh =
                (VideoListAdapter.VideoViewHolder) currentView.getTag();
        String percentsText = "可视百分比: " + String.valueOf(percents);
        vh.getTvPercents().setText(percentsText);
    }

    // 显示可视的百分比程度
    public int getVisibilityPercents(View view) {
        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);
        int height = view.getHeight();

        if (viewIsPartiallyHiddenTop()) {
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if (viewIsPartiallyHiddenBottom(height)) {
            percents = mCurrentViewRect.bottom * 100 / height;
        }

        // 设置百分比
        setVisibilityPercentsText(view, percents);

        return percents;
    }

    @Override public void setActive(View newActiveView, int newActiveViewPosition) {
        VideoListAdapter.VideoViewHolder viewHolder =
                (VideoListAdapter.VideoViewHolder) newActiveView.getTag();
        playNewVideo(new CurrentItemMetaData(newActiveViewPosition, newActiveView),
                viewHolder.getVpvPlayer(), mVideoPlayerManager);
    }

    @Override public void deactivate(View currentView, int position) {
        stopPlayback(mVideoPlayerManager);
    }

    @Override public void stopPlayback(VideoPlayerManager videoPlayerManager) {
        videoPlayerManager.stopAnyPlayback();
    }

    // 顶部出现
    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }

    // 底部出现
    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    // 视频项的标题
    public String getTitle() {
        return mTitle;
    }

    // 视频项的背景图片
    public int getImageResource() {
        return mImageResource;
    }

    public abstract Object getVideoResource();

    public VideoPlayerManager<MetaData> getVideoPlayerManager() {
        return mVideoPlayerManager;
    }
}
