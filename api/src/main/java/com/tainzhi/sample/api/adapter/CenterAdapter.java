package com.tainzhi.sample.api.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tainzhi.sample.api.R;

import java.util.List;

/**
 * Created by muqing on 2019/8/28.
 * Email: qfq61@qq.com
 */
public class CenterAdapter extends RecyclerView.Adapter<CenterAdapter.MyViewHolder> {
	
	private List<Integer> mList;
	private int mRecyclerViewWidth;
	
	public CenterAdapter(List<Integer> data, int recyclerViewWidth) {
		mList = data;
		mRecyclerViewWidth = recyclerViewWidth;
	}
	
	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				            .inflate(
						            R.layout.item_basic_horizontal_view, parent, false);
		return new MyViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		final ViewGroup.MarginLayoutParams p =
				(ViewGroup.MarginLayoutParams) holder.clRoot.getLayoutParams();
		int margin = (mRecyclerViewWidth - p.width) / 2;
		if (position == 0) {
			p.leftMargin = margin;
			p.rightMargin = 0;
		} else if (position == (mList.size() - 1)) {
			p.leftMargin = 0;
			p.rightMargin = margin;
		} else {
			p.leftMargin = 0;
			p.rightMargin = 0;
		}
		holder.tvId.setText(String.valueOf(mList.get(position)));
		holder.clRoot.setLayoutParams(p);
	}
	
	@Override
	public int getItemCount() {
		return mList.size();
	}
	
	public static class MyViewHolder extends RecyclerView.ViewHolder {
		ConstraintLayout clRoot;
		TextView tvId;
		
		public MyViewHolder(View view) {
			super(view);
			clRoot = view.findViewById(R.id.cl_root);
			tvId = view.findViewById(R.id.tv_basic_id);
		}
	}
}
