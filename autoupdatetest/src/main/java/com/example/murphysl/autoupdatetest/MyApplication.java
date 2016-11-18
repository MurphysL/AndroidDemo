package com.example.murphysl.autoupdatetest;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.murphysl.autoupdatetest.db.DaoMaster;
import com.example.murphysl.autoupdatetest.db.DaoSession;
import com.example.murphysl.autoupdatetest.db.ThreadInfoDao;

/**
 * MyApplication
 *
 * @author: MurphySL
 * @time: 2016/11/18 22:46
 */


public class MyApplication extends Application {
    private static MyApplication myApplication;

    public MyApplication(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    public ThreadInfoDao getDao(){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext() , "notes-db" , null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        ThreadInfoDao threadInfoDao = daoSession.getThreadInfoDao();

        return threadInfoDao;
    }

}
