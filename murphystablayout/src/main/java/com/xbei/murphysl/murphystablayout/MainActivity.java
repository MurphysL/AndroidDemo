package com.xbei.murphysl.murphystablayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MurphySTabLayout tabLayout = (MurphySTabLayout) findViewById(R.id.murphySTabLayout);
        tabLayout.setTabLayoutClickListener(new MurphySTabLayout.TabLayoutClickListener() {
            @Override
            public void leftClick() {
                Toast.makeText(MainActivity.this , "left" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void rightClick() {
                Toast.makeText(MainActivity.this , "right" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}
