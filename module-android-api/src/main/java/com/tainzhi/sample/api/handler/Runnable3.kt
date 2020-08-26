package com.tainzhi.sample.api.handler

import android.os.Handler
import android.os.Looper
import android.os.Message

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-26 08:39
 * @description:
 */
class Runnable3 : Runnable {
    var handler3: Handler? = null
    var myLooper3: Looper? = null
    override fun run() {
        Looper.prepare()
        handler3 = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == Constants.SUB_2_SUB) {
                    println("I am from other thread")
                    println("thread=" + Thread.currentThread().name)
                }
            }
        }
        myLooper3 = Looper.myLooper()
        Looper.loop()
    }
}