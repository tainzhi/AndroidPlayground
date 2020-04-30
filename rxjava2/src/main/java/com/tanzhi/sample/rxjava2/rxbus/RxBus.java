package com.tanzhi.sample.rxjava2.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-24 12:39
 * @description:
 **/

public class RxBus {
	public RxBus() {
	}
	
	private PublishSubject<Object> bus = PublishSubject.create();
	
	public void send(Object o) { bus.onNext(o);}
	
	public Observable<Object> toObservable() { return bus; }
	
	public boolean hasObservers() {
		return bus.hasObservers();
	}
}
