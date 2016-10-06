package com.example.murphysl.androiddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.murphysl.androiddemo.view.HeartLayout;
import com.example.murphysl.androiddemo.view.MyView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_text);
        setContentView(R.layout.activity_text);

//        final HeartLayout layout = (HeartLayout) findViewById(R.id.heart);
//        layout.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                layout.addHeart();
//                return false;
//            }
//        });
    }
}
