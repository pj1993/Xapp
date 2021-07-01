package com.jsycn.pj_project.ui.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.constant.RefreshState;

/**
 * @Description:
 * @Author: jsync
 * @CreateDate: 2021/5/21 18:26
 */
public class PowerSmartRefreshLayout extends SmartRefreshLayout {
    public PowerSmartRefreshLayout(Context context) {
        super(context);
    }

    public PowerSmartRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void refreshStatus(RefreshState state){
        notifyStateChanged(state);
    }

//    public void finishHead(){
//        mRefreshHeader.onFinish(this, false);
//    }
}
