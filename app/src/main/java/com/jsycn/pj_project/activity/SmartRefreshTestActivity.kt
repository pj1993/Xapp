package com.jsycn.pj_project.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jsycn.pj_project.R
import kotlinx.android.synthetic.main.activity_smart_refresh_test.*

class SmartRefreshTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_smart_refresh_test)
        initView()
    }
    private fun initView(){
        val data = mutableListOf<String>()
        for (i in 1..100){
            data.add("${i}条目,")
        }

        rv_d.layoutManager = LinearLayoutManager(this)
        rv_d.adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_rv_string,data){
            override fun convert(holder: BaseViewHolder, item: String) {
                holder.setText(R.id.tv_text,item)
            }
        }
    }

}