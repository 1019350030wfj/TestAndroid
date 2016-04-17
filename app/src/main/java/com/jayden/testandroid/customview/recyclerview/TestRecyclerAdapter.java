package com.jayden.testandroid.customview.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jayden.testandroid.BaseRecyclerAdapter;
import com.jayden.testandroid.R;


/**
 * Created by Jayden on 2016/3/9.
 */
public class TestRecyclerAdapter extends BaseRecyclerAdapter<String,TestRecyclerAdapter.ViewHolder> {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(parent.getContext(), R.layout.item,null));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTextView.setText(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClicked(holder.itemView,getData().get(position),position);
            }
        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.text);
//            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mTextView.getContext(),"position = " + getAdapterPosition(),Toast.LENGTH_LONG).show();
        }
    }
}
