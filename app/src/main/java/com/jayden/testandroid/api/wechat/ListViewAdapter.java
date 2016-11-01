package com.jayden.testandroid.api.wechat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jayden.testandroid.R;

import java.util.List;

/**
 * @文件名称: ListViewAdapter.java
 * @功能描述:
 * @版本信息: Copyright (c)2014
 * @开发人员: vincent
 * @版本日志: 1.0
 * @创建时间: 2014年3月18日 下午1:37:52
 */
public class ListViewAdapter extends BaseAdapter {
	private Context context;
	private List<String> dataList;

	public ListViewAdapter(Context context, List<String> dataList) {
		super();
		this.context = context;
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_weichat_delete, null);
		}
		TextView titleTextView = (TextView) convertView.findViewById(R.id.title);
		titleTextView.setText(dataList.get(position));
		return convertView;
	}

}
