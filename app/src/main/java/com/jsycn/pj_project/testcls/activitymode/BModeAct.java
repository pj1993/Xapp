package com.jsycn.pj_project.testcls.activitymode;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import com.jsycn.pj_project.R;

public class BModeAct extends AppCompatActivity {

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
                startActivity(new Intent(BModeAct.this,DModeAct.class));
            }
        });
        Log.e("taskId","B---"+getTaskId());
    }
}