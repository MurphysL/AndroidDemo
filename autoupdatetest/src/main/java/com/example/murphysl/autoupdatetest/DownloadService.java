package com.example.murphysl.autoupdatetest;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;


import com.example.murphysl.autoupdatetest.db.FileInfo;
import com.example.murphysl.autoupdatetest.db.FileInfoDao;
import com.example.murphysl.autoupdatetest.db.ThreadInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * DownloadService
 *
 * @author: MurphySL
 * @time: 2016/11/18 20:52
 */


public class DownloadService extends Service {
    private static final String TAG = "DownloadService";

    //public static String fileurl = "http://120.76.239.192/萧帮主1.1.apk";
    public static String fileurl = "http://www.imooc.com/mobile/mukewang.apk";
    public static String path = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/download/imooc.apk";
    private ThreadInfo threadInfo = null;
    private DownloadThread downloadThread = null;

    public static String START_UPDATE = "START_UPDATE";
    public static String STOP_UPDATE = "STOP_UPDATE";
    public static String SEND_PROGRESS = "SEND_PROGRESS";

    public static int  UPDATE_OK = 1;

    private FileInfo fileInfo = null;

    private FileInfoDao dao = MyApplication.getMyApplication().getFileInfoDao();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        fileInfo = new FileInfo(path , fileurl, 0 , 0);
        dao.insert(fileInfo);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(null != intent){
            if( null!= intent.getAction() && !"".equals(intent.getAction())){
                if(intent.getAction().equals(START_UPDATE)){
                    new InitThread().start();
                    Log.i(TAG, "onStartCommand: " + START_UPDATE);
                }else{
                    Log.i(TAG, "onStartCommand: " + "pause" + downloadThread);
                    if(downloadThread != null){
                        Log.i(TAG, "onStartCommand: " + "pause" + downloadThread);
                        downloadThread.setPause(true);
                        Log.i(TAG, "onStartCommand: " + STOP_UPDATE);
                    }

                }
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == UPDATE_OK){
                Log.i(TAG, "handleMessage: " + fileInfo.getFilename() + "\n"
                        +"\n"+ fileInfo.getMy_url()
                        +"\n"+ fileInfo.getLength()
                        +"\n"+ fileInfo.getProgress());
                downloadThread = new DownloadThread(DownloadService.this , fileInfo);
                downloadThread.start();
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 创建下载信息
     */
    private class InitThread extends Thread{
        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            try {
                URL httpUrl = new URL(fileurl);
                connection = (HttpURLConnection) httpUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                int length = connection.getContentLength();
                Log.i(TAG, "file length: " + length);
                if(length <= 0){
                    return;
                }

                fileInfo.setLength(length);
                dao.insert(fileInfo);

                File file = new File(path);
                if (!file.exists()){
                    Log.i(TAG, "run: " + file.getAbsolutePath());
                    file.createNewFile();
                }

                raf = new RandomAccessFile(file , "rwd");

                raf.setLength(length);

                Message message = new Message();
                message.what = UPDATE_OK;
                handler.sendMessage(message);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(connection != null && raf != null){
                    connection.disconnect();
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            super.run();
        }
    }
}
