package com.jsycn.pj_project;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.lifecycle.ProcessLifecycleOwner;

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
