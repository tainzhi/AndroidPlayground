package com.tainzhi.sample.api.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.tainzhi.sample.api.R;

import java.util.List;

/**
 * Created by muqing on 2019-08-15.
 * Email: qfq61@qq.com
 */
public class QuickAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
	public QuickAdapter(List<Integer> data) {
		super(R.layout.item_basic_horizontal_view, data);
	}
	
	@Override
	protected void convert(@NonNull BaseViewHolder helper, Integer item) {
		helper.setText(R.id.tv_basic_id, item.toString());
	}
}
