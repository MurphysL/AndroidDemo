package com.example.murphysl.todo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.murphysl.todo.db.dao.DaoMaster;
import com.example.murphysl.todo.db.dao.DaoSession;
import com.example.murphysl.todo.db.dao.NoteDao;
import com.example.murphysl.todo.db.entity.Todo;

import org.greenrobot.greendao.query.QueryBuilder;

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
    private List<Todo> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.history_fragment , container , false);
        initData();
        initView(root);
        return root;
    }

    private void initData() {

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getContext(), "notes-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        NoteDao noteDao = daoSession.getNoteDao();

        QueryBuilder builder = noteDao.queryBuilder();

        list = builder.list();
    }

    private void initView(View root) {
        recyclerView = (RecyclerView) root.findViewById(R.id.recycle_view);
        recyclerView.setAdapter(new TimeLineAdapter(getContext() , list));
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
