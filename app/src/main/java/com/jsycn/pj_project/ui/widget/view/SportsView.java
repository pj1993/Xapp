package com.jsycn.pj_project.ui.widget.view;

import static com.jsycn.pj_project.core.utils.MyUtilsKt.dp2px;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


/**
 * create by pj on 2019/7/12
 * 刺.刺.刺激...
 */
public class SportsView extends View {

    private static final int CIRCLE_COLOR= Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR= Color.parseColor("#FF4081");
    float RING_WIDTH= dp2px(10);
    float RADIUS=dp2px(150);
    Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
    public SportsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制环
        p.setStyle(Paint.Style.STROKE);
        p.setColor(CIRCLE_COLOR);
        p.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth()/2,getHeight()/2,RADIUS,p);

        //绘制 进度条
        p.setColor(HIGHLIGHT_COLOR);
        p.setStrokeCap(Paint.Cap.ROUND);//进度条头部圆形
        canvas.drawArc(getWidth()/2-RADIUS,getHeight()/2-RADIUS,getWidth()/2+RADIUS,getHeight()/2+RADIUS,-90,225,false,p);
    }
}
