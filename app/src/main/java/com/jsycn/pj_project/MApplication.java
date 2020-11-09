package com.jsycn.pj_project;

import android.app.Activity;
import android.app.Application;


import androidx.lifecycle.ProcessLifecycleOwner;

//import com.didichuxing.doraemonkit.DoraemonKit;
//import com.didichuxing.doraemonkit.kit.AbstractKit;

import com.jsycn.pj_project.widget.floating.DoraemonKit;
import com.jsycn.pj_project.widget.floating.constant.DokitConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pj on 2019/3/18.
 * 刺.刺.刺激
 * application
 */
public class MApplication extends Application {
    List<Activity> aList = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(new ProcessLifecycleObserver());
    }

}
