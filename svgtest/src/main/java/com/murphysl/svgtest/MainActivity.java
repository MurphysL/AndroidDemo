package com.murphysl.svgtest;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void animArrow(View v){
        ImageView imageView = (ImageView) v;
        Drawable drawable = imageView.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
    }

    public void cancelAnim(View v){
        ImageView view = (ImageView) v;
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getDrawable(R.drawable.anim_cancel);
        view.setImageDrawable(drawable);
        if(drawable != null){
            drawable.start();
        }
    }

    public void searchAnim(View v){
        ImageView view = (ImageView) v;
        Drawable drawable = view.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
    }

    public void pathAnim(View v){
        ImageView view = (ImageView) v;
        Drawable drawable = view.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable) drawable).start();
        }
    }
}
