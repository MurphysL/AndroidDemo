package com.xbei.murphysl.recyclerviewexample;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * LoadMoreListener
 *
 * @author: MurphySL
 * @time: 2017/1/23 15:22
 */


public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {
    private static final String TAG = "LoadMoreListener";

    private boolean loading = true;

    private int currentPage = 1;

    private LinearLayoutManager linearLayoutManager;
    private int firstVisibleItem, visibleItemCount, totalItemCount , lastVisibleItem;

    public LoadMoreListener(LinearLayoutManager linearLayoutManager){
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        visibleItemCount  = recyclerView.getChildCount();
        totalItemCount = linearLayoutManager.getItemCount();
        firstVisibleItem  = linearLayoutManager.findFirstVisibleItemPosition();
        lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
        Log.i(TAG, "firstVisibleItem: " + firstVisibleItem
                +" lastVisibleItem: " + lastVisibleItem
                + " visibleItemCount" + visibleItemCount
        +" totalItemCount" + totalItemCount);

        if(!loading && visibleItemCount + firstVisibleItem > totalItemCount-3){
            currentPage ++;
            loadMore(currentPage);
        }else{
            loading = false;
        }
    }

    public abstract void loadMore(int currentPage);
}
