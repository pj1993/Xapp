package com.jsycn.pj_project

import org.junit.Test

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2020/11/10 14:07
 */
class KotlinTest {

    var list : MutableList<String> = ArrayList()

    @Test
    fun t(){
        list.add("111")
        list.add("222")
        print(list.size)
        list.clear()
        println(list.size)
    }

}