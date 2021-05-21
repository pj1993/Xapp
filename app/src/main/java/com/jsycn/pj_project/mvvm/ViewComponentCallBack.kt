package com.jsycn.pj_project.mvvm

import android.view.View


/**
 *Created by WinWang on 2021/3/29
 *Description-> 定义通用的View向外部传递回调，比如网络响应的回调
 */
interface ViewComponentCallBack {

    /**
     * 网络层的回调
     */
    fun onViewNetLoadCallBack(viewStatus: ViewStatusEnum,view: View)

    /**
     * 用户自定义的回调
     */
    fun onNormalCallBack() {

    }

}