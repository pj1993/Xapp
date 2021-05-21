package com.jsycn.pj_project.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.jsycn.pj_project.fragment.LazyFragmentOld
import java.lang.reflect.ParameterizedType

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/5/17 16:27
 */
abstract class BaseVbFragment<VB : ViewBinding> : LazyFragmentOld() {
    lateinit var mBinding: VB

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val actualTypeArguments = type.actualTypeArguments
            val clazz = (if (actualTypeArguments.size > 1) actualTypeArguments[1] else actualTypeArguments[0]) as Class<VB>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            mBinding = method.invoke(null, layoutInflater) as VB
        }
        return mBinding.root
    }

    /**
     * 页面中所有的view是否加载完成
     */
    protected var hasAllComponentInit = false
    /**
     * component是否加载过
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
                    hasAllComponentInit = true
                    hasInit = true
                    hideRefresh()
                }
            }
        }
    }

    open fun showRefresh() {
        loadComponent()
        if (hasAllComponentInit) {
            hasAllComponentInit = false
        }
        loadNet()
    }

    open fun hideRefresh() {}

    /**
     * 将自定义的component都添加在mComponents中
     */
    abstract fun loadComponent()
    protected fun BaseVbViewComponent<*, *>.attachToFragment() {
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
            mComponent.initObserve()
        }
    }

}