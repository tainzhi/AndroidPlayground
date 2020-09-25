package com.tainzhi.sample.api.handler

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.tainzhi.sample.api.R
import kotlinx.android.synthetic.main.content_handler.mainToSubBtn
import kotlinx.android.synthetic.main.content_handler.runStateDetailTv
import kotlinx.android.synthetic.main.content_handler.subToMainBtn
import kotlinx.android.synthetic.main.content_handler.subToSubBtn

class HandlerActivity : AppCompatActivity() {
    private val mMainHandler: Handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what == Constants.SUB1_2_MAIN) {
                runStateDetailTv.append("${Thread.currentThread().name}, handleMessage\n")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handler)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        runStateDetailTv.movementMethod = ScrollingMovementMethod.getInstance()

        subToMainBtn.setOnClickListener {
            // 子线程到主线程
            testSub2Main()
        }

        mainToSubBtn.setOnClickListener {
            // 主线程到子线程
            testMain2Sub()
        }

        subToSubBtn.setOnClickListener {
            // 子线程到子线程
            testSub2Sub()
        }
    }

    private fun testSub2Main() {
        Thread(Runnable1(mMainHandler, runStateDetailTv)).start()
    }

    private fun testMain2Sub() {
        val runnable2 = Runnable2(runStateDetailTv)
        runStateDetailTv.append("${Thread.currentThread().name}, sendMessage\n")
        Thread(runnable2).start()
        val msg = Message()
        msg.what = Constants.MAIN_2_SUB2
        while (true) {
            if (runnable2.mylooper2 != null) {
                runnable2.handler2?.sendMessage(msg)
                return
            }
        }
    }

    private fun testSub2Sub() {
        val runnable3 = Runnable3(runStateDetailTv)
        Thread(runnable3).start()
        while (true) {
            if (runnable3.myLooper3 != null) {
                val runnable4 = Runnable4(runnable3.handler3, runStateDetailTv)
                Thread(runnable4).start()
                return
            }
        }
    }
}