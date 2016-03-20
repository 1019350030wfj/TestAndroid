package com.jayden.testandroid.customview.recyclerview;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jayden.testandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/3/15.
 */
public class RecyclerViewUseAndFlow extends Activity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recycler);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        //layoutManager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //set adapter
        final List<String> data = new ArrayList<>();
        for (int i=0; i<30; i++) {
            data.add("item" + i);
        }

        final StringAdapter adapter = new StringAdapter<String>(data);

        adapter.setOnItemClickListener(new StringAdapter.OnItemClickListener() {
            @Override
            public void onClick(int layoutPos, int adapterPos) {
                Log.d("jayden","layoutPOs = " + layoutPos + " adapterPos = " + adapterPos);
                adapter.notifyDataSetChanged();
            }
        });

        mRecyclerView.setAdapter(adapter);

        //set ItemDecoration
        mRecyclerView.addItemDecoration(new JaydenItemDecoration(20));


        //0代表不拖拽， 然后向右滑动
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int pos = viewHolder.getAdapterPosition();
                Toast.makeText(RecyclerViewUseAndFlow.this,"Pos = " + pos,Toast.LENGTH_LONG).show();
//
//                data.remove(pos);
                data.add(0, "insert 0");
                adapter.notifyItemInserted(0);
                int posInsert = viewHolder.getAdapterPosition();

                Toast.makeText(RecyclerViewUseAndFlow.this,"Pos = " + posInsert,Toast.LENGTH_LONG).show();
//
                return true;
            }

            //在这个回到我们来处理滑动回调
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                Log.d("jayd","Pos = " + pos);
                Toast.makeText(RecyclerViewUseAndFlow.this,"Pos = " + pos,Toast.LENGTH_LONG).show();
//
//                data.remove(pos);
                data.add(0, "insert 0");
                adapter.notifyItemInserted(0);
                int posInsert = viewHolder.getAdapterPosition();
                Log.d("jayd", "posInsert = " + posInsert);
                int layoutPos = viewHolder.getLayoutPosition();
                Log.d("jayd", "layoutPos = " + layoutPos);
//                Toast.makeText(RecyclerViewUseAndFlow.this,"Pos = " + posInsert,Toast.LENGTH_LONG).show();
//                adapter.notifyItemRemoved(pos);
            }
        }).attachToRecyclerView(mRecyclerView);
    }

    private static class StringAdapter<T> extends RecyclerView.Adapter<StringAdapter.ViewHolder> {

        private List<T> mData;
        private OnItemClickListener mListener;

        public void setOnItemClickListener(OnItemClickListener mListener) {
            this.mListener = mListener;
        }

        public StringAdapter(List<T> data) {
            this.mData = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(View.inflate(parent.getContext(),R.layout.item,null));
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(holder.getLayoutPosition(),holder.getAdapterPosition());
                }
            });
            holder.textView.setText(mData.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        public interface OnItemClickListener {
            void onClick(int layoutPos,int adapterPos);
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            public ViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.text);
            }
        }
    }

    //ItemDecoration
    private static class JaydenItemDecoration extends RecyclerView.ItemDecoration {

        private int mTopSpace;

        public JaydenItemDecoration(int mTopSpace) {
            this.mTopSpace = mTopSpace;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) != 0) {
                outRect.set(0,mTopSpace,0,0);
            }
        }
    }
}
