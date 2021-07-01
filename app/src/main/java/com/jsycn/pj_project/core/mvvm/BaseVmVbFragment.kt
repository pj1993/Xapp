package com.jsycn.pj_project.core.mvvm

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/5/17 17:04
 */
abstract class BaseVmVbFragment <VM:BaseViewModel,VB:ViewBinding> : BaseVbFragment<VB>() {

    protected val viewModel:VM by lazy {
        val types = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
        if (isShareViewModel()){
            ViewModelProvider(requireActivity().viewModelStore
                    ,requireActivity().defaultViewModelProviderFactory
            ).get<VM>(types[0] as Class<VM>)
        }else{
            ViewModelProvider(this).get<VM>(types[0] as Class<VM>)
        }
    }

    /**
     * 是否复用顶层Activity的viewmodel来通信
     */
    open fun isShareViewModel(): Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(viewModel)
        initView()
        // 因为Fragment恢复后savedInstanceState不为null，
        // 重新恢复后会自动从ViewModel中的LiveData恢复数据，
        // 不需要重新初始化数据。
        initStateObserve()
        initObserve()
        if (savedInstanceState == null) {
            initData()
        }
    }

    /**
     * 如果有需要初始化View数据
     */
    open fun initView() {
        // Override if need
    }

    private fun initStateObserve() {
        viewModel.viewStatus.observe(viewLifecycleOwner, Observer {
            when (it) {
                ViewStatusEnum.SUCCESS -> {
//                    showSuccess()
                }

                ViewStatusEnum.ERROR -> {
//                    showError()
                }

                ViewStatusEnum.EMPTY -> {
//                    showEmpty()
                }

                ViewStatusEnum.NETWORKERROR -> {
//                    showTimeOut()
                }

            }
        })
    }

    /**
     * 初始化自己的观察者
     */
    abstract fun initObserve()

    /**
     * 普通加载数据
     */
    abstract fun initData()

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.removeObserver(viewModel)
    }

//    /**
//     * 停止刷新数据
//     */
//    fun stopRefreshAndLoading(refreshLayout: SmartRefreshLayout?) {
//        refreshLayout?.run {
//            when (refreshLayout.state) {
//                RefreshState.Refreshing -> {
//                    this.finishRefresh()
//                }
//
//                RefreshState.Loading -> {
//                    this.finishLoadMore()
//                }
//                else -> {
//                    this.finishRefresh()
//                    this.finishLoadMore()
//                }
//            }
//        }
//    }

}