package com.example.murphysl.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Model> list = new ArrayList<Model>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this , LinearLayoutManager.VERTICAL ,false));

        initData();

        recyclerView.setAdapter(new MyAdapter());

    }

    private void initData() {
        for(int i = 0 ; i < 14 ; i ++){
            Model model = new Model();
            model.viewType = (int)(Math.random() * 2) + 1;
            model.title = "" + i;

            list.add(model);
        }
    }
}
