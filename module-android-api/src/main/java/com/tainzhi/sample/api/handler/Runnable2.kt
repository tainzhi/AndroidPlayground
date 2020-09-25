package com.tainzhi.sample.api.handler

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.TextView

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-26 08:33
 * @description:
 */
class Runnable2(val textView: TextView) : Runnable {
    var mylooper2: Looper? = null
    var handler2: Handler? = null
    override fun run() {
        Looper.prepare()
        handler2 = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == Constants.MAIN_2_SUB2) {
                    textView.append("${Thread.currentThread().name}, handleMessage\n")
                }
            }
        }
        mylooper2 = Looper.myLooper()
        Looper.loop()
    }
}