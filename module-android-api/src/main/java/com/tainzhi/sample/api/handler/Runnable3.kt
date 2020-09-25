package com.tainzhi.sample.api.handler

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.TextView

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-26 08:39
 * @description:
 */
class Runnable3(val textView: TextView) : Runnable {
    var handler3: Handler? = null
    var myLooper3: Looper? = null
    override fun run() {
        Looper.prepare()
        handler3 = object : Handler() {
            override fun handleMessage(msg: Message) {
                if (msg.what == Constants.SUB_2_SUB) {
                    textView.append("${Thread.currentThread().name}, handleMessage\n")
                }
            }
        }
        myLooper3 = Looper.myLooper()
        Looper.loop()
    }
}