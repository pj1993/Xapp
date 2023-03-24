package com.jsycn.pj_project.ui.widget.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup

/**
 *create by pj on 2019/7/26
 *刺.刺.刺激...
 */
class MViewGroup(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {
    val rect: Rect=Rect()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val childAt = getChildAt(0)
        measureChildWithMargins(childAt,widthMeasureSpec,0,heightMeasureSpec,0)
        val lp = childAt.layoutParams as MarginLayoutParams
        rect.set(lp.leftMargin,lp.topMargin
                ,childAt.measuredWidth+lp.leftMargin+lp.rightMargin
                ,lp.topMargin+childAt.measuredHeight+lp.bottomMargin)
        setMeasuredDimension(childAt.measuredWidth+lp.rightMargin+lp.bottomMargin,
                childAt.measuredHeight+lp.topMargin+lp.bottomMargin);
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        getChildAt(0).layout(rect.left,rect.top,rect.right,rect.bottom);
    }


    override fun generateLayoutParams(p: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, p)
    }
}