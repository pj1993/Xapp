package com.jsycn.pj_project.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.LogUtils
import com.jsycn.pj_project.ui.activity.view.DialogActivity
import com.jsycn.pj_project.ui.activity.view.ProgressActivity
import com.jsycn.pj_project.databinding.FragmentViewBinding
import com.jsycn.pj_project.core.utils.getStatusBarHeight
import com.jsycn.pj_project.core.utils.setAndroidNativeLightStatusBar
import kotlinx.android.synthetic.main.fragment_view.*

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/11 18:45
 */
class ViewFragment: LazyFragmentOld() {
    private lateinit var rootBind :FragmentViewBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootBind = FragmentViewBinding.inflate(inflater)
        return rootBind.root
    }

    override fun lazyInit() {
    }

    override fun onVisible() {
        initTitle()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootBind.btPopup.setOnClickListener {
            startActivity(Intent(activity, DialogActivity::class.java))
        }
        activity?.let {
            getStatusBarHeight(it) { height ->
                val params = v_status.layoutParams as ConstraintLayout.LayoutParams
                params.height = height
                v_status.layoutParams = params
                v_status.visibility = View.VISIBLE
            }
            setAndroidNativeLightStatusBar(it, true)
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
}