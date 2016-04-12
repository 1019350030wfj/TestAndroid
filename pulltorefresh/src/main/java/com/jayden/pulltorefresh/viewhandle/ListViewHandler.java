package com.jayden.pulltorefresh.viewhandle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jayden.pulltorefresh.IDataAdapter;
import com.jayden.pulltorefresh.ILoadViewFactory;
import com.jayden.pulltorefresh.OnScrollBottomListener;

/**
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class ListViewHandler implements ViewHandle {
    @Override
    public boolean handleSetAdapter(View contentView, IDataAdapter<?> adapter, ILoadViewFactory.ILoadMoreView iloadMoreView, View.OnClickListener onClickListener) {
        final ListView listView = (ListView) contentView;
        boolean hasInit = false;
        if (iloadMoreView != null) {
            final Context context = listView.getContext().getApplicationContext();
            iloadMoreView.init(new ILoadViewFactory.FootViewAdder() {
                @Override
                public View addFootView(View view) {
                    listView.addFooterView(view);
                    return view;
                }

                @Override
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId,listView,false);
                    return addFootView(view);
                }
            },onClickListener);
            hasInit = true;
        }
        listView.setAdapter((ListAdapter) adapter);
        return hasInit;
    }

    @Override
    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        ListView listView = (ListView) contentView;
        listView.setOnScrollListener(new ListViewOnScrollListener(onScrollBottomListener));
        listView.setOnItemSelectedListener(new ListViewOnItemSelectedListener(onScrollBottomListener));
    }

    private class ListViewOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        private OnScrollBottomListener onScrollBottomListener;

        public ListViewOnItemSelectedListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onItemSelected(AdapterView<?> listview, View view, int position, long id) {
            if (listview.getLastVisiblePosition() + 1 == listview.getCount()) {
                //滚动到最后一行
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScrollBottom();
                }
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    /**
     * 滚动到底部自动加载更多数据
     */
    private static class ListViewOnScrollListener implements AbsListView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public ListViewOnScrollListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onScrollStateChanged(AbsListView listView, int scrollState) {
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && listView.getLastVisiblePosition() + 1 == listView.getCount()) {// 如果滚动到最后一行
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScrollBottom();
                }
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }
}
