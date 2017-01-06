package com.example.murphysl.autoupdatetest;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


import com.example.murphysl.autoupdatetest.db.FileInfo;
import com.example.murphysl.autoupdatetest.db.FileInfoDao;
import com.example.murphysl.autoupdatetest.db.ThreadInfo;
import com.example.murphysl.autoupdatetest.db.ThreadInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * DownloadThread
 *
 * @author: MurphySL
 * @time: 2016/11/18 20:44
 */


public class DownloadThread extends Thread {

    private static final String TAG = "DownloadThread";

    private FileInfo fileInfo;
    private Context context;

    private boolean isPause = false;

    private ThreadInfoDao dao = null;
    private List list = null;
    private ThreadInfo threadInfo;


    public void setPause(boolean pause) {
        isPause = pause;
    }

    public DownloadThread(Context context , FileInfo fileInfo){
        this.fileInfo = fileInfo;
        this.context = context;

        dao = MyApplication.getMyApplication().getThreadDao();
        QueryBuilder<ThreadInfo> qb = dao.queryBuilder();
        list = qb.where(ThreadInfoDao.Properties.ThreadName.eq(DownloadService.fileurl)).list();
    }

    @Override
    public void run() {
        startDownload();
        super.run();
    }

    private void startDownload(){
        Log.i(TAG, "startDownload: ");
        if(list.size() == 0){
            threadInfo = new ThreadInfo(DownloadService.path , 0 , fileInfo.getLength() ,0 ,
                    DownloadService.fileurl);
            dao.insert(threadInfo);
        }else{
            threadInfo = (ThreadInfo) list.get(0);
        }

        String url = threadInfo.getThreadName();
        int start = fileInfo.getProgress() + threadInfo.getIsFinish();
        int length = fileInfo.getLength();
        Log.i(TAG, "startDownload: " + url);
        if(!"".equals(url)){
            RandomAccessFile raf = null;
            InputStream inputStream = null;
            HttpURLConnection connection = null;

            try {
                URL httpUrl = new URL(url);
                connection = (HttpURLConnection) httpUrl.openConnection();
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Range" , "bytes=" + start + "-"+threadInfo.getEnd());

                File file = new File(DownloadService.path );
                raf = new RandomAccessFile(file , "rwd");
                raf.seek(start);

                Log.i(TAG, "startDownload: " + connection.getResponseCode());//206 请求未完成的下载
                inputStream = connection.getInputStream();
                byte[] buffer = new byte[2 * 1024];
                int len = -1;
                long time = System.currentTimeMillis();
                Log.i(TAG, "startDownload: " + "start" + start);
                while((len = inputStream.read(buffer)) != 0){
                    Log.i(TAG, "startDownload: " + len);
                    raf.write(buffer , 0 , len);
                    start += len;
                    if(System.currentTimeMillis() - time >= 1000){
                        Intent intent = new Intent(DownloadService.SEND_PROGRESS);
                        intent.putExtra("progress" , start * 100 / length);
                        context.sendBroadcast(intent);
                    }

                    if(isPause){
                        if(list != null){
                            Log.i(TAG, "startDownload23: " + start * 100 /length);
                            Integer integer = new Integer(start * 100 / length);
                            threadInfo.setIsFinish(integer);
                            dao.update(threadInfo);
                        }
                        return;
                    }
                    Log.i(TAG, "startDownload: " + len);

                }

                dao.delete(threadInfo);

            } catch (MalformedURLException e) {
                try {
                    Toast.makeText(context , connection.getResponseCode() +"" , Toast.LENGTH_LONG);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                connection.disconnect();
                try {
                    raf.close();
                    inputStream.close();
                }catch (Exception e){

                }
            }

        }
    }
}
