package com.xbei.murphysl.recyclerviewexample;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List data = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        for(int i = 0 ;i < 10 ;i ++){
            Integer in = new Integer(i);
            data.add(in);
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this , data);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {
                Snackbar.make(view , "Click " + pos , Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void OnLongItemClick(View view, int pos) {
                adapter.deleteItem(pos);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
