package com.example.murphysl.tagslayout;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TagsLayout tl = (TagsLayout) findViewById(R.id.tags);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);

        String[] tags = {"傲之追猎者" , "盲僧" , "殇之木乃伊" , "深渊巨口" , "德玛西亚之力" , "星界游神" , "诺克萨斯之手"};
        for(int i = 0 ; i < tags.length ; i ++){
            TextView tv = new TextView(this);
            tv.setPadding(30 , 30 , 30 , 30);
            tv.setText(tags[i]);
            tv.setTextSize(25);
            tv.setTextColor(Color.WHITE);
            tv.setBackgroundResource(R.color.colorPrimary);
            tl.addView(tv , layoutParams);
        }
    }
}
