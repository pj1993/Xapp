package com.jsycn.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2020/12/21 16:19
 */
abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //沉浸式：方案，透明状态栏，顶到头,设置占位状态栏view
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
        super.onCreate(savedInstanceState)
        setContentView(initLayout())
        initView()
        initData(savedInstanceState)
    }

    abstract fun initLayout():Int

    abstract fun initView()

    abstract fun initData(savedInstanceState: Bundle?)
}