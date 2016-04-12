package com.jayden.pulltorefresh.impl;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jayden.pulltorefresh.ILoadViewFactory;
import com.jayden.pulltorefresh.R;
import com.jayden.pulltorefresh.view.vary.VaryViewHelper;

/**
 * Created by Jayden on 2016/4/9.
 * Email : 1570713698@qq.com
 */
public class DefaultLoadViewFactory implements ILoadViewFactory {
    @Override
    public ILoadMoreView madeLoadMoreView() {
        return new LoadMoreHelper();
    }

    @Override
    public ILoadingView madeLoadingView() {
        return new LoadingViewHelper();
    }

    /**
     * 加载ing、加载ed，加载failed等的默认布局
     * 实现借助与IVaryViewHelper
     */
    private static class LoadingViewHelper implements ILoadingView {

        private VaryViewHelper helper;
        private View.OnClickListener onClickListener;
        private Context context;

        @Override
        public void init(View switchView, View.OnClickListener onClickListener) {
            //switchView,就是内容区域，要在上面显示加载布局
            this.context = switchView.getContext().getApplicationContext();
            this.onClickListener = onClickListener;
            helper = new VaryViewHelper(switchView);
        }

        @Override
        public void showEmpty() {
            View layout = helper.inflate(R.layout.load_empty);
            TextView textView = (TextView) layout.findViewById(R.id.textView1);
            textView.setText("暂无数据");
            Button button = (Button) layout.findViewById(R.id.button1);
            button.setText("重试");
            button.setOnClickListener(onClickListener);
            helper.showLayout(layout);
        }

        @Override
        public void showLoading() {
            View layout = helper.inflate(R.layout.load_ing);
            TextView textView = (TextView) layout.findViewById(R.id.textView1);
            textView.setText("加载中...");
            helper.showLayout(layout);
        }

        @Override
        public void showFail(Exception e) {
            View layout = helper.inflate(R.layout.load_error);
            TextView textView = (TextView) layout.findViewById(R.id.textView1);
            textView.setText("网络加载失败");
            Button button = (Button) layout.findViewById(R.id.button1);
            button.setText("重试");
            button.setOnClickListener(onClickListener);
            helper.showLayout(layout);
        }

        @Override
        public void tipFail(Exception e) {
            Toast.makeText(context, "网络加载失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void restore() {
            helper.restoreView();
        }
    }

    /**
     * 加载更多默认布局
     */
    private static class LoadMoreHelper implements ILoadMoreView {

        protected TextView footView;

        protected View.OnClickListener onClickListener;

        @Override
        public void init(FootViewAdder footViewAdder, View.OnClickListener onClickListener) {
            footView = (TextView) footViewAdder.addFootView(R.layout.layout_listview_footer);
            this.onClickListener = onClickListener;
            showNormal();
        }

        @Override
        public void showNormal() {
            footView.setText("点击加载更多");
            footView.setOnClickListener(onClickListener);
        }

        @Override
        public void showNoMore() {
            footView.setText("已经加载完毕");
            footView.setOnClickListener(null);
        }

        @Override
        public void showLoading() {
            footView.setText("正在加载中..");
            footView.setOnClickListener(onClickListener);
        }

        @Override
        public void showFail(Exception e) {
            footView.setText("加载失败，点击重新加载");
            footView.setOnClickListener(onClickListener);
        }
    }
}
