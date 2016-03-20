package com.jayden.testandroid.customview.itemtouchhelper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.jayden.testandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/3/18.
 */
public class ChannelActivity extends AppCompatActivity {

    private RecyclerView mRecy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_itemtouchhelper);

        mRecy = (RecyclerView) findViewById(R.id.recy);
        init();
    }

    private void init() {
        List<ChannelEntity> channelEntities = new ArrayList<>();
        for (int i=0;i<18;i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setId(i);
            entity.setName("频道"+i);
            channelEntities.add(entity);
        }

        List<ChannelEntity> otherChannels = new ArrayList<>();
        for (int i=0;i<20;i++) {
            ChannelEntity entity = new ChannelEntity();
            entity.setId(i);
            entity.setName("其它" + i);
            otherChannels.add(entity);
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        mRecy.setLayoutManager(layoutManager);

        //拖拽
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {

            //可以指定需要拖拽的方向
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

                int dragFlags;
                RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
                if (manager instanceof GridLayoutManager || manager instanceof StaggeredGridLayoutManager) {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                } else {
                    dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                }
                // 如果想支持滑动(删除)操作, swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END
                int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // 不同Type之间不可移动
                if (viewHolder.getItemViewType() != target.getItemViewType()) {
                    return false;
                }

                if (recyclerView.getAdapter() instanceof OnItemMoveListener) {
                    OnItemMoveListener listener = ((OnItemMoveListener) recyclerView.getAdapter());
                    listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                }
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public boolean isLongPressDragEnabled() {
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                return true;
            }

        });
        touchHelper.attachToRecyclerView(mRecy);

        final ChannelAdapter adapter = new ChannelAdapter(this,touchHelper,channelEntities,otherChannels);
        mRecy.setAdapter(adapter);

        //可以动态改变gridLayoutManager的列数
        //span size表示一个item的跨度，跨度了多少个span
        //比如我们现在的Span是4， 每一个span size就是 1
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = adapter.getItemViewType(position);
                return type == ChannelAdapter.MY_CHANNEL_HEADER || type == ChannelAdapter.OTHER_CHANNEL_HEADER ? 4 : 1;
            }
        });
    }
}
