package com.tanzhi.sample.rxjava2.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tanzhi.sample.rxjava2.R;

import java.util.List;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-30 08:43
 * @description:
 **/

public class TypeAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
	
	public TypeAdapter() {
		super(R.layout.item_type);
	}
	@Override
	protected void convert(@NonNull BaseViewHolder helper, String item) {
		helper.setText(R.id.tv_content, item);
	}
}
