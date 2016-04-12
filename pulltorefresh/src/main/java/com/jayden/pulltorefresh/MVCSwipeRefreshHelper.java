package com.jayden.pulltorefresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * SwipeRefreshLayout必须有Parent
 *
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class MVCSwipeRefreshHelper<T> extends MVCHelper<T> {

    public MVCSwipeRefreshHelper(SwipeRefreshLayout refreshView) {
        super(new RefreshView(refreshView));
    }

    public MVCSwipeRefreshHelper(SwipeRefreshLayout refreshView, ILoadViewFactory.ILoadingView loadView, ILoadViewFactory.ILoadMoreView iLoadMoreView) {
        super(new RefreshView(refreshView), loadView,iLoadMoreView);
    }


    private static class RefreshView implements IRefreshView {

        private SwipeRefreshLayout swipeRefreshLayout;
        private View mTarget;
        private OnRefreshListener onRefreshListener;

        public RefreshView(SwipeRefreshLayout swipeRefreshLayout) {
            this.swipeRefreshLayout = swipeRefreshLayout;
            if (swipeRefreshLayout.getParent() == null) {
                throw new RuntimeException("PtrClassicFrameLayout 必须有Parent");
            }
            try {
                Method method = swipeRefreshLayout.getClass().getDeclaredMethod("ensureTarget");
                method.setAccessible(true);
                method.invoke(swipeRefreshLayout);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Field field = null;
            try {
                field = swipeRefreshLayout.getClass().getDeclaredField("mTarget");
                field.setAccessible(true);
                mTarget = (View) field.get(swipeRefreshLayout);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            swipeRefreshLayout.setOnRefreshListener(listener);
        }

        @Override
        public View getContentView() {
            return mTarget;
        }

        @Override
        public View getSwitchView() {
            return swipeRefreshLayout;
        }

        @Override
        public void setOnRefreshListener(OnRefreshListener listener) {
            this.onRefreshListener = listener;
        }

        @Override
        public void showRefreshComplete() {
            swipeRefreshLayout.setRefreshing(false);
        }

        @Override
        public void showRefreshing() {
            swipeRefreshLayout.setRefreshing(true);
        }

        private SwipeRefreshLayout.OnRefreshListener listener = new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
            }
        };

    }
}
