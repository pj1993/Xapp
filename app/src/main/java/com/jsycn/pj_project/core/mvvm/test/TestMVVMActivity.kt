package com.jsycn.pj_project.core.mvvm.test

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentFactory
import com.jsycn.base.BaseActivity
import com.jsycn.pj_project.R
import com.jsycn.pj_project.databinding.ActivityTestMvvmBinding


/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/5/18 9:48
 */
class TestMVVMActivity : BaseActivity() {
    private lateinit var rootBinding : ActivityTestMvvmBinding
    override fun initLayout(): View {
        rootBinding = ActivityTestMvvmBinding.inflate(layoutInflater)
        return rootBinding.root
    }

    override fun initView() {
        val clazz = TestMVVMFragment::class.java
        val f =FragmentFactory.loadFragmentClass(clazz.classLoader,clazz.name)
                .newInstance()
        supportFragmentManager.beginTransaction()
                .add(R.id.fl_content,f,clazz.simpleName)
                .commit()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }


}