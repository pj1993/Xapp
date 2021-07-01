package com.jsycn.pj_project.core.mvvm.test

import android.os.Bundle
import android.view.View
import com.jsycn.pj_project.databinding.FragmentTestMvvmBinding
import com.jsycn.pj_project.core.mvvm.BaseVbFragment

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/5/18 9:58
 */
class TestMVVMFragment : BaseVbFragment<FragmentTestMvvmBinding>() {

    override fun lazyInit() {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.srlRefresh.setOnRefreshListener {
            showRefresh()
        }
//        mBinding.srlRefresh.autoRefresh()
    }

    //加载出布局的所有component
    override fun loadComponent() {
        mBinding.cpTest.attachToFragment()
    }

    //里面已经有component的网络请求了，还差其他view的网络请求。
    override fun loadNet() {
        super.loadNet()

    }

    override fun hideRefresh() {
        if (mBinding.srlRefresh.isRefreshing){
            mBinding.srlRefresh.finishRefresh()
        }
    }

}