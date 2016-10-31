package com.example.murphysl.test;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * MyAdapter
 *
 * @author: MurphySL
 * @time: 2016/10/21 0:03
 */

public class MyAdapter extends RecyclerView.Adapter{

    private List<Model> list;


    public MyAdapter(List<Model> list) {
        this.list = list;
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
