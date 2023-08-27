package com.jsycn.pj_project.ui.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 范围裁切没有毛边，强制
 */
public class CircleBgTextView  extends androidx.appcompat.widget.AppCompatTextView
{
    private boolean has = false;
    public CircleBgTextView(@NonNull Context context) {
        super(context);
        init();
    }

    public CircleBgTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleBgTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
       // mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // mPath.addCircle(getWidth()/2,getHeight()/2,Math.min(getWidth()/2,getHeight()/2), Path.Direction.CW);
        //canvas.clipPath(mPath);
        if (!has){
            has = true;
            Log.d("CircleBgTextView","设置圆角");
            setClipToOutline(true);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0,0,getWidth(),getHeight(),Math.min(getWidth()/2,getHeight()/2));
                }
            });
        }


    }




}
