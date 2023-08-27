package com.jsycn.pj_project.testcls.activitymode;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LifeLogActivity  extends AppCompatActivity {
    protected String TAG = this.getClass().getName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,this.getClass().getName()+"-----onCreate------");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG,this.getClass().getName()+"-----onStart------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG,this.getClass().getName()+"-----onResume------");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG,this.getClass().getName()+"-----onPause------");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,this.getClass().getName()+"-----onStop------");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,this.getClass().getName()+"-----onRestart------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,this.getClass().getName()+"-----onDestroy------");
    }
}
