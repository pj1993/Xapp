package com.jsycn.pj_project.widget.floating.dokitview

import android.animation.ValueAnimator
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.blankj.utilcode.util.LogUtils
import com.jsycn.pj_project.R
import com.jsycn.pj_project.widget.floating.config.FloatIconConfig
import com.jsycn.pj_project.widget.floating.kit.core.AbsDokitView
import com.jsycn.pj_project.widget.floating.kit.core.DokitViewLayoutParams

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2020/11/9 16:08
 */
class AdsorptionDokitView : AbsDokitView() {
    override fun onCreate(context: Context?) {}

    override fun onCreateView(context: Context?, rootView: FrameLayout?): View {
        return LayoutInflater.from(context).inflate(R.layout.dk_main_launch_icon, rootView, false)
    }

    override fun onViewCreated(rootView: FrameLayout?) {
        //设置id便于查找
        this.rootView.id = R.id.float_icon_id
        //设置icon 点击事件
        this.rootView.setOnClickListener {
        }
    }

    override fun initDokitViewLayoutParams(params: DokitViewLayoutParams?) {
        params?.let {
            params.x = FloatIconConfig.getLastPosX()
            params.y = FloatIconConfig.getLastPosY()
            params.width = 127
            params.height = 127
        }
    }

    override fun onUp(x: Int, y: Int) {
        super.onUp(x, y)
        //手指离开，吸边
        //获取屏幕宽度
        val screenWidth = getScreenWidth()
        LogUtils.d("onUp的位子", "x=${x}-----y=${y}------screenWidth=${screenWidth}")
        if (x < screenWidth / 2) {
            //左边
            startAnimation(x, 0, y)
        } else {
            //右边
            startAnimation(x, screenWidth, y)
        }

    }

    private fun getScreenWidth(): Int {
        val manager: WindowManager = context?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
                ?: return 0
        val outMetrics = DisplayMetrics()
        manager.defaultDisplay.getMetrics(outMetrics)
        return outMetrics.widthPixels
    }

    private fun startAnimation(startX: Int, endX: Int, y: Int) {
        val animator = ValueAnimator.ofInt(startX, endX)
        animator.duration = 500
        animator.addUpdateListener {
            onMove(0, 0, (it.animatedValue as Int) - startX, 0)
            if (it.animatedValue as Int == endX) {
                saveLocationToSp()
            }
        }
        animator.start()
    }
}