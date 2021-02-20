package com.jsycn.pj_project

import org.junit.Test

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2020/11/10 14:07
 */
fun String.kz() {}
class KotlinTest {

    var list: MutableList<String> = ArrayList()

    @Test
    fun t() {
        list.add("111")
        list.add("222")
        print(list.size)
        list.clear()
        println(list.size)
    }

    //1
    //扩展函数(写在顶部，写在类里面的扩展函数无法被指向)
//    fun String.kz(){}
    @Test
    fun tk() {
        //2中调用方式写法
        "11".kz()//1
        //「指向和函数等价的对象的引用」称作是「指向这个函数的引用」
        (String::kz)("11")//2 这种情况,和下面要讲的dsl有关联，第一个参数得传一个接收者

        //讲扩展函数对象化
        val funObjectA: String.() -> Unit = String::kz  //带接收者的函数对象
        val funObjectB: (String) -> Unit = String::kz   //普通函数对象
        val c: String.() ->Unit = funObjectB //说明可以相互转换
        funObjectA("11")//这种调用方式和下面的把扩展函数当做参数类似
    }


    //2
    //参数写法,结合lambda写法，内部it指向参数
    private fun kotlinDSL2(block: (StringBuilder) -> Unit) {
        block(StringBuilder("Kotlin "))
    }

    //将参数变成接收者，block是StringBuilder的扩展函数，扩展函数会限定调用，lambda内部指向自己
    private fun kotlinDSL(block: StringBuilder.() -> Unit) {
//        StringBuilder("Kotlin").block()
        block(StringBuilder("Kotlin "))
    }

    //传进去一个函数类型的参数  block是一个函数对象，但是使用时得有一个接收者StringBuilder
    @Test
    fun t2() {
        kotlinDSL {
            append("DSL")
            println(this)
        }
        kotlinDSL2 {
            it.append("DSL")
            print(it)
        }
    }


    private infix fun String.zz(cs:String){}
    //3 中缀表示法  关键字 infix
    @Test
    fun t3(){
        "111".zz("222")
        "111" zz "222"
    }

    //结合起来
    class 前{val name : String="前"}
    infix fun Int.天(obj:前) : String{
        return "${this}天${obj.name}"
    }


    @Test
    fun t4(){
        val 前 = 前()
        val p = 1 天 前
        print(p)
    }


    @Test
    fun mathTest(){
        val a = 1.0
        val b = 2.0
        print(a.coerceAtLeast(2.0))
    }

}