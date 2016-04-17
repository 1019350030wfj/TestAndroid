package com.jayden.jaydenrich.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T,VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> mData;
    protected OnRecyclerItemClickListener<T> mListener;
    protected int itemHeight;

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setData(List<T> data) {
        if (data == null) {
            return;
        }
        mData = data;
        try {
            notifyDataSetChanged();
        } catch (Exception e) {

        }
    }

    public void addData(T data) {
        if (data == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mData.add(data);
        notifyItemInserted(mData.size() - 1);
    }

    public List<T> getData() {
        return mData;
    }

    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    public void addData(List<T> data) {
        if (data == null || data.size() < 1) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        int start = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(start, data.size());
    }

    public void removeSingleData(T data) {
        if (data == null || mData == null || !mData.contains(data)) {
            return;
        }
        int position = mData.indexOf(data);
        removeDataByPosition(position);
    }

    /**
     * position  代表数据在集合中的位置,不表示视图的位置
     * @param position
     */
    public void removeDataByPosition(int position) {
        if (mData == null || mData.size() - 1 < position) {
            return;
        }
        mData.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * position  代表数据在集合中的位置,不表示视图的位置
     * @param position
     */
    public void insertData(T data,int position) {
        if (mData == null) {
            mData = new ArrayList<>();
        }
        if (position > mData.size() || data == null) {
            return;
        }
        mData.add(position,data);
        notifyItemInserted(position);
    }

    public T getLastItem() {
        return mData == null ? null : mData.get(mData.size() - 1);
    }

    /**
     * position  代表数据在集合中的位置,不表示视图的位置
     * @param position
     */
    public void replaceData(int position,T newData) {
        if (mData == null || mData.size() - 1 < position ) {
            return;
        }
        mData.set(position,newData);
        notifyItemChanged(position);
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public void setOnRecyclerItemClickListener(OnRecyclerItemClickListener<T> mListener) {
        this.mListener = mListener;
    }

    public interface OnRecyclerItemClickListener<T> {
        void onItemClicked(View item, T data, int position);
    }
}
