package com.jsycn.pj_project.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.jsycn.pj_project.MApplication
import com.jsycn.pj_project.R
import kotlinx.android.synthetic.main.activity_leak_canary.*

var myThread: LeakCanaryActivity.MyThread? = null
fun getThread(t: TextView? = null) : LeakCanaryActivity.MyThread{
    if (myThread == null) {
        myThread = LeakCanaryActivity.MyThread(t)
    }
    return myThread!!
}

class LeakCanaryActivity : AppCompatActivity() {
    private var tv: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leak_canary)
        tv = findViewById(R.id.tv_leak)
        tv?.setOnClickListener {
            getThread(tv).start()
            startActivity(Intent(this,LeakCanaryActivity2::class.java))
        }
    }


    override fun onDestroy() {
        tv = null
        super.onDestroy()
    }


    class MyThread constructor(@Volatile var t: TextView?) : Thread() {
        override fun run() {
            super.run()
            while (true) {
                t?.text = "1111"
                Thread.sleep(3000)
            }
        }

        public fun setText(t: TextView?) {
            this.t = t
        }

        public fun removeT(){
            t =null
        }
    }
}