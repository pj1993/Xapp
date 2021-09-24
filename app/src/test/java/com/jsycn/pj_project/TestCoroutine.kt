package com.jsycn.pj_project

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.util.*
import java.util.regex.Pattern
import kotlin.system.measureTimeMillis

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/3/19 14:20
 */
class TestCoroutine {

    @Test
    fun testCoroutine() = runBlocking {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }.await()
            val two = async { doSomethingUsefulTwo() }.await()
//            println("The answer is ${one.await() + two.await()}")
//            println("The answer is ${one}")
        }
        println("Completed in $time ms")
    }

    private suspend fun doSomethingUsefulOne():Int{
        println("The answer is ${System.currentTimeMillis()}")
        delay(1000)
        return 1
    }

    private suspend fun doSomethingUsefulTwo():Int{
        println("The answer is ${System.currentTimeMillis()}")
        delay(2000)
        return 2
    }

    @Test
    fun testSplit(){
        val ss = "#asdkjha#"
        val split =ss.split("!")
        print(split.toString()+split.size)
    }

    @Test
    fun testZhengze() {
        val contentText = "askjdh123[883]sakj12"
//        val ssb = SpannableStringBuilder(contentText)
        val pattern = "\\[.*\\]"
        val p = Pattern.compile(pattern)
        val m = p.matcher(contentText)

        var start = 0
        var end = 0
        var mCurrentLength = 0
        while (m.find()) {

            start = m.start() - mCurrentLength
            end = m.end() - mCurrentLength

            print(m.group(0))
        }
    }

    @Test
    fun testZhengze2() {
        val contentText = "askjdh123###123#"
        val pattern = "\\#.*\\#"
        val p = Pattern.compile(pattern)
        val m = p.matcher(contentText)
        if (m.find()){
            println(contentText.subSequence(m.start()+1,m.end()-1))
        }


        val r =contentText.matches(Regex(pattern))
        val s =contentText.split(Regex(pattern))
        println(r)
        println(s)
    }

    //时间戳
    @Test
    fun testTime(){
        println(getDailyStartTime(System.currentTimeMillis(),"GMT+8:00"))
        println(getDailyEndTime(System.currentTimeMillis(),"GMT+8:00"))
        println(1630512000000 - 86400000)
        println(1630598399999 - 86400000)
    }

    /**
     * 获取指定某一天的开始时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @param timeZone  如 GMT+8:00
     * @return
     */
    fun getDailyStartTime(timeStamp: Long, timeZone: String): Long {
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone(timeZone)
        calendar.timeInMillis = timeStamp
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        return calendar.timeInMillis
    }

    /**
     * 获取指定某一天的结束时间戳
     *
     * @param timeStamp 毫秒级时间戳
     * @param timeZone  如 GMT+8:00
     * @return
     */
    fun getDailyEndTime(timeStamp: Long, timeZone: String): Long {
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone(timeZone)
        calendar.timeInMillis = timeStamp!!
        calendar[Calendar.HOUR_OF_DAY] = 23
        calendar[Calendar.MINUTE] = 59
        calendar[Calendar.SECOND] = 59
        calendar[Calendar.MILLISECOND] = 999
        return calendar.timeInMillis
    }

    @Test
    fun stringLength(){
//        val ss= "asdasdasdasdasdas"
//        println(ss.length)
//        println(StringBuilder(ss).append("\t").append("\n").length)
        val sb = StringBuilder()
        println(Int.MAX_VALUE)
        for (i in 0 until 214748364) {
            sb.append("0")
            println(i)
        }
        println(sb.toString())
    }

}