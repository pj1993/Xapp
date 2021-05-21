package com.jsycn.pj_project.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.android.material.appbar.AppBarLayout
import com.jsycn.pj_project.R
import com.jsycn.pj_project.behavior.AppBarLayoutBehavior
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.api.RefreshFooter
import com.scwang.smart.refresh.layout.api.RefreshHeader
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.constant.RefreshState
import com.scwang.smart.refresh.layout.listener.OnMultiListener
import com.scwang.smart.refresh.layout.simple.SimpleMultiListener
import kotlinx.android.synthetic.main.activity_coordinator_layout.*
import kotlin.math.abs

/**
 * 详细请看  收藏的blog，里面有各种flag 的用法，还有自定义behavior，结合之前有写过一个behavior
 * 还可以通过监听appbarLayout的滑动，来实现变换
 */
class CoordinatorLayoutActivity : AppCompatActivity() {
    var hasShowHeader = true
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
        abl_head.setLiftable(true)
        abl_head.setLifted(true)
//        abl_head.behavior
        abl_head.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            //下滑一定距离，锁住头部不让下拉滑出头部，拉一定高度后允许下拉
            //会一直触发
            if(abs(verticalOffset) >=appBarLayout.getChildAt(0).height){
                showHead(false)
            }
        })
        v_header.setOnClickListener {
            showHead(true)
        }
        srl_rv.setOnMultiListener(object : SimpleMultiListener() {
            var hasShowHead = false
            override fun onHeaderMoving(header: RefreshHeader?, isDragging: Boolean, percent: Float, offset: Int, headerHeight: Int, maxDragHeight: Int) {
                if (offset>340){
                    showHead(true)
                    hasShowHead = true
                }
//                LogUtils.d("scroll_height","滑动时：${hasShowHead}")
            }
            override fun onHeaderReleased(header: RefreshHeader?, headerHeight: Int, maxDragHeight: Int) {
//                if (hasShowHead){
//                    srl_rv.finishRefresh()
//                    srl_rv.refreshState(RefreshState.None)
//                }
            }
            override fun onHeaderStartAnimator(header: RefreshHeader?, headerHeight: Int, maxDragHeight: Int) {

            }
            override fun onHeaderFinish(header: RefreshHeader?, success: Boolean) {
                LogUtils.d("scroll_height","onHeaderFinish：${hasShowHead}")
                hasShowHead = false
            }

            override fun onRefresh(refreshLayout: RefreshLayout) {
                super.onRefresh(refreshLayout)
                LogUtils.d("scroll_height","onRefresh：${hasShowHead}")
                if (hasShowHead){
                    //下拉出头部,不用刷新
                    srl_rv.finishRefresh(0)
                }else{
                    //常规刷新
                    srl_rv.finishRefresh(900)
                }
            }

            override fun onStateChanged(refreshLayout: RefreshLayout, oldState: RefreshState, newState: RefreshState) {
                super.onStateChanged(refreshLayout, oldState, newState)
            }
        })
    }

    private fun showHead(show:Boolean){
        if (hasShowHeader == show) return
        val lp =abl_head.layoutParams
        if (lp is CoordinatorLayout.LayoutParams){
            val b = lp.behavior
            //头部锁起来
            if (b is AppBarLayoutBehavior){
                b.isVerticalOffsetEnabled = show
            }
        }
        val h = srl_rv.refreshHeader
        if (h is ClassicsHeader){
            if (show){//强拉出头部
                h.setFinishDuration(0)
            }else{//关闭头部
                h.setFinishDuration(500)
            }
        }
        srl_rv.setEnableRefresh(!show)
        hasShowHeader = show
    }

}