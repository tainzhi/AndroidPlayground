package com.tainzhi.sample.api.handler

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.tainzhi.sample.api.R

class HandlerActivity : AppCompatActivity() {
    var mTvDescription: TextView? = null
    private val mMainHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what == Constants.SUB1_2_MAIN) {
                println("I am from Sub thread")
                println("thread=" + Thread.currentThread().name)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        mTvDescription = findViewById(R.id.tv_description)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        // 子线程到主线程
        testSub2Main()
        // 主线程到子线程
        testMain2Sub()
        // 子线程到子线程
        testSub2Sub()
    }

    private fun testSub2Main() {
        Thread(Runnable1(mMainHandler)).start()
    }

    private fun testMain2Sub() {
        val runnable2 = Runnable2()
        Thread(runnable2).start()
        val msg = Message()
        msg.what = Constants.MAIN_2_SUB2
        while (true) {
            if (runnable2.mylooper2 != null) {
                runnable2.handler2!!.sendMessage(msg)
                return
            }
        }
    }

    private fun testSub2Sub() {
        val runnable3 = Runnable3()
        Thread(runnable3).start()
        while (true) {
            if (runnable3.myLooper3 != null) {
                val runnable4 = Runnable4(runnable3.handler3)
                Thread(runnable4).start()
                return
            }
        }
    }
}