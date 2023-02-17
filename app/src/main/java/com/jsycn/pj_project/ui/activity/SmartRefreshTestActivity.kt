package com.jsycn.pj_project.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.jsycn.pj_project.R
import com.jsycn.pj_project.core.entity.WebVo
import com.jsycn.pj_project.databinding.ActivitySmartRefreshTestBinding
import com.jsycn.pj_project.ui.widget.webview.JSAppBridgeImpl
import com.jsycn.pj_project.ui.widget.webview.QuoteWebFragment

class SmartRefreshTestActivity : AppCompatActivity() {
    private lateinit var rootBinding : ActivitySmartRefreshTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootBinding = ActivitySmartRefreshTestBinding.inflate(layoutInflater)
        setContentView(rootBinding.root)
        initView()
    }
    private fun initView(){
//        val data = mutableListOf<String>()
//        for (i in 1..100){
//            data.add("${i}条目,")
//        }
//
//        rv_d.layoutManager = LinearLayoutManager(this)
//        rv_d.adapter = object : BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_rv_string,data){
//            override fun convert(holder: BaseViewHolder, item: String) {
//                holder.setText(R.id.tv_text,item)
//            }
//        }
        val fs = mutableListOf<Fragment>()
        val webFragment = QuoteWebFragment()
        val webVo = WebVo("https://hd.get88.cn/xiguatest/xiguaGuess/#/?szhidden=1", JSAppBridgeImpl(), false)
        val args = Bundle()
        args.putSerializable(WebVo.WEB_VO, webVo)
        webFragment.arguments = args
        rootBinding.viewPager.adapter = MyFragmentAdapter(supportFragmentManager,fs)
    }

}

class MyFragmentAdapter(fm : FragmentManager, var fragmentList :MutableList<Fragment> ) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

}