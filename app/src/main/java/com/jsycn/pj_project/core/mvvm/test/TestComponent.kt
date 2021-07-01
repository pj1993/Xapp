package com.jsycn.pj_project.core.mvvm.test

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.jsycn.pj_project.databinding.ComponentTestBinding
import com.jsycn.pj_project.core.mvvm.BaseVbViewComponent
import com.jsycn.pj_project.core.mvvm.BaseViewModel
import kotlinx.coroutines.delay

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/5/18 14:59
 */
class TestComponent @JvmOverloads constructor(context: Context,attrs: AttributeSet)
    : BaseVbViewComponent<TestViewModel, ComponentTestBinding>(context,attrs) {

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        super.initData()
    }

    override fun initObserve() {
        mViewModel.getText().observe(lifecycleOwner, Observer {
            Log.e("mvvm","收到了数据222")
            mBinding.tvTitle.text = it
        })
    }

}

class TestViewModel: BaseViewModel() {


    fun getText():LiveData<String>{
        return emit (
                error = {},
                block = {
                    //网络请求什么的，然后返回自动封装成liveData
                    //如果不需要自动封装成liveData可以用launch
                    delay(1000)
                    Log.e("mvvm","请求的数据222")
                    "222"
                })
    }



}