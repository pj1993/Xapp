package com.jsycn.pj_project.activity.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/18 10:43
 */
abstract class BaseDialogFragment<T : BaseDialogFragment.Builder> : AppCompatDialogFragment() {
    protected lateinit var mContext: Context
    protected var rootView: View? = null
    protected var mBuilder: T? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    /**
     * 创建视图
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return if (mBuilder != null && mBuilder!!.mRootViewId != -1) {
            rootView = getRootView(mBuilder!!.mRootViewId, inflater, container)
            setData()
            return if (rootView == null) {
                super.onCreateView(inflater, container, savedInstanceState)
            } else {
                rootView
            }
        } else
            super.onCreateView(inflater, container, savedInstanceState)
    }

    /**
     * 获取rootView
     * 建议使用viewBind
     */
    public abstract fun getRootView(@LayoutRes resId: Int, inflater: LayoutInflater, container: ViewGroup?): View?

    /**
     * 设置数据，从mBuilder里面获取数据
     */
    public abstract fun setData()

    /**
     * 创建dialog
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return if (mBuilder != null && mBuilder!!.mDialog != null)
            mBuilder!!.mDialog!!
        else
            super.onCreateDialog(savedInstanceState)
    }


    public open class Builder {
        var mDialog: Dialog? = null

        @LayoutRes
        var mRootViewId: Int = -1
    }

}