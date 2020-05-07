package com.tainzhi.sample.api.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tainzhi.sample.api.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-13 19:42
 * @description:
 **/

public class FilterableAdapter extends RecyclerView.Adapter<FilterableAdapter.MyViewHolder> implements Filterable {
	private Context mContext;
	private List<String> mList = new ArrayList<>();
	private List<String> mOriginalList = new ArrayList<>();
	
	public FilterableAdapter(Context context, List<String> list) {
		this.mContext = context;
		mList = list;
		mOriginalList = list;
	}
	
	@NonNull
	@Override
	public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basic_vertial_view, parent,
				false);
		return new MyViewHolder(view);
	}
	
	@Override
	public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
		holder.textView.setText(mList.get(position));
	}
	
	@Override
	public int getItemCount() {
		return mList.size();
	}
	
	public void removeItem(int position) {
		mList.remove(position);
		mOriginalList.remove(position);
		notifyItemRemoved(position);
	}
	
	public void restoreItem(String item, int position) {
		mList.add(position, item);
		mOriginalList.add(position, item);
		notifyItemInserted(position);
	}
	
	
	@Override
	public Filter getFilter() {
		return new Filter() {
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				String constraintString = constraint.toString();
				List<String> filteredList = new ArrayList<>();
				if (constraintString.isEmpty()) {
					filteredList = mOriginalList;
				} else {
					for (String s : mOriginalList) {
						if (s.toLowerCase().contains(constraintString)) {
							filteredList.add(s);
						}
					}
				}
				FilterResults filterResults = new FilterResults();
				filterResults.values = filteredList;
				return filterResults;
			}
			
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				mList = (ArrayList<String>) results.values;
				notifyDataSetChanged();
			}
		};
	}
	
	public class MyViewHolder extends RecyclerView.ViewHolder {
		public TextView textView;
		public View foreground, background;
		
		public MyViewHolder(View view) {
			super(view);
			textView = view.findViewById(R.id.text_view);
			foreground = view.findViewById(R.id.cl_foreground);
			background = view.findViewById(R.id.cl_background);
		}
	}
}
