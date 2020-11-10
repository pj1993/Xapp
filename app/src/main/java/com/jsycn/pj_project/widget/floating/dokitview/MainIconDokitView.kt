package com.jsycn.pj_project.widget.floating.dokitview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.blankj.utilcode.util.ToastUtils
import com.jsycn.pj_project.R
import com.jsycn.pj_project.widget.floating.config.FloatIconConfig
import com.jsycn.pj_project.widget.floating.kit.core.AbsDokitView
import com.jsycn.pj_project.widget.floating.kit.core.DokitViewLayoutParams

/**
 * 悬浮按钮
 * Created by jintai on 2019/09/26.
 */
class MainIconDokitView : AbsDokitView() {
    override fun onCreate(context: Context?) {}


    override fun onCreateView(context: Context?, rootView: FrameLayout?): View {
        return LayoutInflater.from(context).inflate(R.layout.dk_main_launch_icon, rootView, false)
    }

    override fun onViewCreated(rootView: FrameLayout?) {
        //设置id便于查找
        this.rootView.id = R.id.float_icon_id
        //设置icon 点击事件
        this.rootView.setOnClickListener {
            ToastUtils.showLong("awsl")
        }
    }

    override fun initDokitViewLayoutParams(params: DokitViewLayoutParams?) {
        params?.let {
            params.x = FloatIconConfig.getLastPosX()
            params.y = FloatIconConfig.getLastPosY()
            params.width = FLOAT_SIZE
            params.height = FLOAT_SIZE
        }

    }

    override fun onResume() {
        super.onResume()
        if (isNormalMode) {
            val params = normalLayoutParams
            params.width = FLOAT_SIZE
            params.height = FLOAT_SIZE
            invalidate()
        }
    }

    companion object {
        var FLOAT_SIZE = 174
    }
}