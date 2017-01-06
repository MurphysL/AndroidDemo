package com.example.murphysl.autoupdatetest;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.murphysl.autoupdatetest.db.DaoMaster;
import com.example.murphysl.autoupdatetest.db.DaoSession;
import com.example.murphysl.autoupdatetest.db.FileInfoDao;
import com.example.murphysl.autoupdatetest.db.ThreadInfoDao;

/**
 * MyApplication
 *
 * @author: MurphySL
 * @time: 2016/11/18 22:46
 */


public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
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

    public ThreadInfoDao getThreadDao(){
        Log.i(TAG, "getThreadDao: ");
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext() , "THREAD_INFO" , null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        ThreadInfoDao threadInfoDao = daoSession.getThreadInfoDao();

        return threadInfoDao;
    }

    public FileInfoDao getFileInfoDao(){
        Log.i(TAG, "getFileInfoDao: ");
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(getApplicationContext() , "FILE_INFO" , null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        FileInfoDao fileInfoDao = daoSession.getFileInfoDao();

        return fileInfoDao;
    }

}
