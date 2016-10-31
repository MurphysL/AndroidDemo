package com.example.murphysl.thread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        imageView.setImageBitmap(DecodeBitmap.decodeBitmapFromResource(getResources() , R.drawable.i13 , 100 , 100));
    }

    private void initView() {
        imageView = (ImageView) findViewById(R.id.iv);
    }

}
