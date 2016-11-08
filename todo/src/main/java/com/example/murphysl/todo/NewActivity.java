package com.example.murphysl.todo;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.murphysl.todo.db.dao.DaoMaster;
import com.example.murphysl.todo.db.dao.DaoSession;
import com.example.murphysl.todo.db.dao.NoteDao;
import com.example.murphysl.todo.db.entity.Todo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * NewActivity
 *
 * @author: MurphySL
 * @time: 2016/11/6 0:45
 */


public class NewActivity extends AppCompatActivity {

    private EditText et_title;
    private EditText et_content;
    private CoordinatorLayout coordinatorLayout;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    private String title = null;
    private String content = null;
    private String time;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        initView();
        initEvent();
    }

    private void initEvent() {
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time = PickUtils.pickTime(NewActivity.this , toolbar);
                Log.i("test", "onClick: " + time);
                toolbar.setTitle(time);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                title = et_title.getText().toString();
                content = et_content.getText().toString();

                if(!title.equals("") && !content.equals("")){
                    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(NewActivity.this, "notes-db", null);
                    SQLiteDatabase db = helper.getWritableDatabase();
                    DaoMaster daoMaster = new DaoMaster(db);
                    DaoSession daoSession = daoMaster.newSession();
                    NoteDao noteDao = daoSession.getNoteDao();
                    Todo todo = new Todo(null , title , content , time);
                    noteDao.insert(todo);

                    Snackbar.make(coordinatorLayout , "保存成功" , Snackbar.LENGTH_SHORT).show();

                  /*  QueryBuilder builder = noteDao.queryBuilder();
                   *//* builder.where(NoteDao.Properties.Content.eq("2"));
                    List list = builder.list();
                    Todo t = (Todo) list.get(0);
                    Log.i("test", "onClick: " + list.get(0));
                    Log.i("test", "onClick: " + t.getCreateTime());*//*

                    List list2 = builder.list();
                    for(int i = 0 ; i < list2.size() ; i ++){
                        Log.i("test", "onClick: " + ((Todo)list2.get(i)).getTitle());
                    }
                    */

                }else{
                    Snackbar.make(coordinatorLayout, "内容不能为空", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initView() {
        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab_publish);

        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        time = format.format(date);

        toolbar.setTitle(time);
    }

}
