package com.jsycn.pj_project.testcls.activitymode;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jsycn.pj_project.R;

public class DModeAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_mode);
        startActivity(new Intent(this,BModeAct.class));
        Log.e("taskId","D---"+getTaskId());
    }
}