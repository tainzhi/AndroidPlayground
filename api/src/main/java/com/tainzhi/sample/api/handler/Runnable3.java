package com.tainzhi.sample.api.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-26 08:39
 * @description:
 **/

public class Runnable3 implements Runnable {
	
	public Handler handler3;
	
	public Looper myLooper3;
	
	@Override
	public void run() {
		Looper.prepare();
		handler3 = new Handler() {
			@Override
			public void handleMessage(@NonNull Message msg) {
				super.handleMessage(msg);
				if (msg.what == Constants.SUB_2_SUB) {
					System.out.println("I am from other thread");
					System.out.println("thread=" + Thread.currentThread().getName());
				}
			}
		};
		
		myLooper3 = Looper.myLooper();
		Looper.loop();
	}
	
}
