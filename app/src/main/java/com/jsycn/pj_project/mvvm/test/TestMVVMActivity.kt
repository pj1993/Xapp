package com.jsycn.pj_project.mvvm.test

import android.os.Bundle
import androidx.fragment.app.FragmentFactory
import com.jsycn.base.BaseActivity
import com.jsycn.pj_project.R


/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/5/18 9:48
 */
class TestMVVMActivity : BaseActivity() {
    override fun initLayout(): Int {
        return R.layout.activity_test_mvvm
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