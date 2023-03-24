package com.jsycn.pj_project.ui.widget.view;

import static com.jsycn.pj_project.core.utils.MyUtilsKt.sp2px;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import androidx.annotation.ColorInt;

import com.jsycn.pj_project.R;

/**
 * create by pj on 2019/7/23
 * 刺.刺.刺激...
 */
public class MyProgressBar extends ProgressBar {
    private String msg="已抢58%";
    @ColorInt
    private int msgColor;
    private float msgSize;
    Rect rect = new Rect();
    Paint p;
    private ValueAnimator animator;

    public MyProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取文字大小和文字颜色
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MsgProgressBar);
        msgSize = typedArray.getDimension(R.styleable.MsgProgressBar_msg_size,sp2px(10));
        msgColor=typedArray.getColor(R.styleable.MsgProgressBar_msg_color,getResources().getColor(R.color.text_color_3));
    }

    {
        p= new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextAlign(Paint.Align.CENTER);//文字居中
    }
    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        p.setTextSize(msgSize);
        p.setColor(msgColor);
        //测量文字宽高
        p.getTextBounds(msg, 0, msg.length(), rect);
        float offset = (rect.top + rect.bottom) / 2;
        canvas.drawText(msg,getWidth()/2,getHeight()/2-offset,p);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        invalidate();
    }

    public int getMsgColor() {
        return msgColor;
    }

    public void setMsgColor(@ColorInt int msgColor) {
        this.msgColor = msgColor;
        invalidate();
    }

    public float getMsgSize() {
        return msgSize;
    }

    public void setMsgSize(int msgSize) {
        this.msgSize = sp2px(msgSize);
        invalidate();
    }

    public void setMsgProgress(int progress) {
        getAnimator().setIntValues(getProgress(),progress);
        setProgress(progress);
        getAnimator().start();
    }
    public ValueAnimator getAnimator(){
        if(animator==null){
            animator = ValueAnimator.ofInt();
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    setProgress((Integer) animation.getAnimatedValue());
                }
            });
        }
        return animator;
    }
}
