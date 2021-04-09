package com.jsycn.pj_project

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Test
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
}