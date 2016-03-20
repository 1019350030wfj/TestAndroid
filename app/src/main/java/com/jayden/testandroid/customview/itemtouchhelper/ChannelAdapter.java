package com.jayden.testandroid.customview.itemtouchhelper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayden.testandroid.R;

import java.util.List;

/**
 * Created by Jayden on 2016/3/18.
 */
public class ChannelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnItemMoveListener {

    public static final int MY_CHANNEL_HEADER = 0;
    public static final int MY_CHANNEL = 1;
    public static final int OTHER_CHANNEL_HEADER = 2;
    public static final int OTHER_CHANNEL = 3;

    // 我的频道之前的header数量  该demo中 即标题部分 为 1
    private static final int COUNT_PRE_MY_HEADER = 1;
    // 其他频道之前的header数量  该demo中 即标题部分 为 COUNT_PRE_MY_HEADER + 1
    private static final int COUNT_PRE_OTHER_HEADER = COUNT_PRE_MY_HEADER + 1;

    // 是否为 编辑 模式
    private boolean isEditMode;

    private List<ChannelEntity> mMyChannelItems, mOtherChannelItems;
    private LayoutInflater mInflater;
    private ItemTouchHelper mItemTouchHelper;

    public ChannelAdapter(Context context, ItemTouchHelper helper, List<ChannelEntity> mMyChannelItems, List<ChannelEntity> mOtherChannelItems) {
        this.mInflater = LayoutInflater.from(context);
        this.mItemTouchHelper = helper;
        this.mMyChannelItems = mMyChannelItems;
        this.mOtherChannelItems = mOtherChannelItems;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return MY_CHANNEL_HEADER;
        } else if (position > 0 && position < mMyChannelItems.size() + COUNT_PRE_MY_HEADER) {
            return MY_CHANNEL;
        } else if (position == mMyChannelItems.size() + COUNT_PRE_MY_HEADER) {
            return OTHER_CHANNEL_HEADER;
        } else {
            return OTHER_CHANNEL;
        }
    }

    // touch 点击开始时间
    private long startTime;
    // touch 间隔时间  用于分辨是否是 "点击"
    private static final long SPACE_TIME = 100;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        final View view;
        switch (viewType) {
            case MY_CHANNEL_HEADER: {
                view = mInflater.inflate(R.layout.item_my_channel_header, parent, false);
                MyChannelHeaderViewHolder holder = new MyChannelHeaderViewHolder(view);
                final TextView textView = (TextView) view.findViewById(R.id.tv_btn_edit);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isEditMode) {
                            textView.setText("完成");
                            startEditMode((RecyclerView) parent);
                        } else {
                            textView.setText("编辑");
                            cancelEditMode((RecyclerView) parent);
                        }
                    }
                });
                return holder;
            }
            case MY_CHANNEL: {
                view = mInflater.inflate(R.layout.item_my, parent, false);
                final MyViewHolder myHolder = new MyViewHolder(view);
                myHolder.textView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (isEditMode) {
                            switch (event.getAction()) {
                                case MotionEvent.ACTION_DOWN: {
                                    startTime = System.currentTimeMillis();
                                    break;
                                }
                                case MotionEvent.ACTION_CANCEL:
                                case MotionEvent.ACTION_UP: {
                                    startTime = 0;
                                    break;
                                }
                                case MotionEvent.ACTION_MOVE: {
                                    if (System.currentTimeMillis() - startTime > SPACE_TIME) {
                                        mItemTouchHelper.startDrag(myHolder);
                                    }
                                    break;
                                }
                            }
                        }

                        return false;
                    }
                });
                myHolder.textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (!isEditMode) {
                            RecyclerView recyclerView = (RecyclerView) parent;
                            startEditMode(recyclerView);

                            View view1 = recyclerView.getChildAt(0);
                            if (view1 == recyclerView.getLayoutManager().findViewByPosition(0)) {
                                TextView textView = (TextView) view1.findViewById(R.id.tv_btn_edit);
                                textView.setText("完成");
                            }
                        }
                        mItemTouchHelper.startDrag(myHolder);
                        return true;
                    }
                });
                return myHolder;
            }
            case OTHER_CHANNEL_HEADER: {
                //不变的样式，只要使用默认的RecyclerView.ViewHolder
                view = mInflater.inflate(R.layout.item_other_channel_header, parent, false);
                return new RecyclerView.ViewHolder(view) {
                };
            }
            case OTHER_CHANNEL: {
                view = mInflater.inflate(R.layout.item_other, parent, false);
                OtherViewHolder otherHolder = new OtherViewHolder(view);
                return otherHolder;
            }
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyChannelHeaderViewHolder) {
            MyChannelHeaderViewHolder headerHolder = (MyChannelHeaderViewHolder) holder;
            if (isEditMode) {
                headerHolder.tvBtnEdit.setText(R.string.finish);
            } else {
                headerHolder.tvBtnEdit.setText(R.string.edit);
            }

        } else if (holder instanceof MyViewHolder) {
            MyViewHolder myHolder = (MyViewHolder) holder;
            myHolder.textView.setText(mMyChannelItems.get(position - COUNT_PRE_MY_HEADER).getName());
            if (isEditMode) {
                myHolder.imgEdit.setVisibility(View.VISIBLE);
            } else {
                myHolder.imgEdit.setVisibility(View.INVISIBLE);
            }
        } else if (holder instanceof OtherViewHolder) {
            ((OtherViewHolder) holder).textView.setText(mOtherChannelItems.get(position - mMyChannelItems.size() - COUNT_PRE_OTHER_HEADER).getName());
        }
    }

    @Override
    public int getItemCount() {
        // 我的频道  标题 + 我的频道.size + 其他频道 标题 + 其他频道.size
        return mMyChannelItems.size() + mOtherChannelItems.size() + COUNT_PRE_OTHER_HEADER;
    }


    /**
     * 开启编辑模式
     *
     * @param parent
     */
    private void startEditMode(RecyclerView parent) {
        isEditMode = true;

        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            if (imgEdit != null) {
                imgEdit.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 完成编辑模式
     *
     * @param parent
     */
    private void cancelEditMode(RecyclerView parent) {
        isEditMode = false;

        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            if (imgEdit != null) {
                imgEdit.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        ChannelEntity item = mMyChannelItems.get(fromPosition - COUNT_PRE_MY_HEADER);
        mMyChannelItems.remove(fromPosition - COUNT_PRE_MY_HEADER);
        mMyChannelItems.add(toPosition - COUNT_PRE_MY_HEADER, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    /**
     * 我的频道  标题部分
     */
    class MyChannelHeaderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBtnEdit;

        public MyChannelHeaderViewHolder(View itemView) {
            super(itemView);
            tvBtnEdit = (TextView) itemView.findViewById(R.id.tv_btn_edit);
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;
        private ImageView imgEdit;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
            imgEdit = (ImageView) itemView.findViewById(R.id.img_edit);
        }
    }

    /**
     * 其他频道
     */
    class OtherViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public OtherViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
