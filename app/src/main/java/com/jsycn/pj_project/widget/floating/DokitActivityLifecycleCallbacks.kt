package com.jsycn.pj_project.widget.floating

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.jsycn.pj_project.R
import com.jsycn.pj_project.widget.floating.constant.DokitConstant
import com.jsycn.pj_project.widget.floating.kit.core.DokitViewManager
import com.jsycn.pj_project.widget.floating.model.ActivityLifecycleInfo
import com.jsycn.pj_project.widget.floating.util.LifecycleListenerUtil
import com.jsycn.pj_project.widget.floating.util.PermissionUtil
import com.jsycn.pj_project.widget.floating.util.UIUtils


/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2019-12-31-10:04
 * 描    述：全局的activity生命周期回调
 * 修订历史：
 * ================================================
 */
internal class DokitActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    private var startedActivityCounts = 0
    private var sHasRequestPermission = false

    /**
     * fragment 生命周期回调
     */
    private val sFragmentLifecycleCallbacks: FragmentManager.FragmentLifecycleCallbacks = DokitFragmentLifecycleCallbacks()

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        activity?.let {
            recordActivityLifeCycleStatus(it, LIFE_CYCLE_STATUS_CREATE)
            if (ignoreCurrentActivityDokitView(it)) {
                return
            }
            if (it is FragmentActivity) {
                //注册fragment生命周期回调
                it.supportFragmentManager.registerFragmentLifecycleCallbacks(sFragmentLifecycleCallbacks, true)
            }
        }

    }

    override fun onActivityStarted(activity: Activity?) {
        activity?.let {
            if (ignoreCurrentActivityDokitView(it)) {
                return
            }
            if (startedActivityCounts == 0) {
                DokitViewManager.instance.notifyForeground()
            }
            startedActivityCounts++
        }

    }

    override fun onActivityResumed(activity: Activity?) {
        activity?.let {
            //记录activity状态
            recordActivityLifeCycleStatus(it, LIFE_CYCLE_STATUS_RESUME)
            //是否是 不需要浮层的activity
            if (ignoreCurrentActivityDokitView(it)) {
                return
            }
            //设置app的直接子view的Id
//            UIUtils.getDokitAppContentView(it)
            //添加DokitView
            resumeAndAttachDokitViews(it)
            //监听回调
            for (listener in LifecycleListenerUtil.LIFECYCLE_LISTENERS) {
                listener.onActivityResumed(it)
            }
        }

    }

    override fun onActivityPaused(activity: Activity?) {
        activity?.let {
            if (ignoreCurrentActivityDokitView(it)) {
                return
            }
            for (listener in LifecycleListenerUtil.LIFECYCLE_LISTENERS) {
                listener.onActivityPaused(it)
            }
            DokitViewManager.instance.onActivityPause(it)
        }

    }

    override fun onActivityStopped(activity: Activity?) {
        activity?.let {
            recordActivityLifeCycleStatus(it, LIFE_CYCLE_STATUS_STOPPED)
            if (ignoreCurrentActivityDokitView(it)) {
                return
            }
            startedActivityCounts--
            //通知app退出到后台
            if (startedActivityCounts == 0) {
                DokitViewManager.instance.notifyBackground()
            }
        }

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        activity?.let {
            if (ignoreCurrentActivityDokitView(it)) {
                return
            }
        }

    }

    override fun onActivityDestroyed(activity: Activity?) {
        activity?.let {
            recordActivityLifeCycleStatus(it, LIFE_CYCLE_STATUS_DESTROY)
            if (ignoreCurrentActivityDokitView(it)) {
                return
            }
            //注销fragment的生命周期回调
            if (it is FragmentActivity) {
                it.supportFragmentManager.unregisterFragmentLifecycleCallbacks(sFragmentLifecycleCallbacks)
            }
            DokitViewManager.instance.onActivityDestroy(activity)
        }

    }

    /**
     * 显示所有应该显示的dokitView
     *
     * @param activity
     */
    private fun resumeAndAttachDokitViews(activity: Activity?) {
        activity?.let {
            if (DokitConstant.IS_NORMAL_FLOAT_MODE) {
                //显示内置dokitView icon
                DokitViewManager.instance.resumeAndAttachDokitViews(it)
            } else {
                //悬浮窗权限 vivo 华为可以不需要动态权限 小米需要
                if (PermissionUtil.canDrawOverlays(it)) {
                    DokitViewManager.instance.resumeAndAttachDokitViews(it)
                } else {
                    //请求悬浮窗权限
                    requestPermission(it)
                }
            }
        }


    }

    /**
     * 请求悬浮窗权限
     *
     * @param context
     */
    private fun requestPermission(context: Context) {
        if (!PermissionUtil.canDrawOverlays(context) && !sHasRequestPermission) {
            Toast.makeText(context, context.getText(R.string.dk_float_permission_toast), Toast.LENGTH_SHORT).show()
            //请求悬浮窗权限
            PermissionUtil.requestDrawOverlays(context)
            sHasRequestPermission = true
        }
    }


    /**
     * 记录当前Activity的生命周期状态
     */
    private fun recordActivityLifeCycleStatus(activity: Activity, lifeCycleStatus: Int) {
        var activityLifecaycleInfo = DokitConstant.ACTIVITY_LIFECYCLE_INFOS[activity.javaClass.canonicalName]
        if (activityLifecaycleInfo == null) {
            activityLifecaycleInfo = ActivityLifecycleInfo()
            activityLifecaycleInfo.activityName = activity.javaClass.canonicalName
            if (lifeCycleStatus == LIFE_CYCLE_STATUS_CREATE) {
                activityLifecaycleInfo.activityLifeCycleCount = 0
            } else if (lifeCycleStatus == LIFE_CYCLE_STATUS_RESUME) {
                activityLifecaycleInfo.activityLifeCycleCount = activityLifecaycleInfo.activityLifeCycleCount + 1
            } else if (lifeCycleStatus == LIFE_CYCLE_STATUS_STOPPED) {
                activityLifecaycleInfo.isInvokeStopMethod = true
            }
            DokitConstant.ACTIVITY_LIFECYCLE_INFOS[activity.javaClass.canonicalName!!] = activityLifecaycleInfo
        } else {
            activityLifecaycleInfo.activityName = activity.javaClass.canonicalName
            if (lifeCycleStatus == LIFE_CYCLE_STATUS_CREATE) {
                activityLifecaycleInfo.activityLifeCycleCount = 0
            } else if (lifeCycleStatus == LIFE_CYCLE_STATUS_RESUME) {
                activityLifecaycleInfo.activityLifeCycleCount = activityLifecaycleInfo.activityLifeCycleCount + 1
            } else if (lifeCycleStatus == LIFE_CYCLE_STATUS_STOPPED) {
                activityLifecaycleInfo.isInvokeStopMethod = true
            } else if (lifeCycleStatus == LIFE_CYCLE_STATUS_DESTROY) {
                DokitConstant.ACTIVITY_LIFECYCLE_INFOS.remove(activity.javaClass.canonicalName)
            }
        }
    }

    companion object {
        /**
         * 是否忽略在当前的activity上显示浮标
         *ignoreActivityClassNames
         * @param activity
         * @return
         */
        private fun ignoreCurrentActivityDokitView(activity: Activity): Boolean {
            val ignoreActivityClassNames = arrayOf("DisplayLeakActivity")
            for (activityClassName in ignoreActivityClassNames) {
                if (activity.javaClass.simpleName == activityClassName) {
                    return true
                }
            }
            return false
        }

        /**
         * Activity 创建
         */
        private const val LIFE_CYCLE_STATUS_CREATE = 100

        /**
         * Activity resume
         */
        private const val LIFE_CYCLE_STATUS_RESUME = 101

        /**
         * Activity stop
         */
        private const val LIFE_CYCLE_STATUS_STOPPED = 102

        /**
         * Activity destroy
         */
        private const val LIFE_CYCLE_STATUS_DESTROY = 103
    }

}