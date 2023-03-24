package com.jsycn.pj_project.ui.widget.view

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup

import java.util.ArrayList
import kotlin.math.max

/**
 * 流布局
 * create by pj on 2019/7/25
 * 1.在viewGroup里面，先onMeasure，测量出所有子控件大小位置，使用measureChildWithMargin。然后通过计算所有子view
 * 占的空间总和，得出viewGroup的实际大小，并保存，使用setMeasureDimension，
 * 2.viewGroup的onLayout进行布局，依次调用子view的layout方法进行布局。
 *
 * 扩展onLayout和layout
 * layout是正真布局自己的方法。
 * view的onLayout是空方法，viewGroup的onLayout是抽象方法，告诉开发者要重写，在里面布局子view（view.layout）
 * layout方法中调用了onLayout
 *
 * onMeasure和measure
 * measure真正测量view大小的地方，
 * view的onMeasure里面就一个setMeasureDimension，就是保存测量值的。重写onMeasure必须要调用setMeasureDimension
 * measure方法中调用了onMeasure，同样，viewGroup中可以重写onMeasure，在其中measure子view。
 *https://www.cnblogs.com/StephenWu/p/6263099.html
 * 刺.刺.刺激...
 */
class TagLayout(context: Context, attrs: AttributeSet) : ViewGroup(context, attrs) {
    private val childBounds = ArrayList<Rect>()


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //在这里测量所有子view的，宽高，
        val specMode = MeasureSpec.getMode(widthMeasureSpec)
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)//总宽
        var lineUsedWidth = 0//行用掉的宽
        var widthUsed = 0//总共用掉的宽
        var lineMaxHeight = 0//本行最高的
        var heightUsed = 0//总用掉的高
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            if (specMode != MeasureSpec.UNSPECIFIED && lineUsedWidth + childView.measuredWidth > specWidthSize) {
                //要换行（unspecified类似滑动view，他们获取的都是0）
                heightUsed += lineMaxHeight
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
                lineMaxHeight = 0
                lineUsedWidth = 0
            }
            val childBound = if (i < childBounds.size) childBounds[i] else Rect()
            val lp = childView.layoutParams as MarginLayoutParams //获取margin
            //保存子view大小
            childBound.set(lineUsedWidth + lp.leftMargin,
                    heightUsed + lp.topMargin,
                    lineUsedWidth + lp.leftMargin + childView.measuredWidth + lp.rightMargin,
                    heightUsed + lp.topMargin + childView.measuredHeight + lp.bottomMargin)
            childBounds.add(childBound)

            lineUsedWidth += (childView.measuredWidth + lp.leftMargin + lp.rightMargin)
            lineMaxHeight = max(lineMaxHeight, childView.measuredHeight + lp.topMargin + lp.bottomMargin)
            widthUsed = max(widthUsed, lineUsedWidth)
        }

        setMeasuredDimension(widthUsed, heightUsed + lineMaxHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val rect = childBounds[i]
            child.layout(rect.left, rect.top, rect.right, rect.bottom)
        }
    }


    override fun generateLayoutParams(p: AttributeSet): LayoutParams {
        return MarginLayoutParams(context, p)
    }
}
