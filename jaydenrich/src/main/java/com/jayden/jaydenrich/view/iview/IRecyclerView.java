package com.jayden.jaydenrich.view.iview;

import java.util.List;

/**
 * RecyclerView的视图回调接口
 * Created by Jayden on 2016/4/19.
 * Email : 1570713698@qq.com
 */
public interface IRecyclerView<T> extends IBaseView {

    void onSuccess(List<T> data);

    void onLoadMoreSuccess(List<T> data);

    void onRefreshEnable(boolean isEnable);

    void onLoadMoreEnable(boolean isEnable);

    void loadMoreError();

    void refreshError();
}
