package com.tainzhi.sample.api.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tainzhi.sample.api.R;

import org.jetbrains.annotations.NotNull;

/**
 * Created by muqing on 2019-10-08.
 * Email: qfq61@qq.com
 */
public class BasicHorizontalAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
	public BasicHorizontalAdapter() {
		super(R.layout.item_basic_list);
	}

	@Override
	protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
		baseViewHolder.setText(R.id.activity_name, s);
	}
}
