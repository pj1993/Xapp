package com.jsycn.pj_project.core.delegate

import androidx.fragment.app.Fragment
import com.jsycn.pj_project.databinding.FragmentDelegateBinding

/**
 *@Description:使用代理属性，简化fragment和activity之间的传参
 *@Author: jsync
 *@CreateDate: 2021/6/9 14:37
 */
class TestUseDelegateAugFragment : Fragment() {

    private var orderId:Int by fragmentArgument()
    private var orderType:Int? by fragmentArgumentNullable(2)

    //使用代理viewBinding(fragment和activity中都一样)
    private val mBinding by viewBinding(FragmentDelegateBinding::bind)

    companion object{
        fun newInstance(orderId:Int,orderType:Int?) = TestUseDelegateAugFragment().apply {
            this.orderId = orderId
            this.orderType = orderType
        }
    }

}