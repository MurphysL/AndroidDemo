package com.example.murphysl.androiddemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * TextView
 *
 * @author: MurphySL
 * @time: 2016/9/20 23:58
 */

public class MyView extends View{

    private Paint paint;
    private float y = 0;
    private Context context;

    public MyView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context , attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("onDraw: ", "onDraw: ");
        if (y == 0.0){
            canvas.drawCircle(getWidth() / 2 , 100 , 50 , paint);
            startDraw();
        }else{
            Log.i("startDraw: ", "startDraw2: ");
            canvas.drawCircle(getWidth() / 2 , 100 + y , 50 , paint);
        }
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    private void startDraw() {
        Log.i("startDraw: ", "startDraw: ");
        ValueAnimator animator = ValueAnimator.ofFloat(0f , 800f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                y = (float) animation.getAnimatedValue();
                Log.i("startDraw: ", "onAnimationUpdate: " + y);
                invalidate();
            }
        });
        animator.setDuration(3000);
        animator.setInterpolator(new LinearInterpolator(context , null));
        animator.start();
    }

}
