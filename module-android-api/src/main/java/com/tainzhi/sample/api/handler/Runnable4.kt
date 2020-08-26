package com.tainzhi.sample.api.handler

import android.os.Handler
import android.os.Message

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-26 08:43
 * @description:
 */
class Runnable4(var handler: Handler?) : Runnable {
    override fun run() {
        val msg = Message()
        msg.what = Constants.SUB_2_SUB
        handler!!.sendMessage(msg)
    }
}