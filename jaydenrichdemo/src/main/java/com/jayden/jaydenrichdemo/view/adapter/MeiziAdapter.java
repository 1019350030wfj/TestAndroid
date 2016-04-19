package com.jayden.jaydenrichdemo.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jayden.jaydenrich.utils.DateUtils;
import com.jayden.jaydenrich.utils.image.ImageUtils;
import com.jayden.jaydenrich.view.adapter.BaseRecyclerAdapter;
import com.jayden.jaydenrich.widget.HWRadioImageView;
import com.jayden.jaydenrichdemo.R;
import com.jayden.jaydenrichdemo.model.bean.Meizi;
import com.jayden.pulltorefresh.IDataAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public class MeiziAdapter extends BaseRecyclerAdapter<Meizi, MeiziAdapter.ViewHolder> implements IDataAdapter<List<Meizi>> {

    public MeiziAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizi, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meizi meizi = getItem(position);
//        int red = (int) (Math.random() * 255);
//        int green = (int) (Math.random() * 255);
//        int blue = (int) (Math.random() * 255);
//        holder.ivMeizi.setBackgroundColor(Color.argb(204, red, green, blue));
        ImageUtils.getImage(mContext,holder.ivMeizi,meizi.url);
        holder.tvWho.setText(meizi.who);
        holder.tvAvatar.setText(meizi.who.substring(0, 1).toUpperCase());
        holder.tvDesc.setText(meizi.desc);
        holder.tvTime.setText(DateUtils.toDateTimeStr(meizi.publishedAt));
        holder.tvAvatar.setVisibility(View.GONE);
    }

    @Override
    public void notifyDataChanged(List<Meizi> data, boolean isRefresh) {
        if (isRefresh && mData != null) {
            mData.clear();
            setData(data);
            return;
        }
        addData(data);
    }

    @Override
    public boolean isEmpty() {
        return (mData == null || mData.isEmpty());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_meizi)
        HWRadioImageView ivMeizi;
        @Bind(R.id.tv_avatar)
        TextView tvAvatar;
        @Bind(R.id.tv_who)
        TextView tvWho;
        @Bind(R.id.tv_time)
        TextView tvTime;
        @Bind(R.id.tv_desc)
        TextView tvDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
