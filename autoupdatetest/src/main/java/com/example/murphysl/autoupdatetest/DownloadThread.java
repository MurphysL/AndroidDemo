package com.example.murphysl.autoupdatetest;

import android.os.Handler;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * DownloadThread
 *
 * @author: MurphySL
 * @time: 2016/11/18 20:44
 */


public class DownloadThread extends Thread {

    private String url;
    private ProgressBar progressBar;
    private Handler handler;

    public DownloadThread(String url){
        this.handler = handler;
        this.progressBar = progressBar;
        this.url = url;
    }

    @Override
    public void run() {
        startDownload();
        super.run();
    }

    private void startDownload() {
        if(!"".equals(url)){
            try {
                URL httpUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) httpUrl.openConnection();
                connection.setReadTimeout(5000);
                connection.setRequestMethod("GET");

                StringBuffer sb = new StringBuffer();
                //RandomAccessFile raf = new RandomAccessFile()

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
