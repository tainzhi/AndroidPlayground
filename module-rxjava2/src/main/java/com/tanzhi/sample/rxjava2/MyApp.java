package com.tanzhi.sample.rxjava2;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tanzhi.sample.rxjava2.rxbus.RxBus;
import com.tanzhi.sample.rxjava2.rxbus.RxEvent;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-24 12:41
 * @description:
 **/

public class MyApp extends Application {
	
	private RxBus bus;
	
	@Override
	public void onCreate() {
		super.onCreate();
		bus = new RxBus();
		
		Logger.addLogAdapter(new AndroidLogAdapter());
	}
	
	public RxBus bus() {
		return bus;
	}
	
	public void sendAutoEvent() {
		Observable.timer(2, TimeUnit.SECONDS)
				.subscribe(new Consumer<Long>() {
					@Override
					public void accept(Long aLong) throws Exception {
						bus.send(new RxEvent.AutoEvent());
					
					}
				});
	}
}
