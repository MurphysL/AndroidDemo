package com.example.murphysl.todo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * HistoryFragment
 *
 * @author: MurphySL
 * @time: 2016/10/30 10:59
 */

public class HistoryFragment  extends Fragment{

    private RecyclerView recyclerView;
    private List<String> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history_fragment , container , false);
        initData();
        initView(root);
        return root;
    }

    private void initData() {
        for(int i = 0 ; i< 10 ; i ++){
            list.add(i + "");
        }
    }

    private void initView(View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.recycle_view);
        recyclerView.setAdapter(new TimeLineAdapter(getContext() , list));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
