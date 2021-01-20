package com.jsycn.pj_project.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.jsycn.pj_project.MainActivity
import com.jsycn.pj_project.R
import com.jsycn.pj_project.utils.getStatusBarHeight
import com.jsycn.pj_project.utils.setAndroidNativeLightStatusBar
import kotlinx.android.synthetic.main.fragment_home.*

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/11 18:41
 */
class HomeFragment : LazyFragmentOld() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
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