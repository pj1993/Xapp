package com.jsycn.pj_project.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.jsycn.base.BaseActivity
import com.jsycn.pj_project.R
import com.jsycn.pj_project.ui.activity.dialog.CommonlyDialog
import com.jsycn.pj_project.ui.activity.dialog.FullScreenIosDialog
import com.jsycn.pj_project.core.dao.LAST_DATABASE_VERSION
import com.jsycn.pj_project.core.dao.StockDataBase
import com.jsycn.pj_project.core.entity.StockBean
import com.jsycn.pj_project.core.utils.getStatusBarHeight
import com.jsycn.pj_project.databinding.ActivityStockBinding
import kotlinx.coroutines.*
import java.util.*

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
    private lateinit var rootBinding : ActivityStockBinding
    private lateinit var adapter: BaseQuickAdapter<StockBean, QuickViewHolder>
    private val db by lazy{
        Room.databaseBuilder(applicationContext,StockDataBase::class.java,DATA_BASE_NAME)
                .fallbackToDestructiveMigrationFrom(LAST_DATABASE_VERSION)
                .build()
    }
    private val scope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }


    override fun initLayout(): View {
        rootBinding = ActivityStockBinding.inflate(layoutInflater)
        return rootBinding.root
    }

    override fun initView() {
        rootBinding.rvStock.layoutManager = LinearLayoutManager(this)
        adapter= object :BaseQuickAdapter<StockBean,QuickViewHolder>(mutableListOf()){
            override fun onCreateViewHolder(
                context: Context,
                parent: ViewGroup,
                viewType: Int
            ): QuickViewHolder {
                return QuickViewHolder(R.layout.item_rv_stock,parent)
            }
            override fun onBindViewHolder(
                holder: QuickViewHolder,
                position: Int,
                item: StockBean?
            ) {
                holder.setText(R.id.tv_name,item?.stockName)
                    .setText(R.id.tv_code,item?.stockCode)
            }


        }
        adapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as? StockBean
            val intent = Intent(this@StockActivity,StockDetailsActivity::class.java)
            intent.putExtra("stockId",item?.stockId?:0)
            intent.putExtra("stockName",item?.stockName)
            startActivity(intent)
        }
        rootBinding.rvStock.adapter = adapter
        rootBinding.tvAddStock.setOnClickListener {
            addStock()
        }
        //状态栏
        getStatusBarHeight(this) { height ->
            val params = rootBinding.vStatus.layoutParams as ConstraintLayout.LayoutParams
            params.height = height
            rootBinding.vStatus.layoutParams = params
            rootBinding.vStatus.visibility = View.VISIBLE
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        scope.launch {
            val all=withContext(Dispatchers.IO){
                db.stockDao().getAll()
            }
            adapter.submitList(all)
            //adapter.setList(all)
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
//                                        db.stockDao().insertAll(StockBean(UUID.randomUUID().toString(),code,name))
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