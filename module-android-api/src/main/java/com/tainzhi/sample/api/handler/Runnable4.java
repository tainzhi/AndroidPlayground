package com.tainzhi.sample.api.handler;


import android.os.Handler;
import android.os.Message;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-26 08:43
 * @description:
 **/

public class Runnable4 implements Runnable {
	Handler handler;
	
	public Runnable4(Handler handler) {
		this.handler = handler;
	}
	
	@Override
	public void run() {
		Message msg = new Message();
		msg.what = Constants.SUB_2_SUB;
		handler.sendMessage(msg);
	}
}
