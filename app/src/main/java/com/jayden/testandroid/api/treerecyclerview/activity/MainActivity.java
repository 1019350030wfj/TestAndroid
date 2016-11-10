package com.jayden.testandroid.api.treerecyclerview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jayden.testandroid.R;
import com.jayden.testandroid.api.treerecyclerview.adapter.RecyclerAdapter;
import com.jayden.testandroid.api.treerecyclerview.interfaces.OnScrollToListener;
import com.jayden.testandroid.api.treerecyclerview.model.ItemData;

import java.util.List;

/**
 * @Author Zheng Haibo
 * @PersonalWebsite http://www.mobctrl.net
 * @Description
 */
public class MainActivity extends Activity {

	private RecyclerView recyclerView;

	private RecyclerAdapter myAdapter;

	private LinearLayoutManager linearLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_tree_recyclerview);
		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		linearLayoutManager = new LinearLayoutManager(this);
		recyclerView.setLayoutManager(linearLayoutManager);

		recyclerView.getItemAnimator().setAddDuration(100);
		recyclerView.getItemAnimator().setRemoveDuration(100);
		recyclerView.getItemAnimator().setMoveDuration(200);
		recyclerView.getItemAnimator().setChangeDuration(100);

		myAdapter = new RecyclerAdapter(this);
		recyclerView.setAdapter(myAdapter);
		myAdapter.setOnScrollToListener(new OnScrollToListener() {

			@Override
			public void scrollTo(int position) {
				recyclerView.scrollToPosition(position);
			}
		});
		initDatas();
	}

	private void initDatas() {
		List<ItemData> list = myAdapter.getChildrenByPath("/", 0);
		myAdapter.addAll(list, 0);
	}

}
