package com.tainzhi.sample.api.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tainzhi.sample.api.R;

import java.util.List;

/**
 * Created by muqing on 8/31/2019.
 * Email: qfq61@qq.com
 */
public class CenterHighlightAdapter extends RecyclerView.Adapter<CenterHighlightAdapter.MyViewHolder> {
	
	private List<Integer> mList;
	private int mCenterIndex;
	private Context mContext;
	
	public CenterHighlightAdapter(Context context, List<Integer> data) {
		mContext = context;
		mList = data;
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
		holder.tvId.setText(String.valueOf(mList.get(position)));
		if (position == mCenterIndex) {
			holder.imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.red));
		} else {
			holder.imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.grey));
		}
	}


	@Override
	public int getItemCount() {
		return mList.size();
	}
	
	public void setCenterIndex(int newCenterIndex) {
		notifyItemChanged(mCenterIndex);
		notifyItemChanged(newCenterIndex);
		mCenterIndex = newCenterIndex;
	}
	
	public static class MyViewHolder extends RecyclerView.ViewHolder {
		ConstraintLayout clRoot;
		TextView tvId;
		ImageView imageView;
		
		public MyViewHolder(View view) {
			super(view);
			clRoot = view.findViewById(R.id.cl_root);
			tvId = view.findViewById(R.id.tv_basic_id);
			imageView = view.findViewById(R.id.imageView);
		}
	}
}
