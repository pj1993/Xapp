package com.jsycn.pj_project.ui.widget.view

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import com.jsycn.pj_project.R
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 *@Description:类似马表盘
 *@Author: jsync
 *@CreateDate: 2021/4/14 15:20
 */
class StopwatchView : View {
    private lateinit var mContext: Context
    //半径用 width.toFloat()/2
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    //圆环宽度
    var strokeWidth = dp2px(8f)
    var rect = Rect()
    constructor(context: Context) : this(context, null) {
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context)
    }

    private fun init(context: Context) {
        mContext = context
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制环，4段
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        //1
        paint.color = Color.parseColor("#EFD419")
        canvas.drawArc(
                0f+strokeWidth/2,
                0f+strokeWidth/2,
                width.toFloat()-strokeWidth/2,
                width.toFloat()-strokeWidth/2,
                135f,
                67.5f,
                false,paint)
        //2
        paint.color = Color.parseColor("#F5B026")
        canvas.drawArc(
                0f+strokeWidth/2,
                0f+strokeWidth/2,
                width.toFloat()-strokeWidth/2,
                width.toFloat()-strokeWidth/2,
                202.5f,
                67.5f,
                false,paint)
        //3
        paint.color = Color.parseColor("#FF7912")
        canvas.drawArc(
                0f+strokeWidth/2,
                0f+strokeWidth/2,
                width.toFloat()-strokeWidth/2,
                width.toFloat()-strokeWidth/2,
                270f,
                67.5f,
                false,paint)
        //4
        paint.color = Color.parseColor("#D42541")
        canvas.drawArc(
                0f+strokeWidth/2,
                0f+strokeWidth/2,
                width.toFloat()-strokeWidth/2,
                width.toFloat()-strokeWidth/2,
                337.5f,
                67.5f,
                false,paint)
        //画文字,超卖，下跌，平衡，上升，超买
        val texts = arrayOf("超卖","下跌","平衡","上升","超买")
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.textSize = dp2px(8f)
        textPaint.color = Color.parseColor("#666666")
        //测量文字宽高
        //测量文字宽高
        textPaint.getTextBounds(texts[0], 0, texts[0].length, rect)
        val offset: Float = (rect.top + rect.bottom) .toFloat()
        val radius = width / 2.toFloat();
        //确定第一个文字的位子
        val x1 = radius - radius/2* sqrt(2f)
        canvas.drawText(texts[0],
                x1+dp2px(4f),
                height.toFloat() - dp2px(2f) ,
                textPaint)
        //2
        val x2 = radius - radius* cos(Math.PI/16)
        val y2 = radius - radius* sin(Math.PI/16)
        canvas.drawText(texts[1],
                (x2+dp2px(11f)).toFloat(),
                y2.toFloat(),
                textPaint)
        //3
        textPaint.textAlign = Paint.Align.CENTER
        val y3 = strokeWidth - offset
        canvas.drawText(texts[2],
                radius,
                y3 + dp2px(2f),
                textPaint)
        //4
        textPaint.textAlign = Paint.Align.RIGHT
        val x4 = radius + radius* cos(Math.PI/16)
        val y4 = radius - radius* sin(Math.PI/16)
        canvas.drawText(texts[3],
                (x4-dp2px(11f)).toFloat(),
                y4.toFloat(),
                textPaint)
        //5
        val x5 = radius + radius/2* sqrt(2f)
        canvas.drawText(texts[4],
                x5-dp2px(4f),
                height.toFloat() - dp2px(2f) ,
                textPaint)
        //圆心
        canvas.save()//保存
        canvas.rotate(-30f,radius, radius)
        val b =ContextCompat.getDrawable(mContext,R.mipmap.icon_pointer) as BitmapDrawable
        //y的计算 bitmap底部是一个圆，保证圆心一致
        canvas.drawBitmap(b.bitmap,radius - b.bitmap.width/2,radius - b.bitmap.height + b.bitmap.width/2,paint)
        canvas.restore()//恢复
    }


    fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
    }

}