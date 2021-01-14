package com.jsycn.pj_project.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jsycn.pj_project.R

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/11 18:46
 */
class ToolsFragment: LazyFragmentNew() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tools, container, false)
    }
}