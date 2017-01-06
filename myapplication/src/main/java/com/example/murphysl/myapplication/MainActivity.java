package com.example.murphysl.myapplication;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences()
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("name" , "yq");
        editor.putInt("age" , 15);
        editor.putBoolean("sex" , true);
        editor.commit();

        Log.i(TAG, "onCreate: " + pref.getString("name" , "") + pref.getInt("age" , 0));*/

    }
}
