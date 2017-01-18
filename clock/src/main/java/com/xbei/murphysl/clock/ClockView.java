package com.xbei.murphysl.clock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * ClockView
 *
 * @author: MurphySL
 * @time: 2017/1/14 12:24
 */


public class ClockView extends View {

    private int w , h;
    public ClockView(Context context) {
        this(context , null , 0 , 0);
    }

    public ClockView(Context context, AttributeSet attrs) {
        this(context, attrs , 0 ,0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr , 0);
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        w = MeasureSpec.getSize(widthMeasureSpec);
        h = MeasureSpec.getSize(heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextSize(20);
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        canvas.drawCircle(w/2 , h/2 , w/2 - 10 , paint);

        Paint paint1 = new Paint();
        paint1.setTextSize(30);
        paint1.setAntiAlias(true);
        paint1.setStrokeWidth(10);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(w/2 , h/2 , 15 , paint1);

        for(int i = 0 ;i < 12 ;i ++){
            if(i == 0 || i == 3 || i == 6 || i == 9){
                canvas.drawLine(w/2 , h/2-w/2+10 , w/2 , h/2-w/2+70 , paint1);
                canvas.drawText(i + "" , w/2 -8, h/2 - w/2 + 100 , paint1);
            }else{
                canvas.drawLine(w/2 , h/2-w/2+10 , w/2 , h/2-w/2+40 , paint);
                canvas.drawText(i + "" , w/2 -8, h/2 - w/2 + 60 , paint);
            }

            canvas.rotate(30 , w / 2 , h / 2);
        }

        canvas.translate(w/2 , h /2);
        Paint hour = new Paint();
        hour.setStrokeWidth(20);
        hour.setAntiAlias(true);
        Paint min = new Paint();
        min.setStrokeWidth(15);
        min.setAntiAlias(true);
        canvas.drawLine(0 , 0 , 100 , 150 , hour);
        canvas.drawLine(0 , 0 , 0 , 250 , min);
        super.onDraw(canvas);
    }
}
