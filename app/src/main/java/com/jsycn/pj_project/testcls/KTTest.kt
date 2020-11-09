package com.jsycn.pj_project.testcls

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2020/10/12 9:23
 */
class KTTest {

    //作用域的问题   输入inc(1)  打印 num ： 2
    fun inc(num : Int) {
        val num = 2
        if (num > 0) {
            val num = 3
        }
        println ("num: " + num)
    }
}