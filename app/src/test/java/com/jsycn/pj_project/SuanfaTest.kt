package com.jsycn.pj_project

import org.junit.Test
import java.lang.StringBuilder
import java.sql.DriverManager.println
import java.util.*

class SuanfaTest {


    //1.用两个栈实现队列的功能
    fun test1() {
        //用于入栈，类似于队列添加元素
        val stack1 = Stack<Int>()
        //用于出栈，类似于队列删除元素
        val stack2 = Stack<Int>()

        //加入队列
        fun appendTail(element: Int): Int? {
            stack1.push(element)
            return null
        }

        //出队列
        fun deleteHead(): Int {
            //如果stack2中有元素，直接弹出
            if (stack2.size > 0)
                return stack2.pop()
            //1和2都没有了，返回-1
            if (stack1.size == 0)
                return -1
            //第三种情况，1的元素还有  2中是空的，得将1中的搬到2中
            while (stack1.size > 0) {
                stack2.push(stack1.pop())
            }
            return stack2.pop()
        }
    }


    fun getFblq(n: Int): Int {
        if (n == 1) return 1
        if (n == 0) return 0
        return getFblq(n - 1) + getFblq(n - 2)
    }

    //2斐波拉切数列
    @Test
    fun test2() {
        val x = getFblq(6)
        println("huoqu====${x}")
    }

