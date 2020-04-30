package com.tainzhi.sample.api.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.Unbinder;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-23 18:31
 * @description:
 **/

public abstract class XFragment<P extends IPresent> extends Fragment implements IView<P> {
	
	protected View rootView;
	protected LayoutInflater layoutInflater;
	Activity context;
	private P p;
	private Unbinder unbinder;
	
	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		if (context instanceof Activity) {
			this.context = (Activity) context;
		}
	}
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		this.layoutInflater = inflater;
		if (rootView == null && getLayoutId() > 0) {
			rootView = inflater.inflate(getLayoutId(), null);
			bindUI(rootView);
		} else {
			ViewGroup viewGroup = (ViewGroup) rootView.getParent();
			if (viewGroup != null) {
				viewGroup.removeView(rootView);
			}
		}
		return rootView;
	}
	
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getP();
		
		initData(savedInstanceState);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (getP() != null) {
			getP().detachV();
		}
		p = null;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		context = null;
	}
	
	@Override
	public void bindUI(View rootView) {
		unbinder = KnifeUtil.bind(this, rootView);
	}
	
	protected P getP() {
		if (p == null) {
			p = newP();
		}
		if (p != null) {
			if (p.hasV()) {
				p.attachV(this);
			}
		}
		return p;
	}
}
