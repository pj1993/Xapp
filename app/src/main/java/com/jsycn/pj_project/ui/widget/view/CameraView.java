package com.jsycn.pj_project.ui.widget.view;

import static com.jsycn.pj_project.core.utils.MyUtilsKt.getBitmap;
import static com.jsycn.pj_project.core.utils.MyUtilsKt.getZForCamera;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jsycn.pj_project.R;


/**
 * create by pj on 2019/7/12
 * 刺.刺.刺激...
 */
public class CameraView extends View {
    Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
    Camera camera=new Camera();
    float topFlip=0;
    float bottomFlip=0;
    float flipRotation=0;//旋转角度

    public float getTopFlip() {
        return topFlip;
    }

    public void setTopFlip(float topFlip) {
        this.topFlip = topFlip;
        invalidate();
    }

    public float getBottomFlip() {
        return bottomFlip;
    }

    public void setBottomFlip(float bottomFlip) {
        this.bottomFlip = bottomFlip;
        invalidate();
    }

    public float getFlipRotation() {
        return flipRotation;
    }

    public void setFlipRotation(float flipRotation) {
        this.flipRotation = flipRotation;
        invalidate();
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    {

        camera.setLocation(0,0,getZForCamera());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制上半部分
        canvas.save();
        canvas.translate(100+400/2,100+400/2);//移动回来
        canvas.rotate(-flipRotation);
        camera.save();
        camera.rotateX(topFlip);
        camera.applyToCanvas(canvas);//3维变换
        camera.restore();
        canvas.clipRect(-400,-400,400,0);//切割，保留半部分
        canvas.rotate(flipRotation);
        canvas.translate(-(100+400/2),-(100+400/2));//移动到原点
        canvas.drawBitmap(getBitmap(R.drawable.ttt,400,getResources()),100,100,p);
        canvas.restore();

        //绘制下半部分
        canvas.save();
        canvas.translate(100+400/2,100+400/2);//移动回来
        canvas.rotate(-flipRotation);//旋转回来
        camera.save();
        camera.rotateX(bottomFlip);
        camera.applyToCanvas(canvas);//3维变换
        camera.restore();
        canvas.clipRect(-400,0,400,400);//切割，保留下半部分
        canvas.rotate(flipRotation);//旋转一个角度
        canvas.translate(-(100+400/2),-(100+400/2));//移动到原点
        canvas.drawBitmap(getBitmap(R.drawable.ttt,400,getResources()),100,100,p);
        canvas.restore();



    }
}
