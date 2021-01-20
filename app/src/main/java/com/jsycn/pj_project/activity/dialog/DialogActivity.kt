package com.jsycn.pj_project.activity.dialog

import android.app.Activity
import android.content.Context
import android.graphics.Color
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
import androidx.core.content.ContextCompat
import com.jaeger.library.StatusBarUtil
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


fun setFullScreen(activity: Activity) {
    val window = activity.window
    val decorView = window.decorView
    // Set the IMMERSIVE flag.
    // Set the content to appear under the system bars so that the content
    // doesn't resize when the system bars hide and show.
    val option = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//            or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    decorView.systemUiVisibility = option
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val lp = window.attributes
        lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        window.attributes = lp
    }
}
class DialogActivity : AppCompatActivity() {
    private lateinit var rootBinding: ActivityDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootBinding = ActivityDialogBinding.inflate(LayoutInflater.from(this))
        setContentView(rootBinding.root)
        //设置底部导航栏颜色，像小米系统底部颜色会有适配问题
        window?.navigationBarColor = ContextCompat.getColor(this,R.color.dialogActBg)
        StatusBarUtil.setTransparent(this)
        StatusBarUtil.setLightMode(this)
        initClick()
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