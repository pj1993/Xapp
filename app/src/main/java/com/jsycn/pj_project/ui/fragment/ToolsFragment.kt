package com.jsycn.pj_project.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.LogUtils
import com.jsycn.pj_project.R
import com.jsycn.pj_project.core.utils.getStatusBarHeight
import com.jsycn.pj_project.core.utils.setAndroidNativeLightStatusBar
import com.jsycn.pj_project.databinding.FragmentToolsBinding
import com.jsycn.pj_project.ui.activity.ChatGptAct
import kotlinx.android.synthetic.main.fragment_tools.*

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/11 18:46
 */
class ToolsFragment: LazyFragmentOld() {
    private lateinit var rootBind : FragmentToolsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootBind = FragmentToolsBinding.inflate(inflater)
        return rootBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            getStatusBarHeight(it) { height ->
                val params = v_status.layoutParams as ConstraintLayout.LayoutParams
                params.height = height
                v_status.layoutParams = params
                v_status.visibility = View.VISIBLE
            }
        }

        rootBind.btChatGpt.setOnClickListener {
            //进入chatGpt页面
            startActivity(Intent(activity,ChatGptAct::class.java))
        }
    }

    override fun lazyInit() {

    }

    override fun onVisible() {
        initTitle()
    }

    private fun initTitle() {
        activity?.let {
            setAndroidNativeLightStatusBar(it, false)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("ToolsFragment","onResume")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d("ToolsFragment","setUserVisibleHint")
    }
}