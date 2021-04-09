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
import com.jsycn.pj_project.R

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
    }
    @Synchronized
    public fun setProgress(progress:Int){
        getAnimator()?.setIntValues(pbM.progress,progress)
//        pbM.progress = progress
        getAnimator()?.start()
    }

    private fun refreshText(){
        if (pbM.progress == 0 || pbM.progress == pbM.max){
            tvCurrent.visibility = View.GONE
        }else{
            tvCurrent.visibility = View.VISIBLE
        }
    }


    private var animator: ValueAnimator? = null

    private fun getAnimator(): ValueAnimator? {
        if (animator == null) {
            animator = ValueAnimator.ofInt()
            animator!!.addUpdateListener { animation ->
                pbM.progress = (animation.animatedValue as Int)
                //移动thumb和文本
                val w :Float= width * (animation.animatedValue as Float)/pbM.max
                val lpV =vThumb.layoutParams as ConstraintLayout.LayoutParams
                lpV.leftMargin = w.toInt()
                vThumb.layoutParams = lpV
                //文本
                val lpT = tvCurrent.layoutParams as ConstraintLayout.LayoutParams
                lpT.leftMargin = w.toInt()
                tvCurrent.layoutParams = lpT
                refreshText()
            }
        }
        return animator
    }

    fun dp2px(dp: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
    }
}