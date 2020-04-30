package com.tainzhi.sample.api;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tainzhi.sample.api.adapter.BasicVertialAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewStaggeredGridLayoutActivity extends AppCompatActivity implements
		RecyclerView.RecyclerListener {
	
	private RecyclerView recyclerView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler_view_staggered_grid_layout);
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
		
		recyclerView = findViewById(R.id.recycler_view);
		recyclerView.setRecyclerListener(this);
		
		StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		List<String> datas = new ArrayList<>();
		for (int i = 0; i < 30; i++) { datas.add(String.valueOf(i)); }
		BasicVertialAdapter adapter = new BasicVertialAdapter(true);
		adapter.addData(datas);
		recyclerView.setAdapter(adapter);
		
	}
	
	@Override
	public void onViewRecycled(@NonNull RecyclerView.ViewHolder holder) {
		Log.d("qfq", "position=" + holder.getAdapterPosition() + ", height=" + recyclerView.getHeight());
	}
}
