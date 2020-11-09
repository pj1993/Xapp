package com.jsycn.pj_project.widget.floating.kit.core

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.view.WindowManager
import com.jsycn.pj_project.widget.floating.DoraemonKit
import com.jsycn.pj_project.widget.floating.constant.DokitConstant
import com.jsycn.pj_project.widget.floating.util.UIUtils

/**
 * Created by jintai on 2018/10/23.
 * 浮标管理类
 */
class DokitViewManager : DokitViewManagerInterface {
    companion object {
        val instance: DokitViewManager = DokitViewManager()
    }

    /**
     * 每个类型在页面中的位置 只保存marginLeft 和marginTop
     */
    private val mDokitViewPos: MutableMap<String, Point?> = mutableMapOf()

    private val mLastDokitViewPosInfoMaps: MutableMap<String, LastDokitViewPosInfo> = mutableMapOf()
    var mDokitViewManager: DokitViewManagerInterface ?= null
    private lateinit var mContext: Context


    /**
     * 数据库操作类 懒加载  todo("功能待实现")
     */
//    val db: DokitDatabase by lazy {
//        Room.databaseBuilder(DoraemonKit.APPLICATION!!,
//                DokitDatabase::class.java,
//                "dokit-database") //下面注释表示允许主线程进行数据库操作，但是不推荐这样做。
//                //他可能造成主线程lock以及anr
//                //所以我们的操作都是在新线程完成的
//                .allowMainThreadQueries()
//                .build()
//    }


    fun init(context: Context) {
        mContext = context

        mDokitViewManager = if (DokitConstant.IS_NORMAL_FLOAT_MODE) {
            NormalDokitViewManager(context)
        } else {
            SystemDokitViewManager(context)
        }
    }


    /**
     * 当app进入后台时调用
     */
    override fun notifyBackground() {
        mDokitViewManager?.notifyBackground()
    }

    /**
     * 当app进入前台时调用
     */
    override fun notifyForeground() {
        mDokitViewManager?.notifyForeground()
    }

    /**
     * 只有普通浮标才会调用
     * 保存每种类型dokitView的位置
     */
    fun saveDokitViewPos(tag: String, marginLeft: Int, marginTop: Int) {

        if (mDokitViewPos[tag] == null) {
            val point = Point(marginLeft, marginTop)
            mDokitViewPos[tag] = point
        } else {
            val point = mDokitViewPos[tag]
            point?.set(marginLeft, marginTop)
        }


    }

    /**
     * 只有普通的浮标才需要调用
     * 获得指定dokitView的位置信息
     *
     * @param tag
     * @return
     */
    fun getDokitViewPos(tag: String): Point? {
        return mDokitViewPos[tag]

    }

    /**
     * 只有普通的浮标才需要调用
     * 添加activity关联的所有dokitView activity resume的时候回调
     *
     * @param activity
     */
    override fun resumeAndAttachDokitViews(activity: Activity?) {
        mDokitViewManager?.resumeAndAttachDokitViews(activity)
    }

    override fun onMainActivityCreate(activity: Activity?) {}
    override fun onActivityCreate(activity: Activity?) {}
    override fun onActivityResume(activity: Activity?) {}
    override fun onActivityPause(activity: Activity?) {
        mDokitViewManager?.onActivityPause(activity)
    }

    /**
     * 在当前Activity中添加指定悬浮窗
     *
     * @param dokitIntent
     */
    override fun attach(dokitIntent: DokitIntent?) {
        if (DoraemonKit.APPLICATION == null)return
        mDokitViewManager?.attach(dokitIntent)
    }

    /**
     * 显示主图标 dokitView
     */
    fun attachMainIcon(targetClass: Class<out AbsDokitView?>) {
        val mainIconIntent = DokitIntent(targetClass)
        mainIconIntent.mode = DokitIntent.MODE_SINGLE_INSTANCE
        attach(mainIconIntent)
    }



    /**
     * 移除每个activity指定的dokitView
     */
    override fun detach(tag: String?) {

        mDokitViewManager?.detach(tag)
    }

    override fun detach(activity: Activity?, tag: String?) {

        mDokitViewManager?.detach(activity, tag)
    }

    /**
     * 移除每个activity指定的dokitView
     */
    override fun detach(dokitView: AbsDokitView?) {

        mDokitViewManager?.detach(dokitView)
    }

    override fun detach(activity: Activity?, dokitView: AbsDokitView?) {
        mDokitViewManager?.detach(activity, dokitView)
    }

    override fun detach(dokitViewClass: Class<out AbsDokitView?>?) {
        mDokitViewManager?.detach(dokitViewClass)
    }

    override fun detach(activity: Activity?, dokitViewClass: Class<out AbsDokitView?>?) {

        mDokitViewManager?.detach(activity, dokitViewClass)
    }

    /**
     * 移除所有activity的所有dokitView
     */
    override fun detachAll() {

        mDokitViewManager?.detachAll()
    }

    /**
     * Activity销毁时调用
     */
    override fun onActivityDestroy(activity: Activity?) {
        mDokitViewManager?.onActivityDestroy(activity)
    }

    /**
     * 获取页面上指定的dokitView
     *
     * @param activity 如果是系统浮标 activity可以为null
     * @param tag
     * @return
     */
    override fun getDokitView(activity: Activity?, tag: String?): AbsDokitView? {
        return mDokitViewManager?.getDokitView(activity, tag)
    }

    /**
     * @param activity
     * @return
     */
    override fun getDokitViews(activity: Activity?): MutableMap<String, AbsDokitView?>? {

        return mDokitViewManager?.getDokitViews(activity)
    }

    /**
     * 系统悬浮窗需要调用
     */
    interface DokitViewAttachedListener {
        fun onDokitViewAdd(dokitView: AbsDokitView?)
    }

    /**
     * 系统悬浮窗需要调用
     *
     * @param listener
     */
    fun addDokitViewAttachedListener(listener: DokitViewAttachedListener?) {

        if (!DokitConstant.IS_NORMAL_FLOAT_MODE && mDokitViewManager is SystemDokitViewManager) {
            listener?.let {
                (mDokitViewManager as SystemDokitViewManager).addDoKitViewAttachedListener(listener)
            }
        }
    }

    /**
     * 系统悬浮窗需要调用
     *
     * @param listener
     */
    fun removeDokitViewAttachedListener(listener: DokitViewAttachedListener?) {

        if (!DokitConstant.IS_NORMAL_FLOAT_MODE && mDokitViewManager is SystemDokitViewManager) {
            listener?.let {
                (mDokitViewManager as SystemDokitViewManager).removeDoKitViewAttachedListener(listener)
            }
        }
    }

    /**
     * 获取
     *
     * @return WindowManager
     */
    val windowManager: WindowManager
        get() = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    fun saveLastDokitViewPosInfo(key: String, lastDokitViewPosInfo: LastDokitViewPosInfo) {
        mLastDokitViewPosInfoMaps[key] = lastDokitViewPosInfo
    }

    fun getLastDokitViewPosInfo(key: String): LastDokitViewPosInfo? {
        return mLastDokitViewPosInfoMaps[key]
    }

    fun removeLastDokitViewPosInfo(key: String) {
        mLastDokitViewPosInfoMaps.remove(key)
    }


}