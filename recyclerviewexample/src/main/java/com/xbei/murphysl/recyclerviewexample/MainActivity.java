package com.xbei.murphysl.recyclerviewexample;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.xbei.murphysl.recyclerviewexample.Bean.GankioRandomBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<GankioRandomBean.ResultsBean> list = new ArrayList<>();
    private GankioRandomBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {

        OkHttpClient client = new OkHttpClient();

        Request.Builder builder = new Request.Builder();
        final Request request = builder.get().url("http://gank.io/api/random/data/Android/20").build();

        okhttp3.Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i(TAG + "OkHttp", "onFailure: " + e);
            }

            @Override
            public void onResponse(okhttp3.Call call, final Response response) throws IOException {
                final String con = response.body().string();
                Gson gson = new Gson();
                bean = gson.fromJson(con , GankioRandomBean.class);
                if(!bean.isError() && bean.getResults().size() > 0){
                    Log.i(TAG, "onResponse: " + bean.getResults().size());
                    initDataList();
                }

            }
        });
    }

    public void initDataList(){
        for(int i = 0;i < bean.getResults().size() ; i ++){
            list.add(bean.getResults().get(i));
            Log.i(TAG, "initDataList: " + bean.getResults().get(i).getDesc());
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this , list);
      /*  adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int pos) {
                Snackbar.make(view , "Click " + pos , Snackbar.LENGTH_SHORT).show();
            }
        });*/
        recyclerView.setAdapter(adapter);
    }
}
