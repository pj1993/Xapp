package com.jsycn.pj_project.ui

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent


/**
 * @Description:
 * @Author: jsync
 * @CreateDate: 2020/8/26 18:16
 * @Version:
 */
class ProcessLifecycleObserver : LifecycleObserver {

    var onBackGround : ((background : Boolean) -> Unit)? =null

    fun listenerBackground(block : (background : Boolean) -> Unit){
        onBackGround = block
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(){
        Log.e("app_life","app创建")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onBackground(){
        onBackGround?.invoke(true)
        Log.e("app_life","app进入后台")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onForeground(){
        onBackGround?.invoke(false)
        Log.e("app_life","app进入前台")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){
        Log.e("app_life","app退出了")
    }

}