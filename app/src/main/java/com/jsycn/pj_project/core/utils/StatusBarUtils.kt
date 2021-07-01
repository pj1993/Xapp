package com.jsycn.pj_project.core.utils

import android.R
import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/19 18:13
 */
var mStatusBarHeight:Int=0
/**
 * 获取状态栏高度
 */
fun getStatusBarHeight(activity: Activity, block:(Int)->Unit ={}) {
    var statusBarHeight: Int = mStatusBarHeight
    if (statusBarHeight <= 0) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getStatusBarHeightCompatP(activity, block)
            return
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            statusBarHeight = getStatusBarHeightNormal(activity)
        }
    }
    block(statusBarHeight)
}

fun getStatusBarHeightCompatP(activity: Activity, block:(Int)->Unit ={}) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        try {
            val lp = activity.window.attributes
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            activity.window.attributes = lp
            val content = activity.findViewById<View>(R.id.content)
            content.setOnApplyWindowInsetsListener { v, insets ->
                val displayCutout = insets.displayCutout
                if (displayCutout != null) {
                    val displayCutoutHeight = displayCutout.safeInsetTop
                    mStatusBarHeight = displayCutoutHeight
                    block(displayCutoutHeight)
                } else {
                    val statusBarHeight: Int = getStatusBarHeightNormal(activity)
                    block(statusBarHeight)
                }
                insets
            }
        } catch (e: Exception) {
            val statusBarHeight: Int = getStatusBarHeightNormal(activity)
            block(statusBarHeight)
        }
    }
}

fun getStatusBarHeightNormal(activity: Activity): Int {
    var statusBarHeight = 0
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = activity.resources.getDimensionPixelSize(resourceId)
            mStatusBarHeight = statusBarHeight
        }
    }
    return statusBarHeight
}

/**
 * 设置状态栏模式
 */
fun setAndroidNativeLightStatusBar(activity: Activity, dark: Boolean) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val decor = activity.window.decorView
        if (dark) {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        } else {
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        }
    }
}



class StatusBarUtils {
}