package com.example.murphysl.autoupdatetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivty";
    private TextView tv_progress;
    private ProgressBar pb_progress;
    private Button confirm;
    private Button cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        IntentFilter intentFilter = new IntentFilter(DownloadService.SEND_PROGRESS);
        registerReceiver(broadcastReceiver , intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    private void startUpdate() {
        Log.i(TAG, "startUpdate: " + "start");
        Intent intent = new Intent(MainActivity.this , DownloadService.class);
        intent.setAction(DownloadService.START_UPDATE);
        startService(intent);
    }

    private void stopUpdate(){
        Intent intent = new Intent(MainActivity.this , DownloadService.class);
        intent.setAction(DownloadService.STOP_UPDATE);
        startService(intent);
    }

    private void initView() {
        tv_progress = (TextView) findViewById(R.id.tx_progress);
        pb_progress = (ProgressBar) findViewById(R.id.pb_progress);
        confirm = (Button) findViewById(R.id.confrim);
        cancle = (Button) findViewById(R.id.cancle);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startUpdate();
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopUpdate();
            }
        });
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int progress = intent.getIntExtra("progress" , 0);
            Log.i(TAG, "onReceive: " + progress);
            if(progress < 100){
                tv_progress.setText("已下载：" +progress + "%");
                pb_progress.setProgress(progress);
            }else{
                tv_progress.setText("下载完成");
            }

        }
    };

}
