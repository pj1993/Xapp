package com.jsycn.pj_project.testcls.activitymode;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.jsycn.pj_project.R;

public class BModeAct extends LifeLogActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b_mode);

        findViewById(R.id.tv_startC).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BModeAct.this,CModeAct.class));
            }
        });
        findViewById(R.id.tv_startD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(BModeAct.this,DModeAct.class));
                Intent in = new Intent("com.jsycn.pj_project.OneActivity");
                //in.setFlags(FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
            }
        });
        Log.e("taskId","B---"+getTaskId());
    }
}