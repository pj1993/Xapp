package com.jsycn.pj_project.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;


import com.didichuxing.doraemonkit.DoraemonKit;
import com.jsycn.pj_project.R;
import com.jsyncpj.floating.FloatingCtrl;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;

import leakcanary.AppWatcher;

/**
 * Created by pj on 2019/3/18.
 * 刺.刺.刺激
 * application
 */
public class MApplication extends Application {
    public static Activity leakActivity;

    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ProcessLifecycleObserver());
        AppWatcher.Config build = AppWatcher.getConfig()
                .newBuilder()
                .watchFragmentViews(false)
                .build();
        AppWatcher.setConfig(build);
        FloatingCtrl.setSystemFloat(false);
        FloatingCtrl.install(this);
        DoraemonKit.install(this);
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ApplicationObserver());
    }

    /**
     * 前后台监听
     */
    public static class ApplicationObserver implements LifecycleObserver {
        private String tag = "ApplicationObserver";
        @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
        public void onCreate(){

        }
        @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
        public void onResume(){
            Log.d(tag,"前台");
        }
        //有延迟
        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        public void onStop(){
            Log.d(tag,"后台");
        }

    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.black);//全局设置主题颜色
                layout.setEnableAutoLoadMore(false);
                layout.setEnableOverScrollBounce(false);
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                ClassicsFooter classicsFooter = new ClassicsFooter(context).setDrawableSize(20);
                classicsFooter.setAccentColor(Color.parseColor("#000000"));//设置强调颜色
                return classicsFooter;
            }
        });
    }
}
