package com.jsycn.pj_project.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.jsycn.pj_project.R

class LeakCanaryActivity2 : AppCompatActivity() {
    private var tv : TextView? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_canary2)
        tv = findViewById(R.id.tv_setMe)
        tv!!.setOnClickListener {
            getThread().setText(tv)
        }
    }

    override fun onDestroy() {
        getThread().removeT()
        super.onDestroy()
    }

}