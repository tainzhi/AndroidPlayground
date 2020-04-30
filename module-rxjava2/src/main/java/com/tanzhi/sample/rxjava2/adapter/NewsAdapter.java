package com.tanzhi.sample.rxjava2.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tanzhi.sample.rxjava2.R;
import com.tanzhi.sample.rxjava2.bean.NewsResultBean;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-30 20:38
 * @description:
 **/

public class NewsAdapter extends BaseQuickAdapter<NewsResultBean, BaseViewHolder> {
	public NewsAdapter() {
		super(R.layout.item_type);
	}
	
	@Override
	protected void convert(@NonNull BaseViewHolder helper, NewsResultBean item) {
		helper.setText(R.id.tv_type, item.getType());
		helper.setText(R.id.tv_content, item.getDesc());
	}
}
