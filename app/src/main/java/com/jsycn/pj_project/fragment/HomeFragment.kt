package com.jsycn.pj_project.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.jsycn.pj_project.MainActivity
import com.jsycn.pj_project.R
import kotlinx.android.synthetic.main.fragment_home.*

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/11 18:41
 */
class HomeFragment: LazyFragmentNew() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bt_countDown_cancel.setOnClickListener {
            val b = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!,bt_countDown_cancel,"img_test").toBundle()
            startActivity(Intent(activity,MainActivity::class.java),b)
        }
        bt_zcdh.setOnClickListener {
            //转场动画
            startActivity(Intent(activity,MainActivity::class.java))
        }
    }

}