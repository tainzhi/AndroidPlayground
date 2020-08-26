package com.tainzhi.sample.api.handler

import android.os.Handler
import android.os.Looper
import android.os.Message

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-26 08:33
 * @description:
 */
class Runnable2 : Runnable {
    var mylooper2: Looper? = null
    var handler2: Handler? = null
    override fun run() {
        Looper.prepare()
        handler2 = object : Handler() {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (msg.what == Constants.MAIN_2_SUB2) {
                    println("I am from main thread")
                    println("thread=" + Thread.currentThread().name)
                }
            }
        }
        mylooper2 = Looper.myLooper()
        Looper.loop()
    }
}