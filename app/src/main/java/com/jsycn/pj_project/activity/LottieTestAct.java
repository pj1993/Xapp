package com.jsycn.pj_project.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.jsycn.pj_project.R;

/**
 * create by pj on 2019/10/30
 * 刺.刺.刺激...
 */
public class LottieTestAct extends AppCompatActivity {


    private LottieAnimationView mAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_lottie);
        Log.d("lifeCycle","OtherActivity----------onCreate()");
        mAnimationView = findViewById(R.id.lt_top);

        mAnimationView.setAnimation("antui_refresh_blue.json");
//        mAnimationView.useHardwareAcceleration(true);
        mAnimationView.setRepeatCount(1);//设置-1表示循环

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
//            mAnimationView.playAnimation();
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            mAnimationView.cancelAnimation();
        }else if(MotionEvent.ACTION_MOVE==event.getAction()){
            float x = event.getX();
            float progress=x/300f;
            if(progress>1.0){
                progress=1.0f;
            }
            mAnimationView.setProgress(progress);
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifeCycle","OtherActivity----------onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifeCycle","OtherActivity----------onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifeCycle","OtherActivity----------onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifeCycle","OtherActivity----------onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifeCycle","OtherActivity----------onDestroy()");
    }

}
