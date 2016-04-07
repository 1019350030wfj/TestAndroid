package com.jayden.testandroid.framework.ui.shareelementatfragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jayden.testandroid.R;

import java.util.ArrayList;

/**
 * Created by Jayden on 2016/3/31.
 * Email : 1570713698@qq.com
 */
public class GridFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private ArrayList<Pair<Integer, Integer>> mData;
    private MyGridAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        mAdapter = new MyGridAdapter(mData, new MyViewOnClickListener() {
            @Override
            public void onClickedView(MyGridAdapter.ViewHolder holder, int position) {
                handlerTransitionToDetail(holder, position);
            }
        });
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void handlerTransitionToDetail(MyGridAdapter.ViewHolder holder, int position) {
        DetailFragment detailFragment = DetailFragment.newInstance(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detailFragment.setSharedElementEnterTransition(new DetailTransition());
            detailFragment.setEnterTransition(new Fade());
            detailFragment.setExitTransition(new Fade());
            detailFragment.setSharedElementReturnTransition(new DetailTransition());
        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(holder.getImageView(), getResources().getString(R.string.image_transition))
                .replace(R.id.container, detailFragment)
                .addToBackStack(null)
                .commit();
    }

    private void initData() {
        mData = new ArrayList<>();

        mData.add(Pair.create(R.string.taeyeon, R.drawable.taeyeon));
        mData.add(Pair.create(R.string.jessica, R.drawable.jessica));
        mData.add(Pair.create(R.string.sunny, R.drawable.sunny));
        mData.add(Pair.create(R.string.tiffany, R.drawable.tiffany));
        mData.add(Pair.create(R.string.yuri, R.drawable.yuri));
        mData.add(Pair.create(R.string.yoona, R.drawable.yoona));
    }


}
