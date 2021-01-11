package com.jsycn.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2020/12/21 16:19
 */
abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayout())
        initView()
        initData(savedInstanceState)
    }

    abstract fun initLayout():Int

    abstract fun initView()

    abstract fun initData(savedInstanceState: Bundle?)
}