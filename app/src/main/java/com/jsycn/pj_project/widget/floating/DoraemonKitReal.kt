package com.jsycn.pj_project.widget.floating

import android.app.Application
import com.blankj.utilcode.util.*
import com.jsycn.pj_project.widget.floating.constant.DokitConstant
import com.jsycn.pj_project.widget.floating.kit.core.AbsDokitView
import com.jsycn.pj_project.widget.floating.kit.core.DokitViewManager


/**
 * Created by jintai on 2019/12/18.
 * DoraemonKit 真正执行的类  不建议外部app调用
 */
object DoraemonKitReal {
    private const val TAG = "Doraemon"
    private var APPLICATION: Application? = null

    /**
     * @param app
     */
    fun install(app: Application) {
        //赋值
        APPLICATION = app
        //初始化工具类
        initAndroidUtil(app)
        //判断进程名
        if (!ProcessUtils.isMainProcess()) {
            return
        }
        //注册全局的activity生命周期回调
        app.registerActivityLifecycleCallbacks(DokitActivityLifecycleCallbacks())
        //初始化悬浮窗管理类
        DokitViewManager.instance.init(app)
    }

    private fun initAndroidUtil(app: Application) {
        Utils.init(app)
        LogUtils.getConfig() // 设置 log 总开关，包括输出到控制台和文件，默认开
                .setLogSwitch(true) // 设置是否输出到控制台开关，默认开
                .setConsoleSwitch(true) // 设置 log 全局标签，默认为空，当全局标签不为空时，我们输出的 log 全部为该 tag， 为空时，如果传入的 tag 为空那就显示类名，否则显示 tag
                .setGlobalTag("Doraemon") // 设置 log 头信息开关，默认为开
                .setLogHeadSwitch(true) // 打印 log 时是否存到文件的开关，默认关
                .setLog2FileSwitch(true) // 当自定义路径为空时，写入应用的/cache/log/目录中
                .setDir("") // 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setFilePrefix("djx-table-log") // 输出日志是否带边框开关，默认开
                .setBorderSwitch(true) // 一条日志仅输出一条，默认开，为美化 AS 3.1 的 Logcat
                .setSingleTagSwitch(true) // log 的控制台过滤器，和 logcat 过滤器同理，默认 Verbose
                .setConsoleFilter(LogUtils.V) // log 文件过滤器，和 logcat 过滤器同理，默认 Verbose
                .setFileFilter(LogUtils.E) // log 栈深度，默认为 1
                .setStackDeep(2).stackOffset = 0
    }

    private fun showMainIcon(targetClass: Class<out AbsDokitView?>) {
        DokitViewManager.instance.attachMainIcon(targetClass)
    }

    /**
     * 显示悬浮窗
     */
    fun show(targetClass: Class<out AbsDokitView?>) {
        DokitConstant.AWAYS_SHOW_MAIN_ICON = true
        if (DokitViewManager.instance.getDokitView(ActivityUtils.getTopActivity(), targetClass.simpleName) == null) {
            showMainIcon(targetClass)
        }
    }

    fun isFloatingShow(targetClass: Class<out AbsDokitView?>): Boolean {
        return DokitViewManager.instance.getDokitView(ActivityUtils.getTopActivity(), targetClass.simpleName) != null
    }

    fun hide(targetClass: Class<out AbsDokitView?>) {
        DokitConstant.AWAYS_SHOW_MAIN_ICON = false
        DokitViewManager.instance.detach(targetClass)
    }
}