package com.jsycn.pj_project.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue

/**
 * Author: jsync
 * Date: 2021/2/20 16:31
 * Description:
 * History:
 */

/**
 * 将数字4舍5入到任意小数位
 * @param obj
 * @param num
 * @return
 */
fun parse2String(obj: Any?, num: Int, isJiaHao: Boolean): String? {
    val d: Double = parseDouble(obj)
    return if (isJiaHao && d > 0) "+" + String.format("%." + num + "f", d) else String.format("%." + num + "f", d)
}

/**
 * 转成double类型
 *
 * @param obj
 * @return double
 */
fun parseDouble(obj: Any?): Double {
    val str: String = obj?.toString() ?: ""
    return if ("" == str || "null" == str) {
        0.0
    } else {
        try {
            str.toDouble()
        } catch (e: Exception) {
            0.0
        }
    }
}

fun dp2px(dp: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().displayMetrics)
}

fun sp2px(sp: Float): Float {
    val fontScale = Resources.getSystem().displayMetrics.scaledDensity
    return (sp * fontScale + 0.5f)
}


fun getBitmap(resId: Int, width: Int, res: Resources?): Bitmap? {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeResource(res, resId, options)
    options.inJustDecodeBounds = false
    options.inDensity = options.outWidth
    options.inTargetDensity = width
    return BitmapFactory.decodeResource(res, resId, options)
}

fun getZForCamera(): Float {
    return -6 * Resources.getSystem().displayMetrics.density
}
class MyUtils {


}