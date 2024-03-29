package com.jsycn.pj_project.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jsycn.pj_project.ui.activity.HomeActivity;

import static com.jsycn.pj_project.core.utils.StatusBarUtilsKt.getStatusBarHeight;
import static com.jsycn.pj_project.ui.activity.view.DialogActivityKt.setFullScreen;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


/**
 * Created by pj on 2019/3/18.
 * 刺.刺.刺激
 * 冷启动优化  给window 设置背景(不会出现白屏或者黑屏)  layer_list的纯色背景  或者直接img背景
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                    SplashActivity.this.finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        setFullScreen(this);

        //获取状态栏高度
        getStatusBarHeight(this, integer -> {
            //获取到状态栏高度

            return null;
        });
    }

}
