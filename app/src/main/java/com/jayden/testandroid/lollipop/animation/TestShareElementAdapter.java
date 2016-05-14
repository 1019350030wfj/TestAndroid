package com.jayden.testandroid.lollipop.animation;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayden.testandroid.R;

import java.util.List;

/**
 * Created by Jayden on 2016/5/12.
 * Email : 1570713698@qq.com
 */
public class TestShareElementAdapter extends RecyclerView.Adapter<TestShareElementAdapter.ViewHolder> {

    private List<Actor> mDatas;

    public TestShareElementAdapter(List<Actor> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item_test_share_element,null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Actor actor = mDatas.get(position);
        holder.mTxt.setText(actor.name);
        holder.mImage.setImageResource(actor.picName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(holder.mImage,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImage;
        TextView mTxt;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.image);
            mTxt = (TextView) itemView.findViewById(R.id.text);
        }
    }

    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener mListener) {
        this.mListener = mListener;
    }

    public interface OnItemClickListener {

        void onItemClick(ImageView imageView,int pos);
    }
}
