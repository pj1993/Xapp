package com.jsycn.pj_project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.jsycn.pj_project.R
import com.jsycn.pj_project.utils.getStatusBarHeight
import com.jsycn.pj_project.utils.setAndroidNativeLightStatusBar
import kotlinx.android.synthetic.main.fragment_tools.*

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/11 18:46
 */
class ToolsFragment: LazyFragmentOld() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tools, container, false)
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
            setAndroidNativeLightStatusBar(it, false)
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
}