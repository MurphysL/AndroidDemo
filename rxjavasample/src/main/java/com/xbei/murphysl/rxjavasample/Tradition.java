package com.xbei.murphysl.rxjavasample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Tradition
 *
 * @author: MurphySL
 * @time: 2017/1/6 16:53
 */


public class Tradition extends AppCompatActivity {
    private static final String TAG = "Tradition";

    private ImageView iv1 , iv2 , iv3 ,iv4;
    private List<File> files = new ArrayList<>();
    private List<ImageView> imageViews = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradition);

        initView();
        initFile();
        load();
    }

    private void initFile() {
        for(int i = 1 ;i <= 4 ;i ++){
            File file = new File(Environment.getExternalStorageDirectory()+ "/skin" + i + ".jpg");
            files.add(file);
        }
    }

    private void initView() {
        iv1 = (ImageView) findViewById(R.id.iv_1);
        iv2 = (ImageView) findViewById(R.id.iv_2);
        iv3 = (ImageView) findViewById(R.id.iv_3);
        iv4 = (ImageView) findViewById(R.id.iv_4);
        imageViews.add(iv1);
        imageViews.add(iv2);
        imageViews.add(iv3);
        imageViews.add(iv4);
    }

    private void load(){
        new Thread(new Runnable() {
            @Override
            public void run() {
               for(int i = 0 ;i < files.size() ;i ++){
                   File file = files.get(i);
                   final ImageView imageView = imageViews.get(i);
                   if(file.getName().endsWith(".jpg")){
                       final Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               imageView.setImageBitmap(bitmap);
                           }
                       });
                   }
               }
            }
        }).start();

    }



}
