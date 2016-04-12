package com.jayden.testandroid.pulltorefresh;

import com.jayden.pulltorefresh.IDataCacheLoader;
import com.jayden.pulltorefresh.IDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jayden on 2016/4/12.
 * Email : 1570713698@qq.com
 */
public class BooksDataSource implements IDataSource<List<Book>>,IDataCacheLoader<List<Book>> {

    private int page = 1;
    private int maxPage = 10;

    /**
     * 加载缓存<br>
     * 注意这个方法执行于UI线程，不要做太过耗时的操作<br>
     * 每次刷新的时候触发该方法，该方法在DataSource refresh之前执行<br>
     *
     * @param isEmpty
     *            adapter是否有数据，这个值是adapter.isEmpty()决定
     * @return 加载的数据，返回后会执行adapter.notifyDataChanged(data, true)<br>
     *         相当于refresh执行后adapter.notifyDataChanged(data, true)
     */
    @Override
    public List<Book> loadCache(boolean isEmpty) {
        if (isEmpty) {
            List<Book> books = new ArrayList<Book>();
            for (int i = 0; i < 10; i++) {
                books.add(new Book("cache  page 1  Java编程思想 " + i, 108.00d));
            }
            return books;
        }
        return null;
    }

    @Override
    public List<Book> refresh() throws Exception {
        return loadBooks(1);
    }

    @Override
    public List<Book> loadMore() throws Exception {
        return loadBooks(page + 1);
    }

    private List<Book> loadBooks(int page) throws Exception {
        // 这里用百度首页模拟网络请求，如果网路出错的话，直接抛异常不会执行后面的获取books的语句
        Thread.sleep(3000);

        List<Book> books = new ArrayList<Book>();
        for (int i = 0; i < 20; i++) {
            books.add(new Book("page" + page + "  Java编程思想 " + i, 108.00d));
        }
        this.page = page;
        return books;
    }

    @Override
    public boolean hasMore() {
        return page < maxPage;
    }
}
