package com.jsycn.pj_project.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jsycn.pj_project.R
import kotlinx.android.synthetic.main.activity_coordinator_layout.*

/**
 * 详细请看  收藏的blog，里面有各种flag 的用法，还有自定义behavior，结合之前有写过一个behavior
 * 还可以通过监听appbarLayout的滑动，来实现变换
 */
class CoordinatorLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator_layout)
        initView()
    }
    private fun initView(){
        val data = mutableListOf<String>()
        for (i in 0..100){
            data.add("${i}个元素")
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_rv_string,data){
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.setText(R.id.tv_text,item)
            }
        }
    }

}