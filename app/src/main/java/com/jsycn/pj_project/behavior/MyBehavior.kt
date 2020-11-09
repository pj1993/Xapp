package com.jsycn.pj_project.behavior

import android.R.attr
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.RecyclerView


/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2020/10/14 14:12
 */
class MyBehavior(context: Context?, attrs: AttributeSet?) : CoordinatorLayout.Behavior<View>(context, attrs) {

    // 列表顶部和title底部重合时，列表的滑动距离。
    private var deltaY = 0f

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is RecyclerView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        if (deltaY == 0f){
            deltaY = dependency.y - child.height
        }

        var dy: Float = dependency.y - child.height
        dy = if (dy < 0) 0f else dy
        val y = -(dy / deltaY) * child.height
        val alpha = 1 - dy / deltaY
        child.alpha = alpha
        child.translationY = y

        return true
    }

}