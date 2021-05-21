package com.jsycn.pj_project.widget.webview;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;


/**
 * View层公共方法
 *
 * @author zhang.zheng
 * @version 2017-12-16
 */
public interface BaseView {

    void showToast(String message);

    void showLoading(String message);

    void hideLoading();

//    <T> AutoDisposeConverter<T> bindLifecycle();
//
//    <T> AutoDisposeConverter<T> bindLifecycle(Lifecycle.Event event);
//
//    default void onRefresh(){}
//
//    default void subOn() {
//        LogUtils.d("BaseView", "subOn");
//    }
//
//    default void subOff() {
//        LogUtils.d("BaseView", "subOff");
//    }
//
//    /**
//     * ViewPager 管理Fragment 生命周期控制
//     *
//     * @param hidden
//     * @param viewPager
//     * @param fragmentStatePagerAdapter
//     */
//    default void onHiddenChanged(boolean hidden, ViewPager viewPager, FragmentStatePagerAdapter fragmentStatePagerAdapter) {
//        if (viewPager != null && fragmentStatePagerAdapter != null) {
//            Fragment fragment = fragmentStatePagerAdapter.getItem(viewPager.getCurrentItem());
//            if (fragment != null && fragment instanceof com.libs.core.common.base.BaseView) {
//                if (hidden)
//                    ((com.libs.core.common.base.BaseView) fragment).subOff();
//                else
//                    ((com.libs.core.common.base.BaseView) fragment).subOn();
//            }
//        }
//    }
//
//    /**
//     * ViewPager 管理Fragment 生命周期控制
//     *
//     * @param hidden
//     * @param viewPager
//     * @param fragmentStatePagerAdapter
//     */
//    default void onHiddenChanged(boolean hidden, ViewPager viewPager, FragmentPagerAdapter fragmentStatePagerAdapter) {
//        if (viewPager != null && fragmentStatePagerAdapter != null) {
//            Fragment fragment = fragmentStatePagerAdapter.getItem(viewPager.getCurrentItem());
//            if (fragment != null && fragment instanceof com.libs.core.common.base.BaseView) {
//                if (hidden)
//                    ((com.libs.core.common.base.BaseView) fragment).subOff();
//                else
//                    ((com.libs.core.common.base.BaseView) fragment).subOn();
//            }
//        }
//    }
}
