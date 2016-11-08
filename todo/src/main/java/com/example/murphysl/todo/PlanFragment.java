package com.example.murphysl.todo;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.murphysl.todo.db.dao.DaoMaster;
import com.example.murphysl.todo.db.dao.DaoSession;
import com.example.murphysl.todo.db.dao.NoteDao;
import com.example.murphysl.todo.db.entity.Todo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * PlanFragment
 *
 * @author: MurphySL
 * @time: 2016/10/30 10:59
 */

public class PlanFragment extends Fragment {

    private ImageView imageView;
    private RecyclerView recyclerView;
    private RecyclerViewHeader header;

    private List<Todo> todos;

    /**
     * onCreateView
     * onCreate
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.plan_fragment , container , false);
        initData();
        initView(root);
        initPic();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initView(View root) {
        imageView = (ImageView) root.findViewById(R.id.bing_pic);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler);
        header = (RecyclerViewHeader) root.findViewById(R.id.header);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new PlanAdapter(getContext() , todos));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        header.attachTo(recyclerView);
    }

    private void initData() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "notes-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        NoteDao noteDao = daoSession.getNoteDao();

        QueryBuilder builder = noteDao.queryBuilder();

        todos = builder.list();

    }

    private void initPic() {

        Log.i("test", "initPic: " +"加载成功");

        Glide.with(this)
                .load("http://tu.ihuan.me/api/bing")
                .centerCrop()
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .diskCacheStrategy( DiskCacheStrategy.NONE )//跳过磁盘缓存
                .skipMemoryCache( true )//跳过内存
                .into(imageView);

        //getContext!!!!
        Glide.with(this)
                .load("http://tu.ihuan.me/api/bing")
                .asBitmap()
                .diskCacheStrategy( DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch s1 = palette.getVibrantSwatch();//getVibrantSwatch()可能返回null
                                if(null != s1) {
                                    ((MainActivity) getActivity()).changeColor(s1);
                                    //title.setTextColor(s1.getRgb());
                                    //content.setTextColor(s1.getRgb());
                                }
                            }
                        });
                    }
        });

    }

}
