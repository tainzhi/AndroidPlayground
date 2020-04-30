package com.tainzhi.sample.api.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tainzhi.sample.api.R;

/**
 * Created by muqing on 2019-10-08.
 * Email: qfq61@qq.com
 */
public class BasicHorizontalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
	public BasicHorizontalAdapter() {
		super(R.layout.item_basic_list);
	}
	
	@Override
	protected void convert(@NonNull BaseViewHolder helper, String item) {
		helper.setText(R.id.activity_name, item);
	}
}
