package com.tainzhi.sample.api.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tainzhi.sample.api.R;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-09 08:50
 * @description:
 **/

public class BasicVertialAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
	public boolean isWaterFall = false;
	
	public BasicVertialAdapter(boolean isWaterFall) {
		super(R.layout.item_basic_vertial_view);
		this.isWaterFall = isWaterFall;
	}
	
	@Override
	protected void convert(@NonNull BaseViewHolder helper, String item) {
		// 瀑布流式布局
		if (isWaterFall) {
			View view = helper.itemView;
			ViewGroup.LayoutParams params = view.getLayoutParams();
			int newHeight = getRandomHeight(params.height);
			params.height = newHeight;
			view.setLayoutParams(params);
			helper.setText(R.id.text_view, item);
		} else {
			helper.setText(R.id.text_view, item);
		}
	}
	
	private int getRandomHeight(double oriheight) {
		double height = oriheight + oriheight * Math.random();
		if (height > 1300) {
			height = 300;
		}
		return (int) height;
	}
	
	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
		if (manager instanceof GridLayoutManager) {
			final GridLayoutManager layoutManager = ((GridLayoutManager)manager);
			layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
				@Override
				public int getSpanSize(int position) {
					// 模 9 为 0 的 item, 占位为整个屏幕 2/2
					// 模 9 为非 1 的 item, 占位为整个屏幕的 1/2
					return (position % 9) == 0 ? 2 : 1;
				}
			});
		}
	}
	
}
