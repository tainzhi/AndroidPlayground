package com.tainzhi.sample.api.handler;

import android.os.Handler;
import android.os.Message;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-26 08:32
 * @description:
 **/

public class Runnable1 implements Runnable {
	Handler handler;
	
	public Runnable1(Handler handler) {
		this.handler = handler;
	}
	
	@Override
	public void run() {
		Message msg = new Message();
		msg.what = Constants.SUB1_2_MAIN;
		handler.sendMessage(msg);
	}
}
