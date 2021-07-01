package com.jsycn.pj_project.ui.activity.dialog

import android.app.Dialog
import android.view.View
import android.widget.PopupWindow
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.util.*

/**
 *@Description:
 *@Author: jsync
 *@CreateDate: 2021/1/25 15:19
 */
object DialogManager {

    /**
     * 记录顺序
     */
    private val mQueue = LinkedList<FuncInfo>()
    private var mCurrentFunc: FuncInfo? = null

    /**
     * 添加dialog
     * @param dialog
     * @param cancelListener dialog不能自定义cancelListener，需要回调时，逻辑放在这个里面
     */
    public fun show(dialog: Dialog, importance: Int = 0, cancelListener: () -> Unit = {}) {
        doFunc(importance) {
            dialog.setOnCancelListener {
                cancelListener()
                doFunc(func = null)
            }
            dialog.show()
        }
    }

    /**
     * dialogFragment
     */
    public fun show(dialogFragment: BaseDialogFragment<*>, transaction: FragmentTransaction, tag: String, importance: Int = 0, cancelListener: () -> Unit = {}) {
        doFunc(importance) {
            dialogFragment.setOnCancelListener(object : BaseDialogFragment.OnCancelListener {
                override fun onCancel() {
                    cancelListener()
                    doFunc(func = null)
                }
            })
            dialogFragment.show(transaction, tag)
        }
    }

    public fun show(dialogFragment: BaseDialogFragment<*>, ma: FragmentManager, tag: String, importance: Int = 0, cancelListener: () -> Unit = {}) {
        doFunc(importance) {
            dialogFragment.setOnCancelListener(object : BaseDialogFragment.OnCancelListener {
                override fun onCancel() {
                    cancelListener()
                    doFunc(func = null)
                }
            })
            dialogFragment.show(ma, tag)
        }
    }

    public fun showAsDropDown(popupWindow: PopupWindow, v: View, importance: Int = 0,cancelListener: () -> Unit = {}) {
        doFunc(importance) {
            popupWindow.setOnDismissListener {
                cancelListener()
                doFunc(func = null)
            }
            popupWindow.showAsDropDown(v)
        }
    }

    public fun showAsDropDown(popupWindow: PopupWindow, v: View, xOff: Int, yOff: Int, importance: Int = 0,cancelListener: () -> Unit = {}) {
        doFunc(importance) {
            popupWindow.setOnDismissListener {
                cancelListener()
                doFunc(func = null)
            }
            popupWindow.showAsDropDown(v, xOff, yOff)
        }
    }

    public fun showAsDropDown(popupWindow: PopupWindow, v: View, xOff: Int, yOff: Int, gravity: Int, importance: Int = 0,cancelListener: () -> Unit = {}) {
        doFunc(importance) {
            popupWindow.setOnDismissListener {
                cancelListener()
                doFunc(func = null)
            }
            popupWindow.showAsDropDown(v, xOff, yOff, gravity)
        }
    }

    public fun showAtLocation(popupWindow: PopupWindow, parent: View, gravity: Int, x: Int, y: Int, importance: Int = 0,cancelListener: () -> Unit = {}) {
        doFunc(importance) {
            popupWindow.setOnDismissListener {
                cancelListener()
                doFunc(func = null)
            }
            popupWindow.showAtLocation(parent, gravity, x, y)
        }
    }

    /**
     * 执行函数
     */
    @Synchronized
    private fun doFunc(importance: Int = 0, func: (() -> Unit)?) {
        if (func != null) {
            //加入到队列,优先级最低往后排
            addToQueue(FuncInfo(func, importance))
        } else {
            mCurrentFunc = null
        }
        if (mCurrentFunc == null) {
            if (mQueue.size != 0) {
                val f = mQueue.poll()
                mCurrentFunc = f
                mCurrentFunc?.let {
                    it.func?.invoke()
                }
            }
        }
    }

    private fun addToQueue(funcInfo: FuncInfo) {
        if (funcInfo.importance > 0) {
            //寻找到合适个位置，//如果数量过多，可以考虑从尾部开始循环
            for (i in 0 until mQueue.size) {
                val leftF = mQueue[i]
                //当前优先级比funcInfo低，插在前面
                if (leftF.importance < funcInfo.importance) {
                    mQueue.add(i, funcInfo)
                    return
                }
            }
        }
        //没找到位置，添加到最后
        //默认优先级importance=0，直接添加到最后
        mQueue.offer(funcInfo)
    }


    private class FuncInfo {
        constructor(f: () -> Unit) {
            func = f
        }

        constructor(f: () -> Unit, importance: Int = 0) {
            func = f
            this.importance = importance
        }

        var func: (() -> Unit)? = null

        /**值越大，优先级越高*/
        var importance = 0
    }


}