package com.jsycn.pj_project.utils

import java.util.Objects.isNull

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
class MyUtils {


}