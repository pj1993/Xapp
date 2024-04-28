package com.jsycn.pj_project.ui.activity;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CyclicBarrier;

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
        //CyclicBarrier a = new CyclicBarrier(4);  //java线程的同步屏障
        //CompletableFuture
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
        findViewById(R.id.bt_qdms).setOnClickListener(this);
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
            case R.id.bt_qdms:
                //启动下一个其他app的activity
                Intent in = new Intent("com.test.action.BActivity");
                //in.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);

                //情况1：单独设置FLAG_ACTIVITY_NEW_TASK，bAct是标准启动模式，则会在bAct的task中按照标准模式创建出b，然后将b的task叠加到当前app的task中
                //比如：当前app的task回退栈中是 1,2。bAct所在的app中的回退栈是a,b,c。
                //这时2启动c
                //Intent in = new Intent("com.test.action.BActivity");
                //in.setFlags(FLAG_ACTIVITY_NEW_TASK);
                //startActivity(in);
                //回退栈将会变成 1,2,,a,b,c,c。会跨task，会将当前app的task和bAct的task叠加，但是一旦切换到任务视图或按home键，叠加task将会分开。



                //情况2：设置FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP，bAct是标准启动模式
                //比如：当前app的task回退栈中是 1,2。bAct所在的app中的回退栈是a,b,c,d。
                //这时2启动b，
                //Intent in = new Intent("com.test.action.BActivity");
                //in.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
                //startActivity(in);
                //b,c,d会先出栈，然后重新创建b，bAct中的task就只剩下a,b。然后将这个task合并到当前task中


                //结论
                //FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP 和 singleTask是不同
                //singleTask模式中是不会将bAct出栈再重启onCreate的

                //扩展
                //就是说在activity上下文之外调用startActivity需要FLAG_ACTIVITY_NEW_TASK属性
                //比如广播


                //第五种启动模式 SingleInstancePerTask（自我翻译为:每个task中唯一的act）
                //和singleTask很像，
                //区别 1
                // 但是singleTask只能在指定的task或者默认的task中创建Act
                //而SingleInstancePerTask设置了Intent.FLAG_ACTIVITY_MULTIPLE_TASK或Intent.FLAG_ACTIVITY_NEW_DOCUMENT后
                //会创建新的task，比如自己打开自己都会创建一个新的task
                //区别2 (1个只会在栈底)
                //singleInstancePerTask 会在系统已存在没有该实例的同 taskAffinity 任务栈时，重新开启一个栈，而 singleTask 则会直接在该栈顶创建 Activity
                //比如 1-2-3-1-2-3-1,这种会有两个栈 一：1,2   二：3,1（这没有特殊设置，再次启动3时，12出栈，然后启动1入栈）
                //启动3的时候就会创建一个同taskAffinity的栈，并且里面还能放1


                //singleInstance
                //情况3：甲app的A，B，C页面，乙app的1页面
                //假设 A->B->1->C    其中1是singleInstance1
                //则回退栈叠加后，任务视图里面 排序为  C->B->A->1(已验证，会有1，但是如果情况变成启动ABC，其中B是singleInstance，则不会出现B，只有CA)
                //也就是说singleInstance不会影响当前回退栈的顺序，

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
