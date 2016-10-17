package com.example.murphysl.pinterestlayout;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Drawable> list = new ArrayList<>();
    private MyAdapter adapter;
    private int[] pic = new int[]{R.drawable.i0 , R.drawable.i1 , R.drawable.i10 , R.drawable.i11 ,
            R.drawable.i12 , R.drawable.i13 , R.drawable.i14 , R.drawable.i15 ,
            R.drawable.i16 , R.drawable.i19 , R.drawable.i2 , R.drawable.i20 ,
            R.drawable.i21 , R.drawable.i22 , R.drawable.i23 , R.drawable.i24 ,
            R.drawable.i18, R.drawable.i17, R.drawable.i9, R.drawable.i3 ,
            R.drawable.i4 , R.drawable.i5 , R.drawable.i7 , R.drawable.i8 ,
            R.drawable.i26 , R.drawable.i25 , R.drawable.i7 , R.drawable.i8 ,};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initData();

        adapter = new MyAdapter(this , list);
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3 , StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                Toast.makeText(MainActivity.this , "click" + pos , Toast.LENGTH_SHORT).show();
                adapter.addItem(pos);
            }

            @Override
            public void onItemLongClick(View view, int pos) {
                adapter.deleteItem(pos);
            }
        });
    }

    private void initData() {
        for(int i = 0 ; i < 26 ; i ++){
            Resources resources = this.getResources();
            Drawable drawable = resources.getDrawable(pic[i]);
            list.add(drawable);
        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rc);
    }
}
