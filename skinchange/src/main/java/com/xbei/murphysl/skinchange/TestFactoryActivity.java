package com.xbei.murphysl.skinchange;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * TestFactoryActivity
 *
 * @author: MurphySL
 * @time: 2017/1/6 9:12
 */


public class TestFactoryActivity extends AppCompatActivity {
    private static final String TAG = "TestFactoryActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LayoutInflaterCompat.setFactory(layoutInflater, new LayoutInflaterFactory() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                for(int i = 0 ;i < attrs.getAttributeCount() ; i++){
                    Log.i(TAG, "onCreateView: " + attrs.getAttributeName(i));
                    Log.i(TAG, "onCreateView: " + attrs.getAttributeValue(i));
                }
                if(name.equals("TextView")){
                    return new EditText(context , attrs);
                }else{
                    return null;
                }
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_factory);
    }
}
