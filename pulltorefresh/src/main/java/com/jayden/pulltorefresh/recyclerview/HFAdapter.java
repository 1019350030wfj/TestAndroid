package com.jayden.pulltorefresh.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 外界使用，可以不考虑Header和Footer的位置，
 * 因为这里已经做了计算
 * Created by Jayden on 2016/4/11.
 * Email : 1570713698@qq.com
 */
public abstract class HFAdapter extends RecyclerView.Adapter {

    public static final int TYPE_MANAGER_OTHER = 0;
    public static final int TYPE_MANAGER_LINEAR = 1;
    public static final int TYPE_MANAGER_GRID = 2;
    public static final int TYPE_MANAGER_STAGGERED_GRID = 3;

    public static final int TYPE_HEADER = 7898;
    public static final int TYPE_FOOTER = 7899;

    private List<View> mHeaders = new ArrayList<>();
    private List<View> mFooters = new ArrayList<>();

    private int mManagerType;//布局管理器类型

    public int getHeadSize() {
        //列表头部添加几个的head
        return mHeaders.size();
    }

    public int getFoorSize() {
        //列表底部的view有几个
        return mFooters.size();
    }

    //获取布局管理器的类型
    public int getManagerType() {
        return mManagerType;
    }

    public void notifyDataSetChangeHF() {
        notifyDataSetChanged();
    }


    public void notifyItemChangedHF(int position) {
        notifyItemChanged(getRealPosition(position));
    }

    public void notifyItemRemovedHF(int position) {
        notifyItemRemoved(getRealPosition(position));
    }

    public void notifyItemRangeChangedHF(int positionStart, int itemCount) {
        notifyItemRangeChanged(getRealPosition(positionStart),itemCount);
    }

    public void notifyItemMovedHF(int fromPosition, int toPosition) {
        notifyItemMovedHF(getRealPosition(fromPosition),getRealPosition(toPosition));
    }

    public void notifyItemInsertedHF(int position) {
        notifyItemInserted(getRealPosition(position));
    }

    public void notifyItemRangeRemovedHF(int positionStart, int itemCount) {
        notifyItemRangeRemoved(getRealPosition(positionStart), itemCount);
    }

    public void notifyItemRangeInsertedHF(int positionStart, int itemCount) {
        notifyItemRangeInserted(getRealPosition(positionStart), itemCount);
    }

