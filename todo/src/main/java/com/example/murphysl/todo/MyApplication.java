package com.example.murphysl.todo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.murphysl.todo.db.dao.DaoMaster;
import com.example.murphysl.todo.db.dao.DaoSession;
import com.example.murphysl.todo.db.dao.NoteDao;
import com.example.murphysl.todo.db.entity.Todo;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * MyApplication
 *
 * @author: MurphySL
 * @time: 2016/11/8 9:49
 */


public class MyApplication extends Application {

    private static MyApplication myApplication;

    private MyApplication(){

    }

    public static MyApplication getApplication(){
        if(myApplication == null){
            myApplication = new MyApplication();
        }
        return myApplication;
    }



}
