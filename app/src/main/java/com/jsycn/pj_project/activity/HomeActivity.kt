package com.jsycn.pj_project.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.jsycn.base.BaseActivity
import com.jsycn.pj_project.R
import com.jsycn.pj_project.fragment.HomeFragment
import com.jsycn.pj_project.fragment.ToolsFragment
import com.jsycn.pj_project.fragment.ViewFragment
import kotlinx.android.synthetic.main.activity_home.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView


class HomeActivity : BaseActivity() {

    companion object{
        val tabTitle : Array<String> = arrayOf("首页","view","工具")
        val selectedIcon : IntArray = intArrayOf(R.drawable.ic_icon_home_selected,R.drawable.ic_icon_view_selected,R.drawable.ic_icon_tools_selected)
        val normalIcon : IntArray = intArrayOf(R.drawable.ic_icon_home_normal,R.drawable.ic_icon_view_normal,R.drawable.ic_icon_tools_normal)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
        window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
        super.onCreate(savedInstanceState)
    }

    override fun initLayout(): Int {
        return R.layout.activity_home
    }

    override fun initView() {
        initViewPager()
        initIndicator()
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    private fun initIndicator(){
        val commonNavigator = CommonNavigator(this)
        commonNavigator.isAdjustMode = true
        commonNavigator.adapter = object : CommonNavigatorAdapter(){
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val tabView = CommonPagerTitleView(context)
                val view = LayoutInflater.from(context).inflate(R.layout.tab_home,null)
                val iv_tab = view.findViewById<ImageView>(R.id.iv_tab)
                val tv_title = view.findViewById<TextView>(R.id.tv_title)
                tv_title.text = tabTitle[index]
                tabView.setContentView(view)
                tabView.onPagerTitleChangeListener = object : CommonPagerTitleView.OnPagerTitleChangeListener{
                    override fun onDeselected(index: Int, totalCount: Int) {
                        tv_title.setTextColor(ContextCompat.getColor(this@HomeActivity,R.color.textNormalColor))
                        iv_tab.setImageResource(normalIcon[index])
                    }

                    override fun onSelected(index: Int, totalCount: Int) {
                        tv_title.setTextColor(ContextCompat.getColor(this@HomeActivity,R.color.textSelectedColor))
                        iv_tab.setImageResource(selectedIcon[index])
                    }

                    override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
                        iv_tab.scaleX = 1.3f + (0.9f-1.2f)*leavePercent
                        iv_tab.scaleY = 1.3f + (0.9f-1.2f)*leavePercent
                    }

                    override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
                        iv_tab.scaleX = 1.3f + (1.2f-0.9f)*enterPercent
                        iv_tab.scaleY = 1.3f + (1.2f-0.9f)*enterPercent
                    }

                }
                tabView.setOnClickListener {
                    vp_home_content.currentItem = index
                }
                return tabView
            }

            override fun getCount(): Int {
                return tabTitle.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator? {
                return null
            }
        }
        mi_home_tab.navigator = commonNavigator
        ViewPagerHelper.bind(mi_home_tab,vp_home_content)
    }

    private fun initViewPager(){
        val mAdapter = HomePagerAdapter(supportFragmentManager).apply {
            addData(HomeFragment())
            addData(ViewFragment())
            addData(ToolsFragment())
        }
        vp_home_content.adapter =mAdapter
    }


    class HomePagerAdapter(fm : FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        val data = mutableListOf<Fragment>()

        public fun addData(f:Fragment){
            data.add(f)
        }

        public fun addNewData(fs:MutableList<Fragment>){
            data.clear()
            data.addAll(fs)
        }

        override fun getItem(position: Int): Fragment {
            return data[position]
        }

        override fun getCount(): Int {
            return data.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return tabTitle[position]
        }

    }
}