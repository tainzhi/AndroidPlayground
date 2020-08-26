package com.tainzhi.sample.api.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tainzhi.sample.api.R;

import java.util.List;
import java.util.Random;

/**
 * Created by muqing on 2019-08-14.
 * Email: qfq61@qq.com
 */
public class BasicAdapter extends RecyclerView.Adapter<BasicAdapter.MyViewHolder> {
	
	private List<Integer> mDataList;
	
	public BasicAdapter(List<Integer> datas) {
		mDataList = datas;
	}
	
	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				            .inflate(
						            R.layout.item_basic_horizontal_view, parent, false);
		return new MyViewHolder(view);
	}
	
	private OnItemClickListener mOnItemClickListener;
	
	@Override
	public int getItemCount() {
		return mDataList.size();
	}
	
	public static class MyViewHolder extends RecyclerView.ViewHolder {
		TextView tvId;
		ImageView imageView;
		
		public MyViewHolder(View view) {
			super(view);
			tvId = view.findViewById(R.id.tv_basic_id);
			imageView = view.findViewById(R.id.imageView);
		}
	}
	
	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		holder.tvId.setText(mDataList.get(position).toString());
		holder.imageView.setBackgroundColor(Color.argb(
				new Random().nextInt(255),
				new Random().nextInt(255),
				new Random().nextInt(255),
				new Random().nextInt(255)));
		if (mOnItemClickListener != null) {
			holder.itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					mOnItemClickListener.onClick(view, position);
				}
			});
			holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View view) {
					mOnItemClickListener.onItemLongClick(view, position);
					return false;
				}
			});
		}
	}
	
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}
	
	public interface OnItemClickListener {
		void onClick(View view, int position);
		
		void onItemLongClick(View view, int position);
	}
}
