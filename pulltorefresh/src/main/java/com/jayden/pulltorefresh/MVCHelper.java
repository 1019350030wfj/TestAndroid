package com.jayden.pulltorefresh;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.jayden.pulltorefresh.ILoadViewFactory.ILoadMoreView;
import com.jayden.pulltorefresh.ILoadViewFactory.ILoadingView;
import com.jayden.pulltorefresh.IRefreshView.OnRefreshListener;
import com.jayden.pulltorefresh.impl.DefaultLoadViewFactory;
import com.jayden.pulltorefresh.utils.NetworkUtils;
import com.jayden.pulltorefresh.viewhandle.ListViewHandler;
import com.jayden.pulltorefresh.viewhandle.RecyclerViewHandler;
import com.jayden.pulltorefresh.viewhandle.ViewHandle;

/**
 * <h1>下拉刷新，上滑加载更多的控件的辅助类</h1><br>
 *
 *     刷新，加载更多规则<br>
 *  当用户下拉刷新时，会取消掉当前的刷新，以及加载更多的任务<br>
 *  当用户加载更多的时候，如果有已经正在刷新或加载更多是不会再执行加载更多的操作。<br>
 *
 * 参考自 https://github.com/LuckyJayce/MVCHelper
 * Created by Jayden on 2016/4/9.
 * Email : 1570713698@qq.com
 *
 * 要添加 android.permission.ACCESS_NETWORK_STATE 权限，这个用来检测是否有网络
 */
public class MVCHelper<T> {

    private IDataAdapter<T> dataAdapter;
    private IRefreshView refreshView;
    private IDataSource<T> dataSource;
    private IAsyncDataSource<T> asyncDataSource;
    private RequestHandle cancle;
    private MyAsyncTask<Void,Void,T> asyncTask;

    private ListViewHandler listViewHandler = new ListViewHandler();

    private RecyclerViewHandler recyclerViewHandler = new RecyclerViewHandler();

    private View contentView;
    private Context context;
    private MOnStateChangeListener<T> onStateChangeListener = new MOnStateChangeListener();
    public static ILoadViewFactory loadViewFactory = new DefaultLoadViewFactory();

    private boolean autoLoadMore = true;

    /**
     * 是否还有更多数据，如果服务器返回的数据为空的话
     * 就说明没有更多数据了，也就没有必要自动加载更多数据
     */
    private boolean hasMoreData = true;

    /*** 加载更多的时候是否事先检查网络是否可用。 */
    private boolean needCheckNetwork = true;

    private boolean hasInitLoadMoreView = false;
    private Handler handler;
    private long loadDataTime = -1;

    private ILoadMoreView mLoadMoreView;
    private ILoadingView mLoadView;

    public MVCHelper(IRefreshView refreshView) {
        this(refreshView,loadViewFactory.madeLoadingView(),loadViewFactory.madeLoadMoreView());
    }

    public MVCHelper(IRefreshView refreshView, ILoadingView loadView) {
        this(refreshView, loadView, null);
    }

    public MVCHelper(IRefreshView refreshView, ILoadingView iLoadingView, ILoadMoreView iLoadMoreView) {
        super();
        this.context = refreshView.getContentView().getContext().getApplicationContext();
        this.autoLoadMore = true; //默认自动加载更多
        this.refreshView = refreshView;
        contentView = refreshView.getContentView();
        //scrollview和listview拉到顶或底部的时候那个模糊边缘
        contentView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        refreshView.setOnRefreshListener(onRefreshListener);
        mLoadView = iLoadingView;
        mLoadMoreView = iLoadMoreView;
        mLoadView.init(refreshView.getSwitchView(),onClickRefreshListener);
        handler = new Handler(Looper.getMainLooper());
    }

    /**
     * 设置LoadView的factory，用于创建使用者自定义的加载失败，加载中，加载更多等布局
     *
     * @param loadViewFactory
     */
    public static void setLoadViewFactory(ILoadViewFactory loadViewFactory) {
        MVCHelper.loadViewFactory = loadViewFactory;
    }

    /**
     * 如果不是网络请求的业务可以把这个设置为false
     *
     * @param needCheckNetwork
     */
    public void setNeedCheckNetwork(boolean needCheckNetwork) {
        this.needCheckNetwork = needCheckNetwork;
    }

