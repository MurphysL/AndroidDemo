package com.example.murphysl.androiddemo.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.RelativeLayout;

import com.example.murphysl.androiddemo.BezierEvaluator;

import java.util.Random;

/**
 * HeartLayout
 *
 * @author: MurphySL
 * @time: 2016/9/18 17:43
 */

public class HeartLayout extends RelativeLayout {

    private Context context;

    private int width;
    private int height;
    private int[] colors={Color.RED, Color.YELLOW, Color.GRAY, Color.GREEN, Color.BLUE};


    public HeartLayout(Context context) {
        this(context , null);
    }

    public HeartLayout(Context context, AttributeSet attrs) {
        this(context, attrs , 0);
    }

    public HeartLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width=getWidth();
        height=getHeight();
    }

    public  void addHeart() {
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        final HeartImageView imageView=new HeartImageView(context);
        imageView.setColor(colors[new Random().nextInt(colors.length)]);
        imageView.setVisibility(INVISIBLE);
        addView(imageView, params);

        imageView.post(new Runnable() {
            @Override
            public void run() {

                int point1x=new Random().nextInt((width));
                int point2x=new Random().nextInt((width));
                int point1y=new Random().nextInt(height/2)+height/2;
                int point2y=point1y-new Random().nextInt(point1y);

                int endX=new Random().nextInt(width/2);
                int endY=point2y-new Random().nextInt(point2y);

                ValueAnimator translateAnimator=ValueAnimator.ofObject(
                        new BezierEvaluator(new PointF(point1x, point1y), new PointF(point2x, point2y)),
                        new PointF(width/2-imageView.getWidth()/2, height-imageView.getHeight()),
                        new PointF(endX, endY));

                translateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF pointF= (PointF) animation.getAnimatedValue();
                        imageView.setX(pointF.x);
                        imageView.setY(pointF.y);
                    }
                });
                translateAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        removeView(imageView);
                    }
                });
                TimeInterpolator[] timeInterpolator={new LinearInterpolator(), new AccelerateDecelerateInterpolator(), new DecelerateInterpolator(), new AccelerateInterpolator()};
                translateAnimator.setInterpolator(timeInterpolator[new Random().nextInt(timeInterpolator.length)]);
                ObjectAnimator translateAlphaAnimator=ObjectAnimator.ofFloat(imageView, View.ALPHA,  1f, 0f);
                translateAlphaAnimator.setInterpolator(new DecelerateInterpolator());
                AnimatorSet translateAnimatorSet=new AnimatorSet();
                translateAnimatorSet.playTogether(translateAnimator, translateAlphaAnimator);
                translateAnimatorSet.setDuration(2000);

                ObjectAnimator scaleXAnimator=ObjectAnimator.ofFloat(imageView, View.SCALE_X, 0.5f, 1f);
                ObjectAnimator scaleYAnimator=ObjectAnimator.ofFloat(imageView, View.SCALE_Y, 0.5f, 1f);
                ObjectAnimator alphaAnimator=ObjectAnimator.ofFloat(imageView, View.ALPHA, 0.5f, 1f);
                AnimatorSet enterAnimatorSet=new AnimatorSet();
                enterAnimatorSet.playTogether(scaleXAnimator, scaleYAnimator, alphaAnimator);
                enterAnimatorSet.setDuration(500);
                enterAnimatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        imageView.setVisibility(VISIBLE);
                    }
                });

                AnimatorSet allAnimator=new AnimatorSet();
                allAnimator.playSequentially(enterAnimatorSet, translateAnimatorSet);
                allAnimator.start();
            }
        });

    }

}
