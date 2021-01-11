package com.jsycn.pj_project.activity

import android.content.Context
import android.os.Bundle
import com.jsycn.base.BaseActivity
import com.jsycn.pj_project.R
import kotlinx.android.synthetic.main.activity_home.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView


class HomeActivity : BaseActivity() {
    val tabTitle : Array<String> = arrayOf("首页","view","工具")
    override fun initLayout(): Int {
        return R.layout.activity_home
    }

    override fun initView() {
        val commonNavigator = CommonNavigator(this)
        commonNavigator.adapter = object : CommonNavigatorAdapter(){
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val view = CommonPagerTitleView(context)

                return view
            }

            override fun getCount(): Int {
                return tabTitle.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_WRAP_CONTENT
                return indicator
            }
        }
        mi_home_tab.navigator = commonNavigator
        ViewPagerHelper.bind(mi_home_tab,vp_home_content)
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

}