    @Test
    fun test3() {

        println(
            "huoqu====${
                bestTeamScore(
                    intArrayOf(1, 3, 5, 10, 15),
                    kotlin.intArrayOf(1, 2, 3, 4, 5)
                )
            }"
        )
    }

    fun bestTeamScore(scores: IntArray, ages: IntArray): Int {
        val size = scores.size
        val people = arrayOfNulls<IntArray>(size)

        for (i in 0..(size - 1)) {
            people[i] = intArrayOf(scores[i], ages[i])
        }
        //排序
        Arrays.sort(people, Comparator { o1, o2 ->
            if (o1!![0] != o2!![0]) {
                return@Comparator o1[0] - o2[0]//看分数
            } else {
                return@Comparator o1[1] - o2[1]//看年龄
            }
            return@Comparator 0
        })

        //找到i处的最佳配对，每个i的 配对方式都会不一样
        val dp = IntArray(size)
        var res = 0
        for (i in 0..(size - 1)) {
            //从0到i-1处找到年龄小于people[i]的，这样直接将分数加上去就可以算出i的最佳配对
            if (i > 0) {
                for (j in 0..(i - 1)) {
                    //我的分数比你大，我只要保证我年龄比你大，那么我们就是最佳配对
                    if (people[i]!![1] >= people[j]!![1]) {
                        dp[i] = Math.max(dp[i], dp[j])
                    }
                }
            }

            //配上自己的值
            dp[i] += people[i]!![0]
            //计算当前配对和上一个配对谁更好
            res = Math.max(dp[i], res)
        }
        return res
    }


    //用链表计算两数之和，
    /*给你两个 非空 的链表，表示两个非负的整数。它们每位数字都是按照 逆序 的方式存储的，并且每个节点只能存储 一位 数字。
    请你将两个数相加，并以相同形式返回一个表示和的链表。
    你可以假设除了数字 0 之外，这两个数都不会以 0 开头。*/
    //比如整数342，用链表表示：2-》4-》3，。
    //      465          ：5-》6-》4
    //     合807，        :7->0->8

    class ListNode(var `val`: Int) {
        var next: ListNode? = null
    }

    fun addTwoNumbers(l1: ListNode?, l2: ListNode?): ListNode? {
        //循环l1，合l2，只要其中一个有，就继续循环，
        var ll1 = l1
        var ll2 = l2
        var res: ListNode? = null
        var cn: ListNode? = null
        var step = 0//进位数
        while (ll1 != null || ll2 != null) {
            //取值计算
            val sum = (ll1?.`val` ?: 0) + (ll2?.`val` ?: 0) + step
            //计算进位数
            step = sum / 10
            //计算当前值
            val cNode = ListNode(sum % 10)
            if (res == null) {
                res = cNode
                cn = cNode
            } else {
                cn?.next = cNode
                cn = cNode
            }
            ll1 = ll1?.next
            ll2 = ll2?.next
        }
        if (step > 0) {
            cn?.next = ListNode(step)
        }
        return res
    }


    //无重复字符的最长子串
    fun lengthOfLongestSubstring(s: String): Int {
        var max = 0
        var left = 0//用于记录左边界 下标
        val chars = s.toCharArray()
        val map = hashMapOf<Char, Int>()
        //滑动窗口
        for (i in chars.indices) {
            //如果已经包含了这个字符，找到这个字符的位子，并将左边界更新到这个位子
            if (map.containsKey(chars[i])) {
                left = left.coerceAtLeast(((map[chars[i]] ?: 0) + 1))
            }
            map[chars[i]] = i
            max = max.coerceAtLeast(i - left + 1)

        }
        return max
    }


    //最长回文字
    //中心扩散
    fun longestPalindrome(s: String): String {
        if (s.length < 2) return s

        var maxStartIndex = 0
        var maxEndIndex = 0
        var maxLength = 0
        val chars = s.toCharArray()

        for (i in s.indices) {
            for (j in 0..s.length / 2) {
                var l = i - j - 1
                var r = i + j
                if (l < 0 || r > (s.length - 1)) {
                    break
                }
                if (chars[l] == chars[r]) {
                    //是回文字
                    val length = r - l + 1
                    if (maxLength < length) {
                        maxLength = length
                        maxStartIndex = l
                        maxEndIndex = r
                    }
                } else {
                    break
                }
            }
            for (j in 1..s.length / 2) {
                var l = i - j
                var r = i + j
                if (l < 0 || r > (s.length - 1)) {
                    break
                }
                if (chars[l] == chars[r]) {
                    //是回文字
                    val length = r - l + 1
                    if (maxLength < length) {
                        maxLength = length
                        maxStartIndex = l
                        maxEndIndex = r
                    }
                } else {
                    break
                }
            }

        }
        return s.substring(maxStartIndex, maxEndIndex + 1)
    }

    @Test
    fun getHuiWz() {
        val s1 = longestPalindrome("babad")
        val s2 = longestPalindrome("cbbd")
        var i = 3
    }


    //N字变换
    //输入
    //"PAYPALISHIRING"
    //输出
    //P   A   H   N
    //A P L S I I G
    //Y   I   R
    fun convert(s: String, numRows: Int): String {
        //就是 z字变换，
        if (numRows == 1) {
            return s
        }
        //有几行就有几个sb拼接，然后翻转
        val sbs = arrayOfNulls<StringBuilder>(numRows)
        for (i in 0 until numRows) {
            sbs[i] = StringBuilder()
        }
        var flag = 1//sb的下标变换 01210
        var index = 0;
        for (i in s.indices) {
            sbs[index]!!.append(s[i])
            index += flag
            if (index == (numRows - 1)) {
                flag = -1
            }
            if (index == 0) flag = 1
        }
        val result = StringBuilder()
        for (sb in sbs) {
            result.append(sb)
        }
        return result.toString()
    }

    @Test
    fun getZ() {
        val s2 = convert("AB", 1)
        val s1 = convert("PAYPALISHIRING", 3)
        var i = 3
    }

    //整数翻转，主要是注意边界，边界的判断一般是边界/10 和当前值比较
    //-2^31 , 2^31-1 Int.MAX_VALUE
    fun reverse(x: Int): Int {
        var inN = x
        var res = 0
        while (inN != 0) {
            val remainder = inN % 10
            val nMax = Int.MAX_VALUE / 10//最后一位是7
            val nMin = Int.MIN_VALUE / 10//最后一位是8
            if (res > nMax || res == nMax && remainder>7){
                res = 0
                break
            }
            if (res<nMin || res == nMin && remainder<-8){
                res = 0
                break
            }

            res = res * 10 + remainder

            inN /= 10
        }
        return res
    }


}