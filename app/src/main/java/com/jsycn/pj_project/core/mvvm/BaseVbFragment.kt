package com.jsycn.pj_project.core.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jsycn.pj_project.core.delegate.inflateBindingWithGeneric
import com.jsycn.pj_project.ui.fragment.LazyFragmentOld

/**
 *@Description:遇到选择困难症，1通过反射获取viewBinding，代码量简洁明了。
 * 2使用属性代理，安全无反射，但是还得写getLayoutId，感觉重复冗余。
 *@Author: jsync
 *@CreateDate: 2021/5/17 16:27
 */
abstract class BaseVbFragment<VB : ViewBinding> : LazyFragmentOld() {
    private var _binding:VB?=null
    val mBinding: VB get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = inflateBindingWithGeneric(layoutInflater)
        return mBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null//防止内存泄漏，onDestroyView和onDestroy的问题
    }

    /**
     * 页面中所有的view是否加载完成（网络加载）
     */
    protected var hasAllComponentRefreshComplete = false
    /**
     * component是否初始化完成，页面只会初始化一次component
     * 需要请求网络或者正在请求网络的component集合,
     */
    protected var hasInit = false
    protected var mComponents = mutableListOf<BaseVbViewComponent<*, *>>()

    private var viewComponentCallBack: ViewComponentCallBack = object : ViewComponentCallBack {
        override fun onViewNetLoadCallBack(viewStatus: ViewStatusEnum, view: View) {
            //SUCCESS,ERROR,NETWORKERROR,EMPTY
            if (viewStatus == ViewStatusEnum.SUCCESS) {
                //处理component集合
                if (mComponents.size > 0) {
                    for (mComponent in mComponents) {
                        if (mComponent.id == view.id) {
                            mComponents.remove(mComponent)//剔除掉已经刷新好的component
                            break
                        }
                    }
                }
                //真正判断是否停止刷新
                if (mComponents.size == 0) {
                    hasAllComponentRefreshComplete = true
                    hasInit = true
                    hideRefresh()
                }
            }
        }
    }

    open fun showRefresh() {
        loadComponent()
        if (hasAllComponentRefreshComplete) {
            hasAllComponentRefreshComplete = false
        }
        loadNet()
    }

    open fun hideRefresh() {}

    /**
     * 将自定义的component都添加在mComponents中
     */
    abstract fun loadComponent()
    protected fun BaseVbViewComponent<*, *>.attachToFragment() {
        //TODO 小心这里会重复添加component,建议使用set
        mComponents.add(this)
        if (!hasInit) {
            setViewComponentCallBack(viewComponentCallBack)
            init()
        }
    }

    /**
     * 自定义的viewComponent的网络加载
     */
    open fun loadNet() {
        for (mComponent in mComponents) {
            mComponent.initObserve()//重新请求网络
        }
    }

}