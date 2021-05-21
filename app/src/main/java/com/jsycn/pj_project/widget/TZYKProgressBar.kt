package com.jsycn.pj_project.widget

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import com.jsycn.pj_project.R
import com.jsycn.pj_project.widget.view.CircleView

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/4/9 14:06
 */
class TZYKProgressBar  : FrameLayout {
    private lateinit var mContext: Context
    private lateinit var pbM :MyProgressBar
    private lateinit var tvMin :TextView
    private lateinit var tvMax :TextView
    private lateinit var tvCurrent :TextView
    private lateinit var vThumb : View
    private lateinit var vThumbColor: CircleView

    constructor(context: Context):this(context,null){

    }

    constructor(context: Context, attrs: AttributeSet?) : this(context,attrs,0) {

    }
    constructor(context: Context, attrs: AttributeSet?,defStyleAttr:Int):super(context, attrs,defStyleAttr){
        init(context)
    }


    private fun init(context: Context){
        mContext = context
        LayoutInflater.from(mContext).inflate(R.layout.progress_tzyk,this)

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        pbM = findViewById(R.id.pbM)
        tvMin = findViewById(R.id.tvMin)
        tvMax = findViewById(R.id.tvMax)
        tvCurrent = findViewById(R.id.tvCurrent)
        vThumb = findViewById(R.id.vThumb)
        vThumbColor = findViewById(R.id.vThumbColor)
    }
    @Synchronized
    public fun setProgressWithAnimator(progress:Int){
        getAnimator()?.setIntValues(pbM.progress,progress)
        getAnimator()?.start()
    }

    private fun refreshText(){
        if (pbM.progress == 0 || pbM.progress == pbM.max){
            tvCurrent.visibility = View.GONE
        }else{
            tvCurrent.visibility = View.VISIBLE
        }
        //文字
        tvCurrent.text = "${pbM.progress}"
        //文字颜色
        val p = (pbM.progress.toFloat() / pbM.max).run {
            if (this<0)
                return@run 0f
            if (this>1)
                return@run 1f
            return@run this
        }
        val color = ColorUtils.blendARGB(ContextCompat.getColor(mContext, R.color.color_market_style_progress_start),
                ContextCompat.getColor(mContext, R.color.color_market_style_progress_end), p)
        tvCurrent.setTextColor(color)
        //thumb颜色
        vThumbColor.setColor(color)
    }

    public fun setProgress(progress: Int) {
        pbM.progress = progress
        //移动thumb
        //总长度是width-thumb的宽度
        val w: Float = (width - dp2px(22f)) * (progress.toFloat()) / pbM.max
        val lpV = vThumb.layoutParams as ConstraintLayout.LayoutParams
        lpV.leftMargin = w.toInt()
        vThumb.layoutParams = lpV
        refreshText()
        invalidate()
    }

    private var animator: ValueAnimator? = null

    private fun getAnimator(): ValueAnimator? {
        if (animator == null) {
            animator = ValueAnimator.ofInt()
            animator!!.addUpdateListener { animation ->
                val p = animation.animatedValue as Int
                setProgress(p)
            }
        }
        return animator
    }

    private fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
    }
}