    /**
     * 异步加载
     * 设置数据源，用于加载数据
     *
     * @param asyncDataSource
     */
    public void setAsyncDataSource(IAsyncDataSource<T> asyncDataSource) {
        this.asyncDataSource = asyncDataSource;
    }

    /**
     * 设置数据源，用于加载数据
     *
     * @param dataSource
     */
    public void setDataSource(IDataSource<T> dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 设置适配器，用于显示数据
     *
     * @param adapter
     */
    public void setAdapter(IDataAdapter<T> adapter) {
        View view = getContentView();
        hasInitLoadMoreView = false;
        if (view instanceof ListView) {
            hasInitLoadMoreView = listViewHandler.handleSetAdapter(view,adapter,mLoadMoreView,onClickLoadMoreListener);
            listViewHandler.setOnScrollBottomListener(view,onScrollBottomListener);
        } else if (view instanceof RecyclerView) {
            hasInitLoadMoreView = recyclerViewHandler.handleSetAdapter(view,adapter,mLoadMoreView,onClickLoadMoreListener);
            recyclerViewHandler.setOnScrollBottomListener(view,onScrollBottomListener);
        }
        this.dataAdapter = adapter;
    }

    public void setAdapter(IDataAdapter<T> adapter, ViewHandle viewHandle) {
        hasInitLoadMoreView = false;
        if (viewHandle != null) {
            View view = getContentView();
            hasInitLoadMoreView = viewHandle.handleSetAdapter(view,adapter,mLoadMoreView,onClickLoadMoreListener);
            viewHandle.setOnScrollBottomListener(view,onScrollBottomListener);
        }
        this.dataAdapter = adapter;
    }

    /**
     * 设置状态监听，监听开始刷新，刷新成功，开始加载更多，加载更多成功
     *
     * @param onStateChangeListener
     */
    public void setOnStateChangeListener(OnStateChangeListener<T> onStateChangeListener) {
        this.onStateChangeListener.setOnStateChangeListener(onStateChangeListener);
    }

    /**
     * 设置状态监听，监听开始刷新，刷新成功
     *
     * @param onStateChangeListener
     */
    public void setOnStateChangeListener(OnRefreshStateChangeListener<T> onStateChangeListener) {
        this.onStateChangeListener.setOnRefreshStateChangeListener(onStateChangeListener);
    }

    /**
     * 设置状态监听，监听开始加载更多，加载更多成功
     *
     * @param onLoadMoreStateChangeListener
     */
    public void setOnStateChangeListener(OnLoadMoreStateChangeListener<T> onLoadMoreStateChangeListener) {
        this.onStateChangeListener.setOnLoadMoreStateChangeListener(onLoadMoreStateChangeListener);
    }

    /**
     * 获取内容显示区域view
     * @param <T>
     * @return
     */
    public <T extends View> T getContentView() {
        return (T) refreshView.getContentView();
    }

    /**
     * 做销毁操作，比如关闭正在加载数据的异步线程等
     */
    public void destroy() {
        if (asyncTask != null && asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
            asyncTask.cancel(true);
            asyncTask = null;
        }
        if (cancle != null) {
            cancle.cancle();
            cancle = null;
        }
        handler.removeCallbacksAndMessages(null);
        handler = null;
    }

    private View.OnClickListener onClickLoadMoreListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            loadMore();
        }
    };

    private OnRefreshListener onRefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            refresh();
        }
    };

    private View.OnClickListener onClickRefreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            refresh();
        }
    };

    /**
     * 加载监听
     *
     * @param <T>
     */
    private static class MOnStateChangeListener<T> implements OnStateChangeListener<T> {

        private OnStateChangeListener<T> onStateChangeListener;
        private OnRefreshStateChangeListener<T> onRefreshStateChangeListener;
        private OnLoadMoreStateChangeListener<T> onLoadMoreStateChangeListener;

        public void setOnLoadMoreStateChangeListener(OnLoadMoreStateChangeListener<T> onLoadMoreStateChangeListener) {
            this.onLoadMoreStateChangeListener = onLoadMoreStateChangeListener;
        }

        public void setOnRefreshStateChangeListener(OnRefreshStateChangeListener<T> onRefreshStateChangeListener) {
            this.onRefreshStateChangeListener = onRefreshStateChangeListener;
        }

        public void setOnStateChangeListener(OnStateChangeListener<T> onStateChangeListener) {
            this.onStateChangeListener = onStateChangeListener;
        }

        @Override
        public void onStartLoadMore(IDataAdapter<T> adapter) {
            if (onStateChangeListener != null) {
                onStateChangeListener.onStartLoadMore(adapter);
            } else if (onLoadMoreStateChangeListener != null) {
                onLoadMoreStateChangeListener.onStartLoadMore(adapter);
            }
        }

        @Override
        public void onEndLoadMore(IDataAdapter<T> adapter, T result) {
            if (onStateChangeListener != null) {
                onStateChangeListener.onEndLoadMore(adapter, result);
            } else if (onLoadMoreStateChangeListener != null) {
                onLoadMoreStateChangeListener.onEndLoadMore(adapter, result);
            }
        }

        @Override
        public void onStartRefresh(IDataAdapter<T> adapter) {
            if (onStateChangeListener != null) {
                onStateChangeListener.onStartRefresh(adapter);
            } else if (onRefreshStateChangeListener != null) {
                onRefreshStateChangeListener.onStartRefresh(adapter);
            }
        }

        @Override
        public void onEndRefresh(IDataAdapter<T> adapter, T result) {
            if (onStateChangeListener != null) {
                onStateChangeListener.onEndRefresh(adapter, result);
            } else if (onRefreshStateChangeListener != null) {
                onRefreshStateChangeListener.onEndRefresh(adapter, result);
            }
        }
    }

    /**
     * 是否正在加载
     * @return
     */
    public boolean isLoading() {
        if (asyncDataSource != null) {
            return cancle != null;
        }
        return asyncTask != null && asyncTask.isLoading();
    }

    /**
     * 刷新，开启异步线程，
     * 并且显示加载中的界面，
     * 当数据加载完成自动还原成加载完成的布局，
     * 并且刷新列表数据
     */
    public void refresh() {
        if (dataAdapter == null || (dataSource == null) && asyncDataSource == null) {
            if (refreshView != null) {
                refreshView.showRefreshComplete();
            }
            return;
        }
        if (dataSource != null) {
            if (asyncTask != null && asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
                //取消之前的任务
                asyncTask.cancel(true);
            }
            asyncTask = new RefreshAsynTask(dataSource,dataAdapter);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                asyncTask.execute();
            }
        } else {
            if (cancle != null) {
                cancle.cancle();
                cancle = null;
            }
            MResponseSender responseSender = new RefreshResponseSender(asyncDataSource,dataAdapter);
            responseSender.onPreExecute();
            cancle = responseSender.execute();
        }
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        //正在加载，返回
        if (isLoading()) {
            return;
        }

        if (dataAdapter.isEmpty()) {
            //没有数据，则是调用refresh
            refresh();
            return;
        }
        if (dataAdapter == null || (dataSource == null && asyncDataSource == null)) {
            if (refreshView != null) {
                refreshView.showRefreshComplete();
            }
            return;
        }
        if (dataSource != null) {
            //开启线程执行IDataSource
            if (asyncTask != null && asyncTask.getStatus() != AsyncTask.Status.FINISHED) {
                asyncTask.cancel(true);
            }
            asyncTask = new LoadMoreAsyncTask(dataSource,dataAdapter);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            } else {
                asyncTask.execute();
            }
        } else {
            // 开启线程执行 IAsyncDataSource
            if (cancle != null) {
                cancle.cancle();
                cancle = null;
            }
            LoadMoreResponseSender responseSender = new LoadMoreResponseSender(asyncDataSource, dataAdapter);
            responseSender.onPreExecute();
            cancle = responseSender.execute();
        }
    }

    private OnScrollBottomListener onScrollBottomListener = new OnScrollBottomListener() {
        @Override
        public void onScrollBottom() {
            if (autoLoadMore && hasMoreData && !isLoading()) {
                //自动上啦加载，有更多数据，且正在加载
                if (needCheckNetwork && !NetworkUtils.hasNetwork(context)) {
                    mLoadMoreView.showFail(new Exception("网络不可用"));
                } else {
                    loadMore();
                }
            }
        }
    };

    private class LoadMoreResponseSender extends MResponseSender {
        private IAsyncDataSource<T> tDataSource;
        private IDataAdapter<T> tDataAdapter;

        public LoadMoreResponseSender(IAsyncDataSource<T> tDataSource,IDataAdapter<T> tDataAdapter) {
            super();
            this.tDataAdapter = tDataAdapter;
            this.tDataSource = tDataSource;
        }

        @Override
        protected void onPreExecute() {
            onStateChangeListener.onStartLoadMore(tDataAdapter);
            if (hasInitLoadMoreView && mLoadMoreView != null) {
                mLoadMoreView.showLoading();
            }
        }

        @Override
        public RequestHandle executeImp() throws Exception {
            return tDataSource.loadMore(this);
        }

        @Override
        protected void onPostExecute(T result, Exception exception) {
            if (result == null) {
                mLoadView.tipFail(exception);
                if (hasInitLoadMoreView && mLoadMoreView != null) {
                    mLoadMoreView.showFail(exception);
                }
            } else {
                tDataAdapter.notifyDataChanged(result, false);
                if (tDataAdapter.isEmpty()) {
                    mLoadView.showEmpty();
                } else {
                    mLoadView.restore();
                }
                hasMoreData = tDataSource.hasMore();
                if (hasInitLoadMoreView && mLoadMoreView != null) {
                    if (hasMoreData) {
                        mLoadMoreView.showNormal();
                    } else {
                        mLoadMoreView.showNoMore();
                    }
                }
            }
            onStateChangeListener.onEndLoadMore(tDataAdapter, result);
        }
    }

    private abstract class MResponseSender implements ResponseSender<T> {
        protected abstract void onPreExecute();
        protected abstract void onPostExecute(T data,Exception exeception);

        @Override
        public void sendError(Exception exception) {
            onPostExecute(null,exception);
            cancle = null;
        }

        @Override
        public void sendData(T data) {
            onPostExecute(data,null);
            cancle = null;
        }

        public RequestHandle execute() {
            try {
                return executeImp();
            } catch (Exception e) {
                e.printStackTrace();
                onPostExecute(null,e);
            }
            return null;
        }

        public abstract RequestHandle executeImp() throws Exception;
    }

    private class RefreshResponseSender extends MResponseSender {

        private IAsyncDataSource<T> tDataSource;
        private IDataAdapter<T> tDataAdapter;
        private Runnable showRefreshing;

        public RefreshResponseSender(IAsyncDataSource<T> tDataSource,IDataAdapter<T> tDataAdapter) {
            super();
            this.tDataAdapter = tDataAdapter;
            this.tDataSource = tDataSource;
        }

        @Override
        protected void onPreExecute() {
            if (hasInitLoadMoreView && mLoadMoreView != null) {
                mLoadMoreView.showNormal();
            }
            if (tDataSource instanceof IDataCacheLoader) {
                @SuppressWarnings("unchecked")
                IDataCacheLoader<T> cacheLoader = (IDataCacheLoader<T>) tDataSource;
                T data = cacheLoader.loadCache(tDataAdapter.isEmpty());
                if (data != null) {
                    tDataAdapter.notifyDataChanged(data, true);
                }
            }
            handler.post(showRefreshing = new Runnable() {

                @Override
                public void run() {
                    if (tDataAdapter.isEmpty()) {
                        mLoadView.showLoading();
                        refreshView.showRefreshComplete();
                    } else {
                        mLoadView.restore();
                        refreshView.showRefreshing();
                    }
                }
            });
            onStateChangeListener.onStartRefresh(tDataAdapter);
        }

        @Override
        protected void onPostExecute(T result, Exception exception) {
            handler.removeCallbacks(showRefreshing);
            if (result == null) {
                if (tDataAdapter.isEmpty()) {
                    mLoadView.showFail(exception);
                } else {
                    mLoadView.tipFail(exception);
                }
            } else {
                loadDataTime = System.currentTimeMillis();
                tDataAdapter.notifyDataChanged(result, true);
                if (tDataAdapter.isEmpty()) {
                    mLoadView.showEmpty();
                } else {
                    mLoadView.restore();
                }
                hasMoreData = tDataSource.hasMore();
                if (hasInitLoadMoreView && mLoadMoreView != null) {
                    if (hasMoreData) {
                        mLoadMoreView.showNormal();
                    } else {
                        mLoadMoreView.showNoMore();
                    }
                }
            }
            onStateChangeListener.onEndRefresh(tDataAdapter, result);
            refreshView.showRefreshComplete();
        }

        @Override
        public RequestHandle executeImp() throws Exception {
            return tDataSource.refresh(this);
        }
    }

    private class RefreshAsynTask extends MyAsyncTask<Void,Void,T> {
        private IDataAdapter<T> tDataAdapter;
        private IDataSource<T> tDataSource;
        private volatile Exception tException;
        private Runnable showRefreshing;

        public RefreshAsynTask(IDataSource<T> dataSource,IDataAdapter<T> dataAdapter) {
            super();
            this.tDataAdapter = dataAdapter;
            this.tDataSource = dataSource;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (hasInitLoadMoreView && mLoadMoreView != null) {
                mLoadMoreView.showNormal();
            }

            if (tDataSource instanceof IDataCacheLoader) {
                IDataCacheLoader<T> cacheLoader = (IDataCacheLoader<T>) tDataSource;
                T data = cacheLoader.loadCache(tDataAdapter.isEmpty());
                if (data != null) {
                    tDataAdapter.notifyDataChanged(data,true);
                }
            }

            handler.post(showRefreshing = new Runnable() {

                @Override
                public void run() {
                    if (tDataAdapter.isEmpty()) {
                        mLoadView.showLoading();
                        refreshView.showRefreshComplete();
                    } else {
                        mLoadView.restore();
                        refreshView.showRefreshing();
                    }
                }
            });
            onStateChangeListener.onStartRefresh(tDataAdapter);
        }

        @Override
        protected T doInBackground(Void... params) {
            try {
                return tDataSource.refresh();//执行刷新操作
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            //取消执行的任务
            handler.removeCallbacks(showRefreshing);
        }

        @Override
        protected void onPostExecute(T result) {
            super.onPostExecute(result);
            handler.removeCallbacks(showRefreshing);
            if (result == null) {
                if (tDataAdapter.isEmpty()) {
                    mLoadView.showFail(tException);
                } else {
                    mLoadView.tipFail(tException);
                }
            } else {
                loadDataTime = System.currentTimeMillis();
                tDataAdapter.notifyDataChanged(result,true);
                if (tDataAdapter.isEmpty()) {
                    mLoadView.showEmpty();
                } else {
                    mLoadView.restore();
                }

                hasMoreData = tDataSource.hasMore();
                if (hasInitLoadMoreView && mLoadMoreView != null) {
                    if (hasMoreData) {
                        mLoadMoreView.showNormal();
                    } else {
                        mLoadMoreView.showNoMore();
                    }
                }
            }

            onStateChangeListener.onEndRefresh(tDataAdapter,result);
            refreshView.showRefreshComplete();
        }
    }

    private class LoadMoreAsyncTask extends MyAsyncTask<Void,Void,T> {
        private IDataSource<T> tDataSource;
        private IDataAdapter<T> tDataAdapter;
        private Exception tException;

        public LoadMoreAsyncTask(IDataSource<T> tDataSource,IDataAdapter<T> tDataAdapter) {
            super();
            this.tDataSource = tDataSource;
            this.tDataAdapter = tDataAdapter;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            onStateChangeListener.onStartLoadMore(tDataAdapter);
            if (hasInitLoadMoreView && mLoadMoreView != null) {
                mLoadMoreView.showLoading();
            }
        }

        @Override
        protected T doInBackground(Void... params) {
            try {
                return tDataSource.loadMore();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(T result) {
            super.onPostExecute(result);
            if (result == null) {
                mLoadView.tipFail(tException);
                if (hasInitLoadMoreView && mLoadMoreView != null) {
                    mLoadMoreView.showFail(tException);
                }
            } else {
                tDataAdapter.notifyDataChanged(result,false);
                if (tDataAdapter.isEmpty()) {
                    mLoadView.showEmpty();
                } else {
                    mLoadView.restore();
                }
                hasMoreData = tDataSource.hasMore();
                if (hasInitLoadMoreView && mLoadMoreView != null) {
                    if (hasMoreData) {
                        mLoadMoreView.showNormal();
                    } else {
                        mLoadMoreView.showNoMore();
                    }
                }
            }
            onStateChangeListener.onEndLoadMore(tDataAdapter,result);
        }
    }

    private static class MyAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>{

        private volatile boolean post;

        @Override
        protected Result doInBackground(Params... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Result result) {
            super.onPostExecute(result);
            post = true;
        }

        /**
         * post = true; 说明异步任务已经结束，
         * @return  返回false，说明没有正在加载
         */
        private boolean isLoading() {
            if (post) {
                return false;
            }

            return getStatus() != Status.FINISHED;
        }
    }
}