    /**
     * @param position 这个位置是有包含header  view的
     * @return 返回没有包含header view的位置， 也就是真实数据对应的item位置
     */
    public int getRealPosition(int position) {
        return position - mHeaders.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType != TYPE_FOOTER && viewType != TYPE_HEADER) {
            RecyclerView.ViewHolder vh = onCreateViewHolderHF(parent,viewType);
            return vh;
        } else {
            //有header view 或者 Footer view
            FrameLayout frameLayout = new FrameLayout(parent.getContext());
            //占满全屏
            frameLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
            ));
            return new HeaderFooterViewHolder(frameLayout);
        }
    }

    protected abstract ViewHolder onCreateViewHolderHF(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (isHeader(position)) {
            //头部View
            View v = mHeaders.get(position);
            prepareHeaderFooter((HeaderFooterViewHolder) holder,v);
        } else if(isFooter(position)) {
            //底部view
            View v = mFooters.get(position - getItemCountHF() - mHeaders.size());
            prepareHeaderFooter((HeaderFooterViewHolder)holder,v);
        } else {
            //item 项
            holder.itemView.setOnClickListener(new MyOnClickListener(holder));
            holder.itemView.setOnLongClickListener(new MyOnLongClickListener(holder));
            onBindViewHolderHF(holder,getRealPosition(position));
        }
    }

    protected abstract void onBindViewHolderHF(ViewHolder holder, int realPosition);

    private void prepareHeaderFooter(HeaderFooterViewHolder vh, View view) {
        if (mManagerType == TYPE_MANAGER_STAGGERED_GRID) {
            StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            );
            layoutParams.setFullSpan(true);
            vh.itemView.setLayoutParams(layoutParams);
        }

        //如果这个view已经属于另外的布局了，删除它
        if (view.getParent() != null) {
            ((ViewGroup) view.getParent()).removeView(view);
        }

        vh.base.removeAllViews();
        vh.base.addView(view);
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return TYPE_HEADER;
        } else if (isFooter(position)) {
            return TYPE_FOOTER;
        }
        int type = getItemViewTypeHF(getRealPosition(position));
        if (type == TYPE_HEADER || type == TYPE_FOOTER) {
            throw new IllegalArgumentException("Item type cannot equal " + TYPE_HEADER + " or " + TYPE_FOOTER);
        }
        return type;
    }

    private boolean isHeader(int position) {
        return (position < mHeaders.size());
    }

    private boolean isFooter(int position) {
        return (position >= mHeaders.size() + getItemCountHF());
    }

    public int getItemViewTypeHF(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mHeaders.size() + getItemCountHF() + mFooters.size();
    }

    @Override
    public final long getItemId(int position) {
        return getItemIdHF(getRealPosition(position));
    }

    public long getItemIdHF(int position) {
        return super.getItemId(position);
    }

    //删除header View
    public void removeHeader(View header) {
        if (mHeaders.contains(header)) {
            //animate
            notifyItemRemoved(mHeaders.indexOf(header));
            mHeaders.remove(header);
        }
    }

    //添加header View
    public void addHeader(View header) {
        if (!mHeaders.contains(header)) {
            mHeaders.add(header);
            //TODO 如果mHeader de size == 0?
            notifyItemInserted(mHeaders.size() - 1);
        }
    }

    //添加一个尾部到列表项末尾
    public void addFooter(View footer) {
        if (!mFooters.contains(footer)) {
            mFooters.add(footer);
            //这边可以执行动画
            notifyItemInserted(mHeaders.size() + getItemCountHF() + mFooters.size() - 1);
        }
    }

    //删除列表项的底部局
    public void removeFooter(View footer) {
        if (mFooters.contains(footer)) {
            //animate
            notifyItemRemoved(mHeaders.size() + getItemCountHF() + mFooters.indexOf(footer));
            mFooters.remove(footer);
        }
    }

    //与数据项一样的大小
    protected abstract int getItemCountHF();

    //Item 项被点击
    protected void onItemClick(RecyclerView.ViewHolder vh, int position) {

    }

    //Item项长按
    protected void onItemLongClick(RecyclerView.ViewHolder vh, int position) {

    }

    //头部和尾部 的ViewHolder 只是一个桢布局
    public static class HeaderFooterViewHolder extends ViewHolder {
        FrameLayout base;

        public HeaderFooterViewHolder(View itemView) {
            super(itemView);
            base = (FrameLayout) itemView;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;

    private class MyOnClickListener implements View.OnClickListener {
        private RecyclerView.ViewHolder vh;

        public MyOnClickListener(RecyclerView.ViewHolder vh) {
            super();
            this.vh = vh;
        }

        @Override
        public void onClick(View v) {
            int position = getRealPosition(vh.getLayoutPosition());
            if (HFAdapter.this.onItemClickListener != null) {
                //回调到上层使用这个Adapter的点击事件
                HFAdapter.this.onItemClickListener.onItemClick(HFAdapter.this,vh,position);
            }
            onItemClick(vh,position);
        }
    }

    private class MyOnLongClickListener implements View.OnLongClickListener {
        private RecyclerView.ViewHolder vh;

        public MyOnLongClickListener(RecyclerView.ViewHolder vh) {
            super();
            this.vh = vh;
        }

        @Override
        public boolean onLongClick(View v) {
            int position = getRealPosition(vh.getLayoutPosition());
            if (HFAdapter.this.onItemLongClickListener != null) {
                HFAdapter.this.onItemLongClickListener.onItemLongClick(HFAdapter.this, vh, position);
            }
            onItemLongClick(vh, position);
            return true;
        }

    }

    public static interface OnItemClickListener {
        void onItemClick(HFAdapter adapter, RecyclerView.ViewHolder vh, int postion);
    }

    public static interface OnItemLongClickListener{
        void onItemLongClick(HFAdapter adapter, RecyclerView.ViewHolder vh, int position);
    }
}
