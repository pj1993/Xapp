package com.jsycn.pj_project.widget.floating

import android.app.Application
import android.content.Context
import com.jsycn.pj_project.widget.floating.constant.DokitConstant
import com.jsycn.pj_project.widget.floating.kit.core.AbsDokitView
import com.jsycn.pj_project.widget.floating.kit.core.DokitViewManager
import com.jsycn.pj_project.widget.floating.kit.core.NormalDokitViewManager
import com.jsycn.pj_project.widget.floating.util.PermissionUtil

/**
 * Created by jint on 2018/6/22.
 */
public object DoraemonKit {
    @JvmField
    var APPLICATION: Application? = null
    private const val TAG = "DoraemonKit"

    /**
     * @param app
     * @param mapKits  自定义kits  根据用户传进来的分组 建议优先选择mapKits 两者都传的话会选择mapKits
     * @param listKits  自定义kits 兼容原先老的api
     * @param productId Dokit平台端申请的productId
     */
    @JvmStatic
    public fun install(app: Application) {
        APPLICATION = app
        try {
            DoraemonKitReal.install(app)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @JvmStatic
    fun show(context: Context, targetClass: Class<out AbsDokitView?>) {
        if (DokitConstant.IS_NORMAL_FLOAT_MODE || PermissionUtil.canDrawOverlays(context)) {
            DoraemonKitReal.show(targetClass)
        } else {
            PermissionUtil.requestDrawOverlays(context)
        }
    }

    @JvmStatic
    fun hide(targetClass: Class<out AbsDokitView?>) {
        DoraemonKitReal.hide(targetClass)
    }

    fun isFloatingShow(targetClass: Class<out AbsDokitView?>): Boolean {
        return DoraemonKitReal.isFloatingShow(targetClass)
    }

    /**
     * 是否显示主入口icon
     */
    @JvmStatic
    fun setAwaysShowMainIcon(awaysShow: Boolean) {
        DokitConstant.AWAYS_SHOW_MAIN_ICON = awaysShow
    }
    @JvmStatic
    fun setSystemFloat(boolean: Boolean){
        if (boolean){
            //初始化didi哆啦A梦
            DokitConstant.IS_NORMAL_FLOAT_MODE = false
            DokitViewManager.instance.mDokitViewManager?.detachAll()
        }else{
            DokitConstant.IS_NORMAL_FLOAT_MODE = true
            DokitViewManager.instance.mDokitViewManager?.detachAll()
        }
    }
}