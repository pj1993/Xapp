package com.jsycn.pj_project.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.jsycn.pj_project.ui.activity.view.DialogActivity
import com.jsycn.pj_project.ui.activity.view.ProgressActivity
import com.jsycn.pj_project.databinding.FragmentViewBinding
import com.jsycn.pj_project.core.utils.getStatusBarHeight
import com.jsycn.pj_project.core.utils.setAndroidNativeLightStatusBar

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/11 18:45
 */
class ViewFragment: LazyFragmentOld() {
    private var _rootBind :FragmentViewBinding?=null
    private val rootBind :FragmentViewBinding get() = _rootBind!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _rootBind = FragmentViewBinding.inflate(inflater)
        return rootBind.root
    }

    override fun lazyInit() {
    }

    override fun onVisible() {
        initTitle()
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
        setAndroidNativeLightStatusBar(requireActivity(), true)
        rootBind.btPopup.setOnClickListener {
            startActivity(Intent(activity, DialogActivity::class.java))
        }
        rootBind.btProgress.setOnClickListener {
            startActivity(Intent(activity, ProgressActivity::class.java))
        }
    }

    private fun initTitle() {
        activity?.let {
            setAndroidNativeLightStatusBar(it, true)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("ViewFragment","onResume")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d("ViewFragment","setUserVisibleHint")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _rootBind = null
    }
}