package com.jayden.pulltorefresh.viewhandle;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;

import com.jayden.pulltorefresh.IDataAdapter;
import com.jayden.pulltorefresh.ILoadViewFactory;
import com.jayden.pulltorefresh.OnScrollBottomListener;
import com.jayden.pulltorefresh.recyclerview.HFAdapter;
import com.jayden.pulltorefresh.recyclerview.HFRecyclerViewAdapter;

/**
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class RecyclerViewHandler implements ViewHandle {
    @Override
    public boolean handleSetAdapter(View contentView, IDataAdapter<?> adapter, ILoadViewFactory.ILoadMoreView iloadMoreView, View.OnClickListener onClickListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        boolean hasInit = false;
        Adapter<?> adapter2 = (Adapter<?>) adapter;
        if (iloadMoreView != null) {
            final HFAdapter hfAdapter;
            if (adapter instanceof HFAdapter) {
                hfAdapter = (HFAdapter) adapter;
            } else {
                hfAdapter = new HFRecyclerViewAdapter(adapter2);
            }
            adapter2 = hfAdapter;
            final Context context = recyclerView.getContext().getApplicationContext();
            iloadMoreView.init(new ILoadViewFactory.FootViewAdder() {
                @Override
                public View addFootView(View view) {
                    hfAdapter.addFooter(view);
                    return view;
                }

                @Override
                public View addFootView(int layoutId) {
                    View view = LayoutInflater.from(context).inflate(layoutId, recyclerView, false);
                    return addFootView(view);
                }
            },onClickListener);
            hasInit = true;
        }
        recyclerView.setAdapter(adapter2);
        return hasInit;
    }

    @Override
    public void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener) {
        final RecyclerView recyclerView = (RecyclerView) contentView;
        recyclerView.addOnScrollListener(new RecyclerViewOnScrollListener(onScrollBottomListener));
    }

    /**
     * 滑动监听
     */
    private static class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {
        private OnScrollBottomListener onScrollBottomListener;

        public RecyclerViewOnScrollListener(OnScrollBottomListener onScrollBottomListener) {
            super();
            this.onScrollBottomListener = onScrollBottomListener;
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && isScollBottom(recyclerView)) {
                if (onScrollBottomListener != null) {
                    onScrollBottomListener.onScrollBottom();
                }
            }
        }

        private boolean isScollBottom(RecyclerView recyclerView) {
            return !isCanScollVertically(recyclerView);
        }

        private boolean isCanScollVertically(RecyclerView recyclerView) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                return ViewCompat.canScrollVertically(recyclerView,1) || recyclerView.getScaleY() < recyclerView.getHeight();
            } else {
                return ViewCompat.canScrollVertically(recyclerView,1);
            }
        }
    }
}
