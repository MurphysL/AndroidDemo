package com.example.murphysl.todo;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * DetailActivity
 *
 * @author: MurphySL
 * @time: 2016/11/8 10:26
 */


public class DetailActivity extends AppCompatActivity {

    private TextView title;
    private TextView content;
    private Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initView();
        initData();

    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        title.setText(bundle.getString("title"));
        content.setText(bundle.getString("content"));
        toolbar.setTitle(bundle.getString("data"));
    }

    private void initView() {
        title = (TextView) findViewById(R.id.detail_title);
        content = (TextView) findViewById(R.id.detail_content);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
    }



}
