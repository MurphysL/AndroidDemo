package com.example.murphysl.autoupdatetest;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.murphysl.autoupdatetest.db.ThreadInfo;
import com.example.murphysl.autoupdatetest.db.ThreadInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * DownloadService
 *
 * @author: MurphySL
 * @time: 2016/11/18 20:52
 */


public class DownloadService extends Service {

    private static final String TAG = "DownloadService";
    private String url;
    private String path;
    private String state;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        state = intent.getStringExtra("state");
        if(state.equals(MainActivity.START_UPDATE)){
            //startUpdate();
            Log.i(TAG, "onStartCommand: " + MainActivity.START_UPDATE);
        }else{
            Log.i(TAG, "onStartCommand: " + MainActivity.STOP_UPDATE);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void startUpdate() {
        ThreadInfoDao dao = MyApplication.getMyApplication().getDao();

        QueryBuilder queryBuilder = dao.queryBuilder();
        List<ThreadInfo> list = queryBuilder.where(ThreadInfoDao.Properties.ThreadName.eq("url")).list();
        if(list.size() == 0){
            //ThreadInfo threadInfo = new ThreadInfo()
        }

        new DownloadThread(url);
    }
}
