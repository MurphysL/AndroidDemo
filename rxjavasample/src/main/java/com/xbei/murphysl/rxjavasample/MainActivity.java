package com.xbei.murphysl.rxjavasample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: " + Thread.currentThread().getName());
    }

    public void traLoad(View v){
        Log.i(TAG, "traLoad: ");
        startActivity(new Intent(MainActivity.this , Tradition.class));
    }

    public void rxLoad(View v){
        startActivity(new Intent(MainActivity.this , RxjavaActivity.class));
    }
}
