package com.jayden.pulltorefresh;

import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.AbsListView;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * PtrClassicFrameLayout必须有Parent
 *
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class MVCUltraHelper<T> extends MVCHelper<T> {

    public MVCUltraHelper(PtrClassicFrameLayout refreshView) {
        super(new RefreshView(refreshView));
    }

    public MVCUltraHelper(PtrClassicFrameLayout refreshView, ILoadViewFactory.ILoadingView iLoadingView, ILoadViewFactory.ILoadMoreView iLoadMoreView) {
        super(new RefreshView(refreshView), iLoadingView, iLoadMoreView);
    }

    private static class RefreshView implements IRefreshView {

        private PtrFrameLayout ptrFrameLayout;

        public RefreshView(PtrFrameLayout ptrFrameLayout) {
            super();
            this.ptrFrameLayout = ptrFrameLayout;
            if (ptrFrameLayout.getParent() == null) {
                throw new RuntimeException("PtrClassicFrameLayout 必须有Parent");
            }
            ptrFrameLayout.setPtrHandler(ptrHandler);
        }

        private OnRefreshListener onRefreshListener;

        private PtrHandler ptrHandler = new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return checkContentCanBePulledDown(frame, content, header);
            }
        };

        @Override
        public View getContentView() {
            return ptrFrameLayout.getContentView();
        }

        @Override
        public View getSwitchView() {
            return ptrFrameLayout;
        }

        @Override
        public void setOnRefreshListener(OnRefreshListener listener) {
            this.onRefreshListener = listener;
        }

        @Override
        public void showRefreshComplete() {
            ptrFrameLayout.refreshComplete();
        }

        @Override
        public void showRefreshing() {
            ptrFrameLayout.setPtrHandler(null);
            ptrFrameLayout.autoRefresh(true, 150);
            ptrFrameLayout.setPtrHandler(ptrHandler);
        }
    }

    public static boolean canChildScrollUp(View mTarget) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mTarget, -1);
        }
    }

    /**
     * Default implement for check can perform pull to refresh
     *
     * @param frame
     * @param content
     * @param header
     * @return
     */
    public static boolean checkContentCanBePulledDown(PtrFrameLayout frame, View content, View header) {
        return !canChildScrollUp(content);
    }
}
