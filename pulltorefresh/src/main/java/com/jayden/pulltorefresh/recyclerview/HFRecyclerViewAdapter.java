package com.jayden.pulltorefresh.recyclerview;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

/**
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class HFRecyclerViewAdapter extends HFAdapter {

    private Adapter adapter;

    public HFRecyclerViewAdapter(Adapter adapter) {
        super();
        this.adapter = adapter;
        adapter.registerAdapterDataObserver(adapterDataObserver);
    }

    private AdapterDataObserver adapterDataObserver = new AdapterDataObserver() {

        public void onChanged() {
            HFRecyclerViewAdapter.this.notifyDataSetChanged();
        }

        public void onItemRangeChanged(int positionStart, int itemCount) {
            HFRecyclerViewAdapter.this.notifyItemRangeChanged(positionStart + getHeadSize(), itemCount);
        }

        public void onItemRangeInserted(int positionStart, int itemCount) {
            HFRecyclerViewAdapter.this.notifyItemRangeInserted(positionStart + getHeadSize(), itemCount);
        }

        public void onItemRangeRemoved(int positionStart, int itemCount) {
            HFRecyclerViewAdapter.this.notifyItemRangeRemoved(positionStart + getHeadSize(), itemCount);
        }

        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            HFRecyclerViewAdapter.this.notifyItemMoved(fromPosition + getHeadSize(), toPosition + getHeadSize());
        }
    };

    @Override
    protected ViewHolder onCreateViewHolderHF(ViewGroup parent, int viewType) {
        return adapter.onCreateViewHolder(parent,viewType);
    }

    @Override
    protected void onBindViewHolderHF(ViewHolder holder, int realPosition) {
        adapter.onBindViewHolder(holder,realPosition);
    }

    @Override
    protected int getItemCountHF() {
        return adapter.getItemCount();
    }

    @Override
    public int getItemViewTypeHF(int pos) {
        return adapter.getItemViewType(pos);
    }

    @Override
    public long getItemIdHF(int position) {
        return adapter.getItemId(position);
    }
}
