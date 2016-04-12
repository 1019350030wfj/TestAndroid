package com.jayden.pulltorefresh;

import android.view.View;

/**
 * contentView 必须有Parent
 *
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class MVCNormalHelper<T> extends MVCHelper<T> {
    public MVCNormalHelper(View refreshView) {
        super(new RefreshView(refreshView));
    }

    public MVCNormalHelper(View refreshView, ILoadViewFactory.ILoadingView loadView) {
        super(new RefreshView(refreshView), loadView);
    }

    public MVCNormalHelper(View refreshView, ILoadViewFactory.ILoadingView iLoadingView, ILoadViewFactory.ILoadMoreView iLoadMoreView) {
        super(new RefreshView(refreshView), iLoadingView, iLoadMoreView);
    }

    private static class RefreshView implements IRefreshView{

        private View contentView;

        public RefreshView(View contentView) {
            super();
            this.contentView = contentView;
        }

        @Override
        public View getContentView() {
            return contentView;
        }

        @Override
        public View getSwitchView() {
            return contentView;
        }

        @Override
        public void setOnRefreshListener(OnRefreshListener listener) {

        }

        @Override
        public void showRefreshComplete() {

        }

        @Override
        public void showRefreshing() {

        }
    }
}
