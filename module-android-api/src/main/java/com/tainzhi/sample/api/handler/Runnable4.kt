package com.tainzhi.sample.api.handler

import android.os.Handler
import android.os.Message
import android.widget.TextView

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-26 08:43
 * @description:
 */
class Runnable4(var handler: Handler?, val textView: TextView) : Runnable {
    override fun run() {
        textView.append("${Thread.currentThread().name}: send message\n")
        val msg = Message()
        msg.what = Constants.SUB_2_SUB
        handler!!.sendMessage(msg)
    }
}