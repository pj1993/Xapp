package com.jsycn.pj_project;

import android.app.Activity;
import android.app.Application;
import android.util.Log;


import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;


import com.didichuxing.doraemonkit.DoraemonKit;
import com.jsyncpj.floating.FloatingCtrl;

import java.util.ArrayList;
import java.util.List;

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

}
