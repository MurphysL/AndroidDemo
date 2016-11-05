package com.example.murphysl.todo;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

/**
 * NewActivity
 *
 * @author: MurphySL
 * @time: 2016/11/6 0:45
 */


public class NewActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.plan_fragment);
    }
}
