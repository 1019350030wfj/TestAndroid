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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public class XMeiziAdapter extends BaseRecyclerAdapter<Meizi, XMeiziAdapter.ViewHolder>{

    public XMeiziAdapter(Context context) {
        super(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meizi, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Meizi meizi = getItem(position);
        ImageUtils.getImage(mContext,holder.ivMeizi,meizi.url);
        holder.tvWho.setText(meizi.who);
        holder.tvAvatar.setText(meizi.who.substring(0, 1).toUpperCase());
        holder.tvDesc.setText(meizi.desc);
        holder.tvTime.setText(DateUtils.toDateTimeStr(meizi.publishedAt));
        holder.tvAvatar.setVisibility(View.GONE);
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
