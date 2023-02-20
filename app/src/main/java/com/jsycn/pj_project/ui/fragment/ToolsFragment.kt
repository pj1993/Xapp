package com.jsycn.pj_project.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.jsycn.pj_project.core.utils.getStatusBarHeight
import com.jsycn.pj_project.core.utils.setAndroidNativeLightStatusBar
import com.jsycn.pj_project.databinding.FragmentToolsBinding
import com.jsycn.pj_project.ui.activity.ChatGptAct

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/11 18:46
 */
class ToolsFragment: LazyFragmentOld() {
    private var _rootBind : FragmentToolsBinding ? =null
    private val rootBind : FragmentToolsBinding get() = _rootBind!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _rootBind = FragmentToolsBinding.inflate(inflater)
        return rootBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            getStatusBarHeight(it) { height ->
                val params = rootBind.vStatus.layoutParams as ConstraintLayout.LayoutParams
                params.height = height
                rootBind.vStatus.layoutParams = params
                rootBind.vStatus.visibility = View.VISIBLE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _rootBind = null
    }
}