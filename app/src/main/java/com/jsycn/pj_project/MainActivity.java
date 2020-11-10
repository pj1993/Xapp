package com.jsycn.pj_project;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.jsycn.pj_project.activity.CoordinatorLayoutActivity;
import com.jsycn.pj_project.activity.LianXupaizhaoTest;
import com.jsycn.pj_project.activity.LottieTestAct;
import com.jsycn.pj_project.activity.SmartRefreshTestActivity;
import com.jsycn.pj_project.testcls.activitymode.AModeAct;
import com.jsycn.pj_project.testcls.fragmenttest.FragmentLifeTestActivity;
import com.jsycn.pj_project.widget.floating.DoraemonKit;
import com.jsycn.pj_project.widget.floating.dokitview.AdsorptionDokitView;
import com.jsycn.pj_project.widget.floating.dokitview.MainIconDokitView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("lifeCycle","MainActivity----------onCreate()");
        tv_handler = findViewById(R.id.tv_handler);
        tv_handler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        //子线程创建handler 必选先给线程指定looper
                        //1 looper。prepar，说明处理消息  在子线程
                        //2使用主线程looper  则处理消息在主线程，
                        //总结 处理消息的线程 在looper绑定的线程里
                        Looper mainLooper = Looper.getMainLooper();
                        Handler handler=new Handler(mainLooper){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                tv_handler.setText("我被更新了");
                            }
                        };
                        handler.sendEmptyMessage(1);
                    }
                }.start();
            }
        });
        findViewById(R.id.tv_img).setOnClickListener(this);
        findViewById(R.id.bt_lottie).setOnClickListener(this);
        findViewById(R.id.bt_life).setOnClickListener(this);
        findViewById(R.id.bt_life_fragment).setOnClickListener(this);
        findViewById(R.id.bt_refresh_test).setOnClickListener(this);
        findViewById(R.id.bt_cl_test).setOnClickListener(this);
        findViewById(R.id.bt_xfc_test).setOnClickListener(this);
        findViewById(R.id.bt_xfc_test_no).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_img:
                startActivity(new Intent(MainActivity.this, LianXupaizhaoTest.class));
                break;
            case R.id.bt_lottie:
                startActivity(new Intent(MainActivity.this, LottieTestAct.class));
                break;
            case R.id.bt_life:
//                showDialog();
                startActivity(new Intent(this, AModeAct.class));
                break;
            case R.id.bt_life_fragment:
                startActivity(new Intent(MainActivity.this, FragmentLifeTestActivity.class));
                break;
            case R.id.bt_refresh_test:
                startActivity(new Intent(MainActivity.this, SmartRefreshTestActivity.class));
                break;
            case R.id.bt_cl_test:
                startActivity(new Intent(MainActivity.this, CoordinatorLayoutActivity.class));
                break;
            case R.id.bt_xfc_test:
                //初始化didi哆啦A梦
                DoraemonKit.setSystemFloat(true);
                DoraemonKit.install(getApplication());
                DoraemonKit.show(this,MainIconDokitView.class);
                break;
            case R.id.bt_xfc_test_no:
                DoraemonKit.setSystemFloat(false);
                DoraemonKit.install(getApplication());
                DoraemonKit.show(this, AdsorptionDokitView.class);
                break;
        }
    }
    private void showDialog(){
        new AlertDialog.Builder(this).setTitle("12").setMessage("asd").create().show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifeCycle","MainActivity----------onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifeCycle","MainActivity----------onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifeCycle","MainActivity----------onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifeCycle","MainActivity----------onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifeCycle","MainActivity----------onDestroy()");
    }
}
