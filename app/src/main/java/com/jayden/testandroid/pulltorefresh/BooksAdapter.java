package com.jayden.testandroid.pulltorefresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jayden.pulltorefresh.IDataAdapter;
import com.jayden.testandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class BooksAdapter extends BaseAdapter
        implements IDataAdapter<List<Book>> {

    private List<Book> books = new ArrayList<>();
    private LayoutInflater inflater;

    public BooksAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
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
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_book, parent, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(books.get(position).getName());
        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
