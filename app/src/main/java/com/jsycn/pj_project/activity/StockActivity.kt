package com.jsycn.pj_project.activity

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jsycn.base.BaseActivity
import com.jsycn.pj_project.R
import com.jsycn.pj_project.activity.dialog.CommonlyDialog
import com.jsycn.pj_project.activity.dialog.FullScreenIosDialog
import com.jsycn.pj_project.dao.StockDataBase
import com.jsycn.pj_project.entity.StockBean
import com.jsycn.pj_project.utils.getStatusBarHeight
import kotlinx.android.synthetic.main.activity_stock.*
import kotlinx.coroutines.*

/**
 * Author: jsync
 * Date: 2021/2/9 15:25
 * Description: 股票相关
 * 长期：
 * 用于股票：买入点  卖出点 对应  ，可以有多只股票
 * 计算买入点和卖出点的差价，计算盈亏，底仓不动，底仓一只保持2w，一只股票的底仓价干到0后换下一只，预期值。
 *
 * 短期：
 * 严格规定涨跌幅的买入卖出，到规定点必须抛售
 *
 * History:
 */
const val DATA_BASE_NAME = "pms"
class StockActivity : BaseActivity(){
    private lateinit var adapter: BaseQuickAdapter<StockBean,BaseViewHolder>
    private val db by lazy{
        Room.databaseBuilder(applicationContext,StockDataBase::class.java,DATA_BASE_NAME)
                .fallbackToDestructiveMigrationFrom(3)
                .build()
    }
    private val scope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //沉浸式：方案，透明状态栏，顶到头,设置占位状态栏view
        if (Build.VERSION.SDK_INT >= 21) {
            val decorView = window.decorView
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
        }
        super.onCreate(savedInstanceState)
    }
    override fun initLayout(): Int {
        return R.layout.activity_stock
    }

    override fun initView() {
        rv_stock.layoutManager = LinearLayoutManager(this)
        adapter= object :BaseQuickAdapter<StockBean,BaseViewHolder>(R.layout.item_rv_stock,mutableListOf()){
            override fun convert(holder: BaseViewHolder, item: StockBean) {
                holder.setText(R.id.tv_name,item.stockName)
                        .setText(R.id.tv_code,item.stockCode)
            }
        }
        rv_stock.adapter = adapter
        tv_add_stock.setOnClickListener {
            addStock()
        }
        //状态栏
        getStatusBarHeight(this) { height ->
            val params = v_status.layoutParams as ConstraintLayout.LayoutParams
            params.height = height
            v_status.layoutParams = params
            v_status.visibility = View.VISIBLE
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        scope.launch {
            val all=withContext(Dispatchers.IO){
                db.stockDao().getAll()
            }
            adapter.setList(all)
        }
    }

    private fun addStock(){
        CommonlyDialog.Builder()
                .setDialog(FullScreenIosDialog(this))
                .setRootViewId(R.layout.dialog_add_stock,object :CommonlyDialog.OnSetViewCallBack{
                    override fun onViewCreate(view: View, dialog: AppCompatDialogFragment?) {
                        view.findViewById<TextView>(R.id.btn_selectNegative).setOnClickListener {
                            dialog?.dismiss()
                        }
                        //确定
                        view.findViewById<TextView>(R.id.btn_selectPositive).setOnClickListener {
                            val name = view.findViewById<EditText>(R.id.txt_input_name).text.toString().trim()
                            val code = view.findViewById<EditText>(R.id.txt_input_code).text.toString().trim()
                            if (name.isBlank()||code.isBlank()){
                                ToastUtils.showShort("code和名称不能为空！")
                            }else{
                                scope.launch {
                                    withContext(Dispatchers.IO){
                                        db.stockDao().insertAll(StockBean(null,code,name))
                                    }
                                    ToastUtils.showShort("新增完成!")
                                    initData(null)
                                    dialog?.dismiss()
                                }
                            }
                        }
                    }
                })
                .build().show(supportFragmentManager,StockActivity::class.simpleName)
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}