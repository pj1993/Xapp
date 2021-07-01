package com.jsycn.pj_project.ui.widget.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

import com.jsycn.pj_project.R;


/**
 * create by pj on 2019/7/11
 * 刺.刺.刺激...
 */
public class CircleView extends View {
    private Paint p = null;
    /**
     * 背景色
     */
    private int color = 0;
    public CircleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        color = typedArray.getColor(R.styleable.CircleView_bg_color, getResources().getColor(R.color.color_market_style_progress_bg));
        typedArray.recycle();
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setColor(color);
        float x = ((float) getWidth()) / 2;
        float y = ((float) getHeight()) / 2;
        canvas.drawCircle(x, y, x, p);
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
        invalidate();
    }

}
