package com.example.murphysl.todo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Transformation;

import java.io.IOException;

/**
 * PlanFragment
 *
 * @author: MurphySL
 * @time: 2016/10/30 10:59
 */

public class PlanFragment extends Fragment implements Transformation{

    private ImageView imageView;
    private boolean hasMeasured = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.plan_fragment , container , false);
        imageView = (ImageView) root.findViewById(R.id.image);
        ViewTreeObserver vto = root.getViewTreeObserver();

        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (hasMeasured == false) {
                    int height = imageView.getMeasuredHeight();
                    int width = imageView.getMeasuredWidth();

                    try {
                        initPic(height , width);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    hasMeasured = true;
                }
                return true;
            }
        });
        return root;
    }

    private void initPic(int height , int width) throws IOException {

        Log.i("pic", "initPic: " + height + width);

        Glide.with(this)
                .load("http://tu.ihuan.me/api/bing")
                .fitCenter()
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .into(imageView);

        //getContext!!!!
        Glide.with(this)
                .load("http://tu.ihuan.me/api/bing")
                .asBitmap()
                .override(width , height)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        //imageView.setImageBitmap(resource);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch s1 = palette.getVibrantSwatch();
                                ((MainActivity)getActivity()).changeColor(s1);
                            }
                        });
                    }
        });

    }

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap result = Bitmap.createBitmap(source, x, y, size, size);
        if (result != source) {
            source.recycle();
        }
        return result;
    }

    @Override
    public String key() {
        return null;
    }
}
