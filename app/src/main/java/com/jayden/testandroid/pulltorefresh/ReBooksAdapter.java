package com.jayden.testandroid.pulltorefresh;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jayden.pulltorefresh.IDataAdapter;
import com.jayden.testandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class ReBooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IDataAdapter<List<Book>> {
    private LayoutInflater inflater;
    private List<Book> books = new ArrayList<Book>();

    public ReBooksAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(inflater.inflate(R.layout.item_book, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        TextView textView = (TextView) holder.itemView;
        textView.setText(books.get(position).getName());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"position = " + position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    @Override
    public void notifyDataChanged(List<Book> data, boolean isRefresh) {
        if (isRefresh) {
            books.clear();
        }
        books.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public List<Book> getData() {
        return books;
    }

    @Override
    public boolean isEmpty() {
        return books.isEmpty();
    }

}

