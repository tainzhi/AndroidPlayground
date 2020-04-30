package com.tainzhi.sample.api.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.Unbinder;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-23 16:52
 * @description:
 **/

public abstract class XActivity<P extends IPresent> extends AppCompatActivity implements IView<P> {
	
	protected Activity context;
	private P p;
	private Unbinder unbinder;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		context = this;
		
		getP();
		
		if (getLayoutId() > 0) {
			setContentView(getLayoutId());
			bindUI(null);
		}
		initData(savedInstanceState);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (getP() != null) {
			getP().detachV();
		}
		
		p = null;
		
		unbinder.unbind();
		unbinder = null;
	}
	
	protected P getP() {
		if (p == null) {
			p = newP();
		}
		if (p != null) {
			if (!p.hasV()) {
				p.attachV(this);
			}
		}
		return p;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (getOptionsMenuId() > 0) {
			getMenuInflater().inflate(getOptionsMenuId(), menu);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public int getOptionsMenuId() {
		return 0;
	}
	
	@Override
	public void bindUI(View rootView) {
		unbinder = KnifeUtil.bind(this);
	}
}
