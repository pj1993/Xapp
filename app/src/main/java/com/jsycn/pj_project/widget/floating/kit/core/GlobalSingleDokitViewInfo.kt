package com.jsycn.pj_project.widget.floating.kit.core

import android.os.Bundle

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2019-09-29-17:39
 * 描    述：关于全局dokitView的基本信息 由于普通的浮标是每个页面自己管理的
 * 需要有一个map用来保存当前每个类型的dokitview 便于新开页面和页面resume时的dokitview添加
 * 修订历史：
 * ================================================
 */
internal data class GlobalSingleDokitViewInfo(val absDokitViewClass: Class<out AbsDokitView?>,
                                              val tag: String,
                                              val mode: Int,
                                              val bundle: Bundle?)



