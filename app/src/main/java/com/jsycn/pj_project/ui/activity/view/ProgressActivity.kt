package com.jsycn.pj_project.ui.activity.view

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.jaeger.library.StatusBarUtil
import com.jsycn.pj_project.R
import com.jsycn.pj_project.databinding.ActivityProgressBinding

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/4/9 10:26
 */
class ProgressActivity : AppCompatActivity(){
    private lateinit var rootBinding: ActivityProgressBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootBinding = ActivityProgressBinding.inflate(LayoutInflater.from(this))
        setContentView(rootBinding.root)
        //设置底部导航栏颜色，像小米系统底部颜色会有适配问题
        window?.navigationBarColor = ContextCompat.getColor(this, R.color.dialogActBg)
        StatusBarUtil.setTransparent(this)
        StatusBarUtil.setLightMode(this)
        initClick()
    }

    private fun initClick(){
        rootBinding.marketStyleProgress.setProgressWithAnimator(80)
    }
}