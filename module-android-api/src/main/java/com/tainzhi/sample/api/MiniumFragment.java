package com.tainzhi.sample.api;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Created by muqing on 2019-08-03.
 * Email: qfq61@qq.com
 */
public class MiniumFragment extends Fragment {
	private String title;
	
	private TextView tvTitle;
	
	public MiniumFragment(String title) { this.title = title; }
	
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
	                    @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_minium, container, false);
		tvTitle = view.findViewById(R.id.tv_title);
		tvTitle.setText(title);
		return view;
	}
}
