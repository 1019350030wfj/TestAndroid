package com.jayden.testandroid.video;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayden.testandroid.R;
import com.jayden.testandroid.video.items.VideoListItem;
import com.squareup.picasso.Picasso;
import com.volokh.danylo.video_player_manager.meta.CurrentItemMetaData;
import com.volokh.danylo.video_player_manager.ui.MediaPlayerWrapper;
import com.volokh.danylo.video_player_manager.ui.VideoPlayerView;

import java.util.List;

/**
 * Created by Jayden on 2016/5/11.
 * Email : 1570713698@qq.com
 */
public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.VideoViewHolder> {

    private final List<VideoListItem> mList;//视频项列表

    //构造器
    public VideoListAdapter(List<VideoListItem> list) {
        this.mList = list;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);

        //必须设置tag，否则无法显示
        VideoViewHolder holder = new VideoViewHolder(view);
        view.setTag(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        VideoListItem videoListItem = mList.get(position);
        holder.bindTo(videoListItem,position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder {

        private VideoPlayerView mVpvPlayer;//播放控件
        private ImageView mIvCover; //覆盖层
        private TextView mTvTitle; //标题
        private TextView mTvPercents; //百分比

        private Context mContext;
        private MediaPlayerWrapper.MainThreadMediaPlayerListener mPlayerListener;

        public VideoViewHolder(View itemView) {
            super(itemView);
            mVpvPlayer = (VideoPlayerView) itemView.findViewById(R.id.item_video_vpv_player);
            mIvCover = (ImageView) itemView.findViewById(R.id.item_video_iv_cover);
            mTvTitle = (TextView) itemView.findViewById(R.id.item_video_tv_title);
            mTvPercents = (TextView) itemView.findViewById(R.id.item_video_tv_percents);

            mContext = itemView.getContext().getApplicationContext();

            mPlayerListener = new MediaPlayerWrapper.MainThreadMediaPlayerListener() {
                @Override
                public void onVideoSizeChangedMainThread(int width, int height) {

                }

                @Override
                public void onVideoPreparedMainThread() {
                    // 视频播放隐藏前图
                    mIvCover.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onVideoCompletionMainThread() {

                }

                @Override
                public void onErrorMainThread(int what, int extra) {

                }

                @Override
                public void onBufferingUpdateMainThread(int percent) {

                }

                @Override
                public void onVideoStoppedMainThread() {
                    // 视频暂停显示前图
                    mIvCover.setVisibility(View.VISIBLE);
                }
            };
            mVpvPlayer.addMediaPlayerListener(mPlayerListener);
        }

        public void bindTo(final VideoListItem item, final int position) {
            //点击item项，然后播放
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO item.getVideoResource() 记得根据type，看是本地还是网络
                    item.getVideoPlayerManager().playNewVideo(new CurrentItemMetaData(position, itemView),
                            mVpvPlayer, (AssetFileDescriptor) item.getVideoResource());
                }
            });
            mTvTitle.setText(item.getTitle());
            mIvCover.setVisibility(View.VISIBLE);
            Picasso.with(mContext).load(item.getImageResource()).into(mIvCover);
        }

        // 返回播放器
        public VideoPlayerView getVpvPlayer() {
            return mVpvPlayer;
        }

        // 返回百分比
        public TextView getTvPercents() {
            return mTvPercents;
        }
    }
}
