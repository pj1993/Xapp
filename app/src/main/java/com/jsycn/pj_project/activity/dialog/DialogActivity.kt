package com.jsycn.pj_project.activity.dialog

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.DisplayMetrics
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import com.jaeger.library.StatusBarUtil
import com.jsycn.pj_project.R
import com.jsycn.pj_project.activity.dialog.NormalDialog.*
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
        //通用
        rootBinding.btCommonly.setOnClickListener {
            getDialogFragment().show(supportFragmentManager,CommonlyDialog::class.simpleName)
        }
        //ios
        rootBinding.btIos.setOnClickListener {
            getIosDialogFragment().show(supportFragmentManager,CommonlyDialog::class.simpleName)
        }
        //全屏popup
        rootBinding.btPopup.setOnClickListener {
            getPopup().showAtLocation(window.decorView, Gravity.CENTER, 0, 0)
        }
        //dialog
        rootBinding.btDialog1.setOnClickListener {
            NormalDialog.Builder()
                    .setDialogType(DIALOG_CONFIRM)
                    .setCanceledOnTouchOutside(false)
                    .setCancelable(false)
                    .setTip("你在想peach？")
                    .setTitle("警告!")
                    .setAutoClose(false)
                    .setPositiveListener { d, _ ->
                        d.dismiss()
                    }
                    .build(this).show()
        }
        rootBinding.btDialog2.setOnClickListener {
            NormalDialog.Builder()
                    .setDialogType(DIALOG_SELECT)
                    .setTitle("提示!")
                    .setTip("是否要删除？")
                    .setPositiveListener { _, _ ->

                    }
                    .build(this).show()
        }
        rootBinding.btDialog3.setOnClickListener {
            NormalDialog.Builder()
                    .setDialogType(DIALOG_ONEINPUT)
                    .setTitle("提示!")
                    .setTip("是否要删除？")
                    .setPositiveListener { _, _ ->

                    }
                    .build(this).show()
        }

        //一次弹出多个
        rootBinding.btDialogList.setOnClickListener {
            DialogManager.apply {
                //第一个
                show(getDialogFragment(),supportFragmentManager,"1")
                //第二个
                showAtLocation(getPopup(),window.decorView, Gravity.CENTER, 0, 0)
                //第三个
                show(getIosDialogFragment(),supportFragmentManager,"2",1)
                //第四个
                show(getDialogFragment("第4个"),supportFragmentManager,"1",0)
            }
        }
        //延时弹出多个
        rootBinding.btDialogList2.setOnClickListener {
            showList()
        }
    }

    private fun showList(){
        //弹第一个
        DialogManager.show(getDialogFragment(),supportFragmentManager,"1")
        //1秒后弹第二个
        rootBinding.btDialogList2.postDelayed({
            DialogManager.show(getIosDialogFragment(),supportFragmentManager,"2")
        },2000)
        //2000后弹第三个
        rootBinding.btDialogList2.postDelayed({
            DialogManager.showAtLocation(getPopup(),window.decorView, Gravity.CENTER, 0, 0)
        },4000)
    }


    private fun getDialogFragment():CommonlyDialog{
        return CommonlyDialog.Builder()
                .setDialog(FullscreenBottomDialog(this))
                .setRootViewId(R.layout.dialog_fragment_commonly,object :CommonlyDialog.OnSetViewCallBack{
                    override fun onViewCreate(view: View, dialog: AppCompatDialogFragment?) {
                        view.findViewById<TextView>(R.id.btn_selectPositive).setOnClickListener {
                            Toast.makeText(this@DialogActivity,"点到我了！",Toast.LENGTH_SHORT).show()
                            dialog?.dismiss()
                        }
                        view.findViewById<TextView>(R.id.txt_dialog_title).text = "底部"
                    }
                })
                .build()
    }

    private fun getDialogFragment(title:String):CommonlyDialog{
        return CommonlyDialog.Builder()
                .setDialog(FullscreenBottomDialog(this))
                .setRootViewId(R.layout.dialog_fragment_commonly,object :CommonlyDialog.OnSetViewCallBack{
                    override fun onViewCreate(view: View, dialog: AppCompatDialogFragment?) {
                        view.findViewById<TextView>(R.id.btn_selectPositive).setOnClickListener {
                            Toast.makeText(this@DialogActivity,"点到我了！",Toast.LENGTH_SHORT).show()
                            dialog?.dismiss()
                        }
                        view.findViewById<TextView>(R.id.txt_dialog_title).text = title
                    }
                })
                .build()
    }

    private fun getIosDialogFragment():CommonlyDialog{
        return CommonlyDialog.Builder()
                .setDialog(FullScreenIosDialog(this))
                .setRootViewId(R.layout.dialog_fragment_commonly,object :CommonlyDialog.OnSetViewCallBack{
                    override fun onViewCreate(view: View, dialog: AppCompatDialogFragment?) {
                        view.findViewById<TextView>(R.id.btn_selectPositive).setOnClickListener {
                            Toast.makeText(this@DialogActivity,"点到我了！",Toast.LENGTH_SHORT).show()
                            dialog?.dismiss()
                        }
                        view.findViewById<TextView>(R.id.txt_dialog_title).text = "IOS风格"
                    }
                })
                .build()
    }

    private fun getPopup():PopupWindow{
        val v = LayoutInflater.from(this@DialogActivity).inflate(R.layout.popupwindow_task_not_finish, null, false)
        val taskPopupWindow = PopupWindow(v, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        v.findViewById<TextView>(R.id.tv_desc).setOnClickListener {
            taskPopupWindow.dismiss()
        }
        taskPopupWindow.isOutsideTouchable = false
        taskPopupWindow.isFocusable = true
        taskPopupWindow.setBackgroundDrawable(ColorDrawable()) //getResources().getColor(R.color.f_color_000000_75)
        taskPopupWindow.isClippingEnabled = false
        return taskPopupWindow
    }

}