package com.tainzhi.sample.api.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-26 08:33
 * @description:
 **/

public class Runnable2 implements Runnable {
	
	public Looper mylooper2;
	public Handler handler2;
	
	@Override
	public void run() {
		Looper.prepare();
		handler2 = new Handler() {
			@Override
			public void handleMessage(@NonNull Message msg) {
				super.handleMessage(msg);
				if (msg.what == Constants.MAIN_2_SUB2) {
					System.out.println("I am from main thread");
					System.out.println("thread=" + Thread.currentThread().getName());
				}
			}
		};
		mylooper2 = Looper.myLooper();
		Looper.loop();
	}
	
}
