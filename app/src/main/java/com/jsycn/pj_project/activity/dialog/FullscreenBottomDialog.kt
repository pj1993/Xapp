package com.jsycn.pj_project.activity.dialog

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import com.jsycn.pj_project.R

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/18 13:58
 */
class FullscreenBottomDialog: AppCompatDialog {
    constructor(context: Context):this(context,0){

    }

    constructor(context: Context, theme: Int =0):super(context,theme){
        //去掉title
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }
    constructor(context: Context,cancelable: Boolean,cancelListener:DialogInterface.OnCancelListener):super(context,cancelable,cancelListener){
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //设置背景透明
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        //解决title变黑，和小米11底部无法遮挡的问题
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        super.onCreate(savedInstanceState)
        window?.let {
            it.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
            it.setGravity(Gravity.BOTTOM)
            it.setWindowAnimations(R.style.BottomDialog_Animation_Style)
            it.decorView.setPadding(0,0,0,0)
        }
    }

}