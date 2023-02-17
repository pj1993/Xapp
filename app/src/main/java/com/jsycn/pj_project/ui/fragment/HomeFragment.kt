package com.jsycn.pj_project.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.blankj.utilcode.util.LogUtils
import com.jsycn.pj_project.ui.activity.MainActivity
import com.jsycn.pj_project.R
import com.jsycn.pj_project.ui.activity.StockActivity
import com.jsycn.pj_project.core.mvvm.test.TestMVVMActivity
import com.jsycn.pj_project.core.utils.getStatusBarHeight
import com.jsycn.pj_project.core.utils.setAndroidNativeLightStatusBar
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
        }

        //股票
        bt_gp.setOnClickListener {
            startActivity(Intent(context,StockActivity::class.java))
        }
        bt_countDown_cancel.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }
        //mvvm
        bt_mvvm.setOnClickListener {
            startActivity(Intent(context,TestMVVMActivity::class.java))
        }
        LogUtils.d("home_log","我初始化了")
    }

    private fun initTitle() {
        activity?.let {
            setAndroidNativeLightStatusBar(it, true)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("homeFragment","onResume")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Log.d("homeFragment","setUserVisibleHint")
    }

}