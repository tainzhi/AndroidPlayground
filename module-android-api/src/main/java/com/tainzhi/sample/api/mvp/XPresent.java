package com.tainzhi.sample.api.mvp;

import java.lang.ref.WeakReference;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-23 16:22
 * @description:
 **/

public class XPresent<V extends IView> implements IPresent<V> {
	
	protected WeakReference<V> v;
	
	@Override
	public void attachV(V view) {
		v = new WeakReference<>(view);
	}
	
	@Override
	public void detachV() {
		if (v.get() != null) {
			v.clear();
		}
		v = null;
	}
	
	@Override
	public boolean hasV() {
		return v != null && v.get() != null;
	}
	
	protected V getV() {
		if (v == null || v.get() == null) {
			throw new IllegalStateException("v can not be null");
		}
		return v.get();
	}
}
