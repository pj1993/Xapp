package com.jsycn.pj_project.activity.dialog

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatDialogFragment

/**
 *@Description:通用的（一般用于简单的填充view）
 *@Author: jsync
 *@CreateDate: 2021/1/18 14:29
 */
class CommonlyDialog : BaseDialogFragment<CommonlyDialog.Builder>() {
    override fun getRootView(resId: Int, inflater: LayoutInflater, container: ViewGroup?): View? {
        mBuilder?.let {
            if (it.mRootViewId != -1) {
                return inflater.inflate(it.mRootViewId, container, false)
            }
        }
        return null
    }

    override fun setData() {
        mBuilder?.let {
            if (it.setViewCallback != null && rootView != null) {
                it.setViewCallback!!.onViewCreate(rootView!!,this)
            }
        }
    }

    public fun setBuilder(builder: Builder): CommonlyDialog {
        this.mBuilder = builder
        return this
    }


    public class Builder : BaseDialogFragment.Builder() {
        var setViewCallback: OnSetViewCallBack? = null

        public fun setRootViewId(@LayoutRes resId: Int, setViewCallBack: OnSetViewCallBack? = null): Builder {
            this.mRootViewId = resId
            this.setViewCallback = setViewCallBack
            return this
        }

        public fun setDialog(dialog: Dialog): Builder {
            this.mDialog = dialog
            return this
        }

        public fun build(): CommonlyDialog {
            return CommonlyDialog().setBuilder(this)
        }
    }

    public interface OnSetViewCallBack {
        fun onViewCreate(view: View,dialog: AppCompatDialogFragment?)
    }
}