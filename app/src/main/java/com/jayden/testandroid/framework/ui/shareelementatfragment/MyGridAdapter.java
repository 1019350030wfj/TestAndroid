package com.jayden.testandroid.framework.ui.shareelementatfragment;

import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayden.testandroid.R;

import java.util.ArrayList;

/**
 * Created by Jayden on 2016/3/31.
 * Email : 1570713698@qq.com
 */
public class MyGridAdapter extends RecyclerView.Adapter<MyGridAdapter.ViewHolder> {

    private ArrayList<Pair<Integer, Integer>> mData;
    private MyViewOnClickListener listener;

    public MyGridAdapter(ArrayList<Pair<Integer, Integer>> mData,MyViewOnClickListener listener) {
        this.mData = mData;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gird, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(mData.get(position).first);
        holder.mImageView.setImageResource(mData.get(position).second);

        // 把每个图片视图设置不同的Transition名称, 防止在一个视图内有多个相同的名称, 在变换的时候造成混乱
        // Fragment支持多个View进行变换, 使用适配器时, 需要加以区分
        ViewCompat.setTransitionName(holder.getImageView(), String.valueOf(position) + "_image");

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickedView(holder,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.grid_image);
            mTextView = (TextView) itemView.findViewById(R.id.grid_text);
        }

        public ImageView getImageView() {
            return mImageView;
        }

        public TextView getTextView() {
            return mTextView;
        }
    }
}
