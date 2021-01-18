package com.jsycn.pj_project.activity.dialog

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import com.jsycn.pj_project.R
import com.jsycn.pj_project.databinding.ActivityDialogBinding
import java.lang.reflect.Method


/**
 * 是不是小米全面屏
 */
public fun isXiaoMiFullScreen(context:Context):Boolean{
    //是否是小米
    if (Build.MANUFACTURER == "Xiaomi") {
        //是否开启了全面屏
        return Settings.Global.getInt(context.contentResolver, "force_fsg_nav_bar", 0) != 0
    }
    return false
}
/**
 * 获取底部虚拟按键高度
 * @return
 */
public fun getNavigationBarHeight(context: Context):Int{
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = windowManager.defaultDisplay
    val dm = DisplayMetrics()
    try {
        val c = Class.forName("android.view.Display")
        val method: Method = c.getMethod("getRealMetrics", DisplayMetrics::class.java)
        method.invoke(display, dm)
        return dm.heightPixels - display.height
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return 0
}

class DialogActivity : AppCompatActivity() {
    private lateinit var rootBinding: ActivityDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootBinding = ActivityDialogBinding.inflate(LayoutInflater.from(this))
        //解决title变黑，和小米11底部无法遮挡的问题
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        setContentView(rootBinding.root)
        initClick()
    }

//    override fun onResume() {
//        super.onResume()
//        setRealHeight()
//    }

    private fun setRealHeight(){
        val lp =rootBinding.root.layoutParams
        //获取真实高度
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        var height = metrics.heightPixels
        if (isXiaoMiFullScreen(this)) {
            height += getNavigationBarHeight(this)
        }
        lp.height = height
        rootBinding.root.layoutParams = lp
    }

    private fun initClick(){
        rootBinding.btCommonly.setOnClickListener {
            CommonlyDialog.Builder()
                    .setDialog(FullscreenBottomDialog(this))
                    .setRootViewId(R.layout.dialog_fragment_commonly,object :CommonlyDialog.OnSetViewCallBack{
                        override fun onViewCreate(view: View, dialog: AppCompatDialogFragment?) {
                            view.findViewById<TextView>(R.id.btn_selectPositive).setOnClickListener {
                                Toast.makeText(this@DialogActivity,"点到我了！",Toast.LENGTH_SHORT).show()
                                dialog?.dismiss()
                            }
                        }
                    })
                    .build().show(supportFragmentManager,CommonlyDialog::class.simpleName)

        }

        rootBinding.btIos.setOnClickListener {
            CommonlyDialog.Builder()
                    .setDialog(FullScreenIosDialog(this))
                    .setRootViewId(R.layout.dialog_fragment_commonly,object :CommonlyDialog.OnSetViewCallBack{
                        override fun onViewCreate(view: View, dialog: AppCompatDialogFragment?) {
                            view.findViewById<TextView>(R.id.btn_selectPositive).setOnClickListener {
                                Toast.makeText(this@DialogActivity,"点到我了！",Toast.LENGTH_SHORT).show()
                                dialog?.dismiss()
                            }
                        }
                    })
                    .build().show(supportFragmentManager,CommonlyDialog::class.simpleName)
        }
    }



}