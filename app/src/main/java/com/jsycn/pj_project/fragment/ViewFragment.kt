package com.jsycn.pj_project.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.jsycn.pj_project.R
import com.jsycn.pj_project.activity.dialog.DialogActivity
import com.jsycn.pj_project.databinding.FragmentViewBinding
import com.jsycn.pj_project.utils.getStatusBarHeight
import com.jsycn.pj_project.utils.setAndroidNativeLightStatusBar
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
            startActivity(Intent(activity,DialogActivity::class.java))
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
    }

    private fun initTitle() {
        activity?.let {
            setAndroidNativeLightStatusBar(it, true)
        }
    }
}