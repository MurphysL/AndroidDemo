package com.example.murphysl.autoupdatetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.murphysl.autoupdatetest.db.ThreadInfo;
import com.example.murphysl.autoupdatetest.db.ThreadInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tv_progress;
    private ProgressBar pb_progress;
    private Button confirm;
    private Button cancle;

    public static String START_UPDATE = "START_UPDATE";
    public static String STOP_UPDATE = "STOP_UPDATE";

    private String url = "http://120.76.239.192/萧帮主1.1.apk";
    private String path = "path = Environment.getExternalStorageDirectory().getAbsolutePath()" + "/download/temp.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        ThreadInfoDao dao = MyApplication.getMyApplication().getDao();
        QueryBuilder queryBuilder = dao.queryBuilder();
        List<ThreadInfo> list = queryBuilder.where(ThreadInfoDao.Properties.ThreadName.eq("url")).list();
        if(list.size() == 0){
            startUpdate();
        }else if(list.get(0).getIsFinish()){

        }
        startUpdate();
    }

    private void startUpdate() {
        Intent intent = new Intent(MainActivity.this , DownloadService.class);
        intent.putExtra("state" , START_UPDATE);
        startService(intent);
    }

    private void stopUpdate(){
        Intent intent = new Intent(MainActivity.this , DownloadService.class);
        intent.putExtra("state" , STOP_UPDATE);
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


}
