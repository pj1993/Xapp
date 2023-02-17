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
import com.jsycn.pj_project.databinding.FragmentHomeBinding

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/11 18:41
 */
class HomeFragment : LazyFragmentOld() {
    private lateinit var rootBinding : FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootBinding = FragmentHomeBinding.inflate(inflater)
        return rootBinding.root
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
                val params = rootBinding.vStatus.layoutParams as ConstraintLayout.LayoutParams
                params.height = height
                rootBinding.vStatus.layoutParams = params
                rootBinding.vStatus.visibility = View.VISIBLE
            }
        }

        //股票
        rootBinding.btGp.setOnClickListener {
            startActivity(Intent(context,StockActivity::class.java))
        }
        rootBinding.btCountDownCancel.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
        }
        //mvvm
        rootBinding.btMvvm.setOnClickListener {
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