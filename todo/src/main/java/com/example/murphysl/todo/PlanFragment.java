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
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * PlanFragment
 *
 * @author: MurphySL
 * @time: 2016/10/30 10:59
 */

public class PlanFragment extends Fragment {

    private static final String TAG = "PlanFragment";

    private ImageView imageView;
    private EditText title;
    private EditText content;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.plan_fragment , container , false);
        imageView = (ImageView) root.findViewById(R.id.image);
        title = (EditText) root.findViewById(R.id.et_title);
        content = (EditText) root.findViewById(R.id.et_content);

        initPic();

        /*  private boolean hasMeasured = false;
         ViewTreeObserver vto = root.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (hasMeasured == false) {
                    int height = imageView.getMeasuredHeight();
                    int width = imageView.getMeasuredWidth();
                    hasMeasured = true;
                }
                return true;
            }
        });*/
        return root;
    }

    private void initPic() {

        Glide.with(this)
                .load("http://tu.ihuan.me/api/bing")
                .centerCrop()
                .crossFade() //设置淡入淡出效果，默认300ms，可以传参
                .diskCacheStrategy( DiskCacheStrategy.NONE )//跳过磁盘缓存
                .skipMemoryCache( true )//跳过内存
                .into(imageView);

        //getContext!!!!
        Glide.with(this)
                .load("http://tu.ihuan.me/api/bing")
                .asBitmap()
                .diskCacheStrategy( DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(Palette palette) {
                                Palette.Swatch s1 = palette.getVibrantSwatch();//getVibrantSwatch()可能返回null
                                if(null != s1) {
                                    ((MainActivity) getActivity()).changeColor(s1);
                                    title.setTextColor(s1.getRgb());
                                    content.setTextColor(s1.getRgb());
                                }
                            }
                        });
                    }
        });

    }

}
