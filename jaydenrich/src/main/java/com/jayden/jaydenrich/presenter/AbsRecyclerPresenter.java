package com.jayden.jaydenrich.presenter;

import android.content.Context;

import com.jayden.jaydenrich.view.iview.IRecyclerView;

/**
 * 下拉刷新和加载更多的抽象Presenter
 *
 * Created by Jayden on 2016/4/19.
 */
public abstract class AbsRecyclerPresenter extends BasePresenter<IRecyclerView> {

    public AbsRecyclerPresenter(Context context, IRecyclerView iView) {
        super(context, iView);
    }

    public abstract void refresh();

    public abstract void loadMore();
}
