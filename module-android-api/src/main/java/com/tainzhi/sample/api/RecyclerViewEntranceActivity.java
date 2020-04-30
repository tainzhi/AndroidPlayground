package com.tainzhi.sample.api;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tainzhi.sample.api.adapter.BasicHorizontalAdapter;
import com.tainzhi.sample.api.widget.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RecyclerViewEntranceActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler_view_entrance);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		FloatingActionButton fab = findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});
		
		RecyclerView recyclerView = findViewById(R.id.recycler_view);
		recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayout.VERTICAL,
				20));
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
				false));
		BasicHorizontalAdapter adapter = new BasicHorizontalAdapter();
		LinkedHashMap<String, Class<?>> datas = new LinkedHashMap<>();
		datas.put("horizontal/middle", RecyclerViewActivity.class);
		datas.put("GridLayoutManager", RecyclerViewGridLayoutActivity.class);
		datas.put("StaggeredGridLayoutManager",
				RecyclerViewStaggeredGridLayoutActivity.class);
		datas.put("Search Filter/Swpie delete", RecyclerViewFilterableActivity.class);
		List<String> keys = new ArrayList<>(datas.keySet());
		adapter.addData(keys);
		recyclerView.setAdapter(adapter);
		adapter.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
				startActivity(new Intent().setClass(RecyclerViewEntranceActivity.this,
						datas.get(keys.get(position))));
			}
		});

	}
	
}
