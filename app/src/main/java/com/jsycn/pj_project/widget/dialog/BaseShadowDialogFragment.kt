package com.jsycn.pj_project.widget.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2020/10/23 17:47
 */
fun createDialog() : BaseShadowDialogFragment{
    return BaseShadowDialogFragment()
}
class BaseShadowDialogFragment : AppCompatDialogFragment() {
    //是否是底部弹出，(设置了底部弹出，动画就失效了)
    //dialog是否you 弹出动画（默认动画）
    //有设置view，则暴露出view的事件


    private lateinit var mContext : Context
    private lateinit var rootView : View
    private lateinit var contentView : View
    private var builder : Builder? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //这里判断是  底部展示还是 中间

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun setBuilder(b : Builder ){
        builder = b
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //这里设置动画
        return super.onCreateDialog(savedInstanceState)
    }

    companion object Builder{


        fun build() : BaseShadowDialogFragment{
            return BaseShadowDialogFragment().apply { setBuilder(this@Builder) }
        }
    }



}