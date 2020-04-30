package com.tainzhi.sample.api;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tainzhi.sample.api.adapter.BasicHorizontalAdapter;
import com.tainzhi.sample.api.handler.HandlerActivity;
import com.tainzhi.sample.api.widget.MyDividerItemDecoration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
	
	Toolbar mToolbar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mToolbar = findViewById(R.id.my_toolbar);
		setSupportActionBar(mToolbar);
		
		LinkedHashMap<String, Class<?>> data = new LinkedHashMap<>();
		data.put("引导页", GuidePageActivity.class);
		data.put("TabLayout", GoogleTabLayoutActivity.class);
		data.put("RecyclerView", RecyclerViewEntranceActivity.class);
		data.put("Handler的用法", HandlerActivity.class);
		List<String> keys = new ArrayList<>(data.keySet());
		
		RecyclerView recyclerView = findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,
				false));
		recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayout.VERTICAL,
				20));
		BasicHorizontalAdapter adapter = new BasicHorizontalAdapter();
		recyclerView.setAdapter(adapter);
		adapter.addData(keys);
		adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
				startActivity(new Intent().setClass(MainActivity.this,
						data.get(keys.get(position))));
			}
		});
	}
}
