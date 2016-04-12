package com.jayden.pulltorefresh.viewhandle;

import android.view.View;

import com.jayden.pulltorefresh.IDataAdapter;
import com.jayden.pulltorefresh.ILoadViewFactory;
import com.jayden.pulltorefresh.OnScrollBottomListener;

/**
 * Created by Jayden on 2016/4/11.
 * Email : 1570713698@qq.com
 */
public interface ViewHandle {

    /**
     * 是否有初始化ILoadMoreView
     * @param contentView
     * @param adapter
     * @param onClickListener
     * @return
     */
    boolean handleSetAdapter(View contentView, IDataAdapter<?> adapter,
                             ILoadViewFactory.ILoadMoreView iloadMoreView, View.OnClickListener onClickListener);

    void setOnScrollBottomListener(View contentView, OnScrollBottomListener onScrollBottomListener);
}
