package com.jsycn.pj_project.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.blankj.utilcode.util.SPUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jsycn.base.BaseActivity
import com.jsycn.pj_project.R
import com.jsycn.pj_project.ui.activity.dialog.CommonlyDialog
import com.jsycn.pj_project.ui.activity.dialog.FullScreenIosDialog
import com.jsycn.pj_project.core.dao.LAST_DATABASE_VERSION
import com.jsycn.pj_project.core.dao.StockDataBase
import com.jsycn.pj_project.core.entity.StockDetails
import com.jsycn.pj_project.core.utils.getStatusBarHeight
import com.jsycn.pj_project.core.utils.parse2String
import kotlinx.android.synthetic.main.activity_stock_details.*
import kotlinx.coroutines.*
import kotlin.math.abs

/**
 * Author: jsync
 * Date: 2021/2/20 13:45
 * Description:
 * History:
 */
class StockDetailsActivity : BaseActivity(){
    private lateinit var adapter: BaseQuickAdapter<StockDetails,BaseViewHolder>
    private val db by lazy{
        Room.databaseBuilder(applicationContext, StockDataBase::class.java,DATA_BASE_NAME)
                .fallbackToDestructiveMigrationFrom(LAST_DATABASE_VERSION)
                .build()
    }
    private val scope by lazy {
        CoroutineScope(Dispatchers.Main + Job())
    }
    override fun initLayout(): Int {
        return R.layout.activity_stock_details
    }
    private var stockId =-1

    override fun initView() {
        //状态栏
        getStatusBarHeight(this) { height ->
            val params = v_status.layoutParams as ConstraintLayout.LayoutParams
            params.height = height
            v_status.layoutParams = params
            v_status.visibility = View.VISIBLE
        }
        rv_stock_details.layoutManager = LinearLayoutManager(this)
        adapter = object :BaseQuickAdapter<StockDetails,BaseViewHolder>(R.layout.item_rv_stock,mutableListOf()){
            override fun convert(holder: BaseViewHolder, item: StockDetails) {

            }
        }
        //点击事件
        tv_price_predict_desc.setOnClickListener {
            setPredictPrice()
        }
        tv_price_predict.setOnClickListener {
            setPredictPrice()
        }
        //买入
        tv_buy.setOnClickListener {
            buyOrSell(true)
        }
        //卖出
        tv_sell.setOnClickListener {
            buyOrSell(false)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        stockId = intent.getIntExtra("stockId",-1)
        tv_title.text = intent.getStringExtra("stockName")
        //预售价格
        tv_price_predict.text = SPUtils.getInstance().getString(stockId.toString())
        if (stockId == -1) return
        getStockDetails()
    }
    private fun getStockDetails(){
        scope.launch {
            val all=withContext(Dispatchers.IO){
                db.stockDetailsDao().getStockDetailsById(stockId)
            }
            adapter.setList(all)
            if (all.isNullOrEmpty()) return@launch
            //计算 仓量，当前平均价格，
            var qtySum = 0
            var priceCount = 0.0
            for (item in all) {
                if (item.price<0){
                    //买入
                    qtySum += item.quantity
                }else{
                    //卖出
                    qtySum -= item.quantity
                }
                priceCount+=item.price*item.quantity
            }
            tv_qty.text = "$qtySum"
            tv_price_in.text = parse2String(-priceCount/qtySum,2,false)
        }
    }

    private fun buyOrSell(isBuy:Boolean){
        CommonlyDialog.Builder()
                .setDialog(FullScreenIosDialog(this))
                .setRootViewId(R.layout.dialog_add_stock_details,object :CommonlyDialog.OnSetViewCallBack{
                    override fun onViewCreate(view: View, dialog: AppCompatDialogFragment?) {
                        view.findViewById<TextView>(R.id.txt_dialog_title).text = if(isBuy){"买入"}else{"卖出"}
                        view.findViewById<TextView>(R.id.btn_selectNegative).setOnClickListener {
                            dialog?.dismiss()
                        }
                        //确定
                        view.findViewById<TextView>(R.id.btn_selectPositive).setOnClickListener {
                            val qty = try {
                                view.findViewById<EditText>(R.id.txt_input_qty).text.toString().trim().toInt()
                            } catch (e: Exception) {
                                0
                            }
                            var price = try {
                                view.findViewById<EditText>(R.id.txt_input_price).text.toString().trim().toDouble()
                            } catch (e: Exception) {
                                0.0
                            }
                            if (qty==0||price==0.0){
                                ToastUtils.showShort("数量和价格不能为0！")
                            }else{
                                scope.launch {
                                    withContext(Dispatchers.IO){
                                        //加上手续费
                                        //佣金（按照成交金额乘以佣金比例，不足五元按五元收取）
                                        //印花税（按照成交金额的千分之一，卖出时收取）
                                        //过户费（沪市按照成交金额的万分之零点二收取，买卖双向收取，深市不单独收取）
                                        if (isBuy){
                                            //佣金,不足5块按5块
                                            val yj = abs(price * qty * 2.5 / 10000).coerceAtLeast(5.0)
                                            //过户费
                                            val ghf = abs(price * qty * 0.2 / 10000)
                                            price = (-price*qty -yj -ghf)/qty
                                        }else{
                                            //印花税（按照成交金额的千分之一，卖出时收取）
                                            val yhs = abs(price * qty / 1000)
                                            price -= yhs
                                        }
                                        db.stockDetailsDao().insertAll(StockDetails(null,stockId,qty,price))
                                    }
                                    ToastUtils.showShort("新增完成!")
                                    getStockDetails()
                                    dialog?.dismiss()
                                }
                            }
                        }
                    }
                })
                .build().show(supportFragmentManager,StockActivity::class.simpleName)
    }

    private fun setPredictPrice(){
        CommonlyDialog.Builder()
                .setDialog(FullScreenIosDialog(this))
                .setRootViewId(R.layout.dialog_predict_price,object :CommonlyDialog.OnSetViewCallBack{
                    override fun onViewCreate(view: View, dialog: AppCompatDialogFragment?) {
                        view.findViewById<TextView>(R.id.btn_selectNegative).setOnClickListener {
                            dialog?.dismiss()
                        }
                        //确定
                        view.findViewById<TextView>(R.id.btn_selectPositive).setOnClickListener {
                            val price = view.findViewById<EditText>(R.id.txt_input_price).text.toString()
                            tv_price_predict.text = price
                            SPUtils.getInstance().put(stockId.toString(),price)
                            dialog?.dismiss()
                        }
                    }
                })
                .build().show(supportFragmentManager,StockActivity::class.simpleName)
    }
}