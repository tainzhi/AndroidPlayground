package com.tainzhi.sample.api.mvp;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-23 19:10
 * @description:
 **/

public class KnifeUtil {
	public static Unbinder bind(Object target) {
		if (target instanceof Activity) {
			return ButterKnife.bind((Activity) target);
		} else if (target instanceof Dialog) {
			return ButterKnife.bind((Dialog) target);
		} else if (target instanceof View) {
			return ButterKnife.bind((View) target);
		}
		return Unbinder.EMPTY;
	}
	
	public static Unbinder bind(Object target, Object source) {
		if (source instanceof Activity) {
			return ButterKnife.bind(target, (Activity) source);
		} else if (source instanceof Dialog) {
			return ButterKnife.bind(target, (Dialog) source);
		} else if (source instanceof View) {
			return ButterKnife.bind(target, (View) source);
		}
		return Unbinder.EMPTY;
	}
	
	public static void unbind(Unbinder unbinder) {
		if (unbinder != Unbinder.EMPTY) {
			unbinder.unbind();
		}
	}
}
