package com.tainzhi.sample.api.mvp;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-23 16:18
 * @description:
 **/

public interface IPresent<V> {
	void attachV(V view);
	
	void detachV();
	
	boolean hasV();
}
