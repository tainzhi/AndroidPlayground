package com.tainzhi.sample.api.mvp;

import android.os.Bundle;
import android.view.View;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-23 16:18
 * @description:
 **/

public interface IView<P> {
	P newP();
	
	int getLayoutId();
	
	int getOptionsMenuId();
	
	void bindUI(View rootView);
	
	void initData(Bundle savedInstanceState);
	
}
