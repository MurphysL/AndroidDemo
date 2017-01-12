package com.xbei.murphysl.murphystablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * MurphySTabLayout
 *
 * @author: MurphySL
 * @time: 2017/1/12 14:17
 */


public class MurphySTabLayout extends RelativeLayout {
    
    private String title;
    private int titleColor;
    private float titleSize;

    private String leftTitle;
    private int leftTitleColor;
    private float leftTitleSize;
    private int leftBackground;

    private String rightTitle;
    private int rightTitleColor;
    private float rightTitleSize;
    private int rightBackground;
    
    public MurphySTabLayout(Context context) {
        super(context , null , 0 , 0);
    }

    public MurphySTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs , 0 , 0);

        TypedArray ta = context.obtainStyledAttributes(attrs ,R.styleable.MurphySTabLayout);
        title = ta.getString(R.styleable.MurphySTabLayout_title);
        titleColor = ta.getColor(R.styleable.MurphySTabLayout_titleColor , 0);
        titleSize = ta.getFloat(R.styleable.MurphySTabLayout_titleSize , 0);

        leftTitle = ta.getString(R.styleable.MurphySTabLayout_leftTitle);
        leftTitleColor = ta.getColor(R.styleable.MurphySTabLayout_leftTitleColor , 0);
        leftTitleSize = ta.getFloat(R.styleable.MurphySTabLayout_leftTitleSize , 0);
        leftBackground = ta.getColor(R.styleable.MurphySTabLayout_leftBackground , 0);

        rightTitle = ta.getString(R.styleable.MurphySTabLayout_rightTitle);
        rightTitleColor = ta.getColor(R.styleable.MurphySTabLayout_rightTitleColor , 0);
        rightTitleSize = ta.getFloat(R.styleable.MurphySTabLayout_rightTitleSize , 0);
        rightBackground = ta.getColor(R.styleable.MurphySTabLayout_rightBackground , 0);

        Button lb = new Button(context);
        lb.setBackgroundColor(leftBackground);
        lb.setText(leftTitle);
        lb.setTextColor(leftTitleColor);
        lb.setTextSize(leftTitleSize);

        Button rb = new Button(context);
        rb.setBackgroundColor(rightBackground);
        rb.setText(rightTitle);
        rb.setTextColor(rightTitleColor);
        rb.setTextSize(rightTitleSize);

        TextView tv = new TextView(context);
        tv.setText(title);
        tv.setTextColor(titleColor);
        tv.setTextSize(titleSize);
        tv.setGravity(CENTER_IN_PARENT);

        setBackgroundColor(Color.WHITE);

        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(ALIGN_PARENT_LEFT);
        addView(lb , lp);

        LayoutParams rp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        rp.addRule(ALIGN_PARENT_RIGHT);
        addView(rb , rp);

        LayoutParams tp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT , ViewGroup.LayoutParams.WRAP_CONTENT);
        tp.addRule(CENTER_IN_PARENT);
        addView(tv , tp);
        
    }

    public MurphySTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr , 0);
    }

    public MurphySTabLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes );
    }
}
