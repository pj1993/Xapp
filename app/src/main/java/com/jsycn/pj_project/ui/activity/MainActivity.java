package com.jsycn.pj_project.ui.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jsycn.pj_project.R;
import com.jsycn.pj_project.core.entity.MessageBean;
import com.jsycn.pj_project.testcls.activitymode.AModeAct;
import com.jsycn.pj_project.testcls.fragmenttest.FragmentLifeTestActivity;
import com.jsycn.pj_project.ui.widget.MessageFloatingView;
import com.jsyncpj.floating.FloatingCtrl;
import com.jsyncpj.floating.model.FloatingIntent;

//测试提交
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_handler;
    private CountDownTimer countDownTimer = new CountDownTimer(10000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tv_handler.setText(""+millisUntilFinished);
        }

        @Override
        public void onFinish() {
            tv_handler.setText("结束");
        }
    };

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
        findViewById(R.id.bt_leak).setOnClickListener(this);
        findViewById(R.id.bt_floating_my).setOnClickListener(this);
        findViewById(R.id.bt_countDown).setOnClickListener(this);
        findViewById(R.id.bt_countDown_cancel).setOnClickListener(this);
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
//                DoraemonKit.setSystemFloat(true);
//                DoraemonKit.install(getApplication());
//                DoraemonKit.show(this,MainIconDokitView.class);
                break;
            case R.id.bt_xfc_test_no:
//                DoraemonKit.setSystemFloat(false);
//                DoraemonKit.install(getApplication());
//                DoraemonKit.show(this, AdsorptionDokitView.class);
                break;
            case R.id.bt_leak:
                startActivity(new Intent(MainActivity.this, LeakCanaryActivity.class));
                break;
            case R.id.bt_floating_my:
//                FloatingCtrl.setSystemFloat(false);
//                FloatingCtrl.install(getApplication());
                //初始化放在app中
                FloatingIntent intent = new FloatingIntent(MessageFloatingView.class);
                intent.setMode(FloatingIntent.MODE_SINGLE_INSTANCE);
                intent.setBundle(new Bundle());
                intent.getBundle().putSerializable("MessageFloatingView",new MessageBean());
                FloatingCtrl.show(this,intent);
                break;
            case R.id.bt_countDown:
                countDownTimer.start();//对于暂停的，会重新开始
                break;
            case R.id.bt_countDown_cancel:
//                countDownTimer.onFinish();//没有效果
                countDownTimer.cancel();
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
