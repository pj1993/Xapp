package com.jsycn.pj_project

import org.junit.Test
import kotlin.math.sqrt
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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
        //两者  取大
        print(a.coerceAtLeast(2.0))
    }

    @Test
    fun xunhuanTest(){
        //循环，从后往前
        val list= mutableListOf<String>("1","2","3")
        for(i in list.size-1 downTo 0){
            println(list[i])
        }
    }
    @Test
    fun shuxue(){
//        //1
//        print("${sqrt(2f)}")
//        //2
//        val d = doubleArrayOf(0.1,2.2,3.3,5.5,2.5)
//        d.sort()//array排序没返回值，list的排序有返回值
//        print(d)
        //3
        var ss = "我们是冠军最军最饿"
//        val s = java.lang.StringBuilder(ss).insert(4, "\n").toString()
        print(ss.substring(0,8))
    }


    //-------------------------Kotlin委托----------------------------------------------------
    //博客地址--https://juejin.cn/post/6958346113552220173
    //1类委托
    //基础接口
    interface Base{
        fun print()
    }
    //基础对象
    class BaseImpl(val x:Int):Base{
        override fun print() {
            print(x)
        }
    }
    //被委托类(Derived的方法都交给了b里面的方法处理，有点像接口定义的规则类)
    class Derived(b:Base):Base by b
    @Test
    fun testByjz(){
        //委托
        val b = BaseImpl(10)
        Derived(b).print()
        //原始写法
        val y:Base =BaseImpl(12)
        y.print()
    }

    //2属性委托(属性委托的对象里面方法用到关键字operator)
//    基础类不需要实现任何接口，但必须提供 getValue() 方法，
//    如果是委托可变属性，还需要提供 setValue()。在每个属性委托的实现的背后，
//    Kotlin 编译器都会生成辅助属性并委托给它。 例如，对于属性 prop，会生成「辅助属性」 prop$delegate。
//    而 prop 的 getter() 和 setter() 方法只是简单地委托给辅助属性的 getValue() 和 setValue() 处理。

    class Example{
        //被委托属性
        var prop:String by Delegate()//基础对象
    }
    //基础类
    class Delegate{
        private var realValue:String ="彭"
        operator fun getValue(thisRef:Any?,property:KProperty<*>):String{
            println("getValue")
            return realValue
        }
        operator fun setValue(thisRef:Any?,property:KProperty<*>,value:String){
            println("setValue")
            realValue = value
        }
    }
    @Test
    fun testByshuxing(){
        //委托写法
        val e = Example()
        println(e.prop)// 最终调用 Delegate#getValue()
        e.prop = "Peng"// 最终调用 Delegate#setValue()
        println(e.prop)
        //原始写法
        //感觉就是给这个属性加了个getter和setter方法，这个有什么用呢？
    }

    //3局部变量委托(没啥特别的)
    fun testjubu(args: Array<String>) {
        val lazyValue: String by lazy {
            println("Lazy Init Completed!")
            "Hello World."
        }
        if (true) {
            println(lazyValue) // 首次调用
            println(lazyValue) // 后续调用

        }
    }

    //kotlin中常用的委托lazy,ObservableProperty,使用 Map 存储属性值
    val lazyValue: String by lazy {
        println("Lazy Init Completed!")
        "Hello World."
    }

    fun testByLazy() {
        println(lazyValue) // 首次调用
        println(lazyValue) // 后续调用
    }
//    输出：
//    Lazy Init Completed!
//    Hello World.
//    Hello World.

//自带的快速实现代理
val name by ReadOnlyProperty<Any?, String> { thisRef, property -> "peng" }

    //-------------------------Kotlin委托----------------------------------------------------



}