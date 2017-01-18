package com.xbei.murphysl.murphysbottleloading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * MurphySBottleView
 *
 * @author: MurphySL
 * @time: 2017/1/14 13:34
 */


public class MurphySBottleView extends View {

    private int w , h;
    private Paint paint;
    private PathMeasure pathMeasure;
    private Path mPath;
    private Path mDst;
    private float length;
    private float d;

    public MurphySBottleView(Context context) {
        super(context);
    }

    public MurphySBottleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(15);
        paint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
        mDst = new Path();
        mPath.addCircle(100 , 100 , 50 , Path.Direction.CW);

        pathMeasure = new PathMeasure();
        pathMeasure.setPath(mPath,true);
        length = pathMeasure.getLength();

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0 , 1);
        valueAnimator.setDuration(1000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                d = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    public MurphySBottleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MurphySBottleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        w = measureHight(heightMeasureSpec);
        h = measureWidth(widthMeasureSpec);
        setMeasuredDimension(w , h);
    }

    private int measureWidth(int widthMeasureSpec){
        int result;
        int specMode = MeasureSpec.getMode(widthMeasureSpec);
        int specSize = MeasureSpec.getSize(widthMeasureSpec);

        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else{
            result = 100;
            if(specMode == MeasureSpec.AT_MOST){
                Math.min(result , specSize);
            }
        }

        return result;
    }

    private int measureHight(int heightMeasureSpec){
        int result;
        int specMode = MeasureSpec.getMode(heightMeasureSpec);
        int specSize = MeasureSpec.getSize(heightMeasureSpec);

        if(specMode == MeasureSpec.EXACTLY){
            result = specSize;
        }else{
            result = 200;
            if(specMode == MeasureSpec.AT_MOST){
                Math.min(result , specSize);
            }
        }

        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDst.reset();
        float end = length *d;
        float start = (float) (end - (0.5 - Math.abs(0.5 - d))*length);
        pathMeasure.getSegment(start , end , mDst , true);
        canvas.drawPath(mDst , paint);
    }
}
