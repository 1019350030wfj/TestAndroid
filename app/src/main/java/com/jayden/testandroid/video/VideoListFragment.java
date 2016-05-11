package com.jayden.testandroid.video;

import android.content.res.AssetFileDescriptor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.jayden.testandroid.R;
import com.jayden.testandroid.video.items.LocalVideoListItem;
import com.jayden.testandroid.video.items.OnlineVideoListItem;
import com.jayden.testandroid.video.items.VideoListItem;
import com.volokh.danylo.video_player_manager.manager.PlayerItemChangeListener;
import com.volokh.danylo.video_player_manager.manager.SingleVideoPlayerManager;
import com.volokh.danylo.video_player_manager.manager.VideoPlayerManager;
import com.volokh.danylo.video_player_manager.meta.MetaData;
import com.volokh.danylo.visibility_utils.calculator.DefaultSingleItemCalculatorCallback;
import com.volokh.danylo.visibility_utils.calculator.ListItemsVisibilityCalculator;
import com.volokh.danylo.visibility_utils.calculator.SingleListViewItemActiveCalculator;
import com.volokh.danylo.visibility_utils.scroll_utils.ItemsPositionGetter;
import com.volokh.danylo.visibility_utils.scroll_utils.RecyclerViewItemPositionGetter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Jayden on 2016/5/11.
 * Email : 1570713698@qq.com
 */
public class VideoListFragment extends Fragment {

    public static final String VIDEO_TYPE_ARG = "me.chunyu.spike.video_list_fragment.video_type_arg";
    public static final int LOCAL = 0; // 本地
    public static final int ONLINE = 1; // 在线

    //网络视频地址
    private static final String URL =
            "http://dn-chunyu.qbox.me/fwb/static/images/home/video/video_aboutCY_A.mp4";

    //本地资源文件名
    private static final String[] LOCAL_NAMES = new String[]{
            "chunyu-local-1.mp4",
            "chunyu-local-2.mp4",
            "chunyu-local-3.mp4",
            "chunyu-local-4.mp4"
    };

    public static VideoListFragment newInstance(int type) {
        VideoListFragment videoListFragment = new VideoListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(VIDEO_TYPE_ARG,type);
        videoListFragment.setArguments(bundle);
        return videoListFragment;
    }

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;//布局管理器
    private ItemsPositionGetter mItemsPositionGetter;//位置提取器
    private int mScrollState;//滑动状态

    private final ArrayList<VideoListItem> mList; //视频项的列表
    private final ListItemsVisibilityCalculator mVisibilityCalculator;//可视估计器
    private final VideoPlayerManager<MetaData> mVideoPlayerManager;

    //构造器
    public VideoListFragment() {
        mList = new ArrayList<>();
        mVisibilityCalculator = new SingleListViewItemActiveCalculator(
                new DefaultSingleItemCalculatorCallback(),mList);
        mVideoPlayerManager = new SingleVideoPlayerManager(new PlayerItemChangeListener() {
            @Override
            public void onPlayerItemChanged(MetaData currentItemMetaData) {

            }
        });
        mScrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE;//暂停滚动状态
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_video_list,container,false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            //设置类型
            if (args.getInt(VIDEO_TYPE_ARG) == LOCAL) {
                initLocalVideoList();
            } else {
                initOnlineVideoList();
            }
        } else {
            initLocalVideoList();
        }

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        VideoListAdapter adapter = new VideoListAdapter(mList);
        mRecyclerView.setAdapter(adapter);

        //获取item的位置
        mItemsPositionGetter = new RecyclerViewItemPositionGetter(mLayoutManager,mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mScrollState = newState;
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !mList.isEmpty()) {
                    mVisibilityCalculator.onScrollStateIdle(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition()
                    );
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (!mList.isEmpty()) {
                    mVisibilityCalculator.onScroll(
                            mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition() -
                                    mLayoutManager.findFirstVisibleItemPosition() + 1,
                            mScrollState
                    );
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mList.isEmpty()) {
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    //判断一些滚动状态
                    mVisibilityCalculator.onScrollStateIdle(mItemsPositionGetter,
                            mLayoutManager.findFirstVisibleItemPosition(),
                            mLayoutManager.findLastVisibleItemPosition());
                }
            });
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //页面不显示时，释放播放器
        mVideoPlayerManager.resetMediaPlayer();
    }

    // 初始化本地视频
    private void initLocalVideoList() {
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[0], R.drawable.cover, getFile(LOCAL_NAMES[0])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[1], R.drawable.cover, getFile(LOCAL_NAMES[1])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[2], R.drawable.cover, getFile(LOCAL_NAMES[2])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[3], R.drawable.cover, getFile(LOCAL_NAMES[3])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[0], R.drawable.cover, getFile(LOCAL_NAMES[0])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[1], R.drawable.cover, getFile(LOCAL_NAMES[1])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[2], R.drawable.cover, getFile(LOCAL_NAMES[2])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[3], R.drawable.cover, getFile(LOCAL_NAMES[3])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[0], R.drawable.cover, getFile(LOCAL_NAMES[0])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[1], R.drawable.cover, getFile(LOCAL_NAMES[1])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[2], R.drawable.cover, getFile(LOCAL_NAMES[2])));
        mList.add(new LocalVideoListItem(mVideoPlayerManager, LOCAL_NAMES[3], R.drawable.cover, getFile(LOCAL_NAMES[3])));
    }

    // 获取assets文件夹下的资源文件
    private AssetFileDescriptor getFile(String localName) {
        try {
            return getActivity().getAssets().openFd(localName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 在线资源名
    private static final String ONLINE_NAME = "chunyu-online";

    // 初始化在线视频, 需要缓冲
    private void initOnlineVideoList() {
        final int count = 10;
        for (int i = 0; i < count; ++i) {
            mList.add(new OnlineVideoListItem(mVideoPlayerManager, ONLINE_NAME, R.drawable.cover, URL));
        }
    }
}
