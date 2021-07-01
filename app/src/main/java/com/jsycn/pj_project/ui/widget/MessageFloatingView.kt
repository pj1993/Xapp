package com.jsycn.pj_project.ui.widget

import android.animation.ValueAnimator
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.jsycn.pj_project.R
import com.jsycn.pj_project.core.entity.MessageBean
import com.jsyncpj.floating.core.AbsFloatingView
import com.jsyncpj.floating.core.FloatingManager
import com.jsyncpj.floating.model.FloatingViewLayoutParams
import com.jsyncpj.floating.util.dp2px

/**
 *@Description:一次显示，normal
 * 5秒自动销毁，上滑消失,点击跳转
 *@Author: jsync
 *@CreateDate: 2020/11/25 18:25
 */
class MessageFloatingView : AbsFloatingView() {
    private var mHeight = 140f.dp2px

    override fun onCreate(context: Context?) {
    }

    override fun onCreateView(context: Context?, rootView: FrameLayout?): View {
        return LayoutInflater.from(context).inflate(R.layout.floating_message, rootView, false)
    }

    override fun onViewCreated(rootView: FrameLayout?) {
        bundle?.let { b->
            this.rootView.id = R.id.floating_icon_id
            this.rootView.setOnClickListener {
                val m : MessageBean = b.getSerializable("MessageFloatingView") as MessageBean?:return@setOnClickListener
                ToastUtils.showLong(m.name)
            }
        }
    }

    override fun initFloatingViewLayoutParams(params: FloatingViewLayoutParams?) {
        params?.let {
            it.width = WindowManager.LayoutParams.MATCH_PARENT
            it.height = mHeight
            //初始位置是顶部
            it.x = 0
            it.y = -mHeight
        }
    }

    //屏蔽掉父View的滑动
    override fun canDrag(): Boolean {
        return false
    }

    //不限制边界
    override fun restrictBorderline(): Boolean {
        return false
    }

    override fun onResume() {
        super.onResume()
        startAnimation(-mHeight, 0,800)
    }

    override fun onDown(x: Int, y: Int) {
        super.onDown(x, y)
        animator?.cancel()
    }

    override fun onMove(x: Int, y: Int, dx: Int, dy: Int) {
        super.onMove(x, y, dx, dy)
        //上滑动,消失
        moveSelf(dy)
    }

    override fun onUp(x: Int, y: Int) {
        //判断是否要退出
        if (normalLayoutParams.topMargin> -mHeight/2){
            //弹回
            startAnimation(normalLayoutParams.topMargin,0,500)
        }else{
            //删除
            startAnimation(normalLayoutParams.topMargin,-mHeight,500)
        }
    }

    private fun moveSelf(dy: Int) {
        normalLayoutParams.topMargin += dy
        if (normalLayoutParams.topMargin < -mHeight) {
            normalLayoutParams.topMargin = -mHeight
        }
        if (normalLayoutParams.topMargin > 0) {
            normalLayoutParams.topMargin = 0
        }
        //更新位置
        updateViewLayout(tag, false)
    }

    private var animator : ValueAnimator? =null
    /**
     * -mHeight ~ 0dp
     * 0dp ~ -mHeight
     */
    private fun startAnimation(startY: Int, endY: Int,duration: Long) {
        if (animator != null){
            animator?.cancel()
        }
        animator = ValueAnimator.ofInt()
        animator?.setIntValues(startY,endY)
        animator?.duration = duration
        animator?.addUpdateListener {
            moveSelf((it.animatedValue as Int) - startY)
            if (it.animatedValue as Int == endY) {
                if (endY == 0) {

                } else {
                    //销毁自己
                    LogUtils.d("myFloating","销毁调用")
                    FloatingManager.instance.detach(MessageFloatingView::class.java)
                }
            }
        }
        animator!!.start()
    }

    override fun onDestroy() {
        LogUtils.d("myFloating","销毁")
        animator?.removeAllUpdateListeners()
        animator?.cancel()
        animator=null
        super.onDestroy()
    }

}