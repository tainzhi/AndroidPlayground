package com.tainzhi.sample.api;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.tainzhi.sample.api.adapter.FilterableAdapter;
import com.tainzhi.sample.api.widget.MyDividerItemDecoration;
import com.tainzhi.sample.api.widget.RecyclerItemTouchHelper;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewFilterableActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {
	
	private CoordinatorLayout mCoordinatorLayoutRoot;
	private RecyclerView mRecyclerView;
	private FilterableAdapter mAdapter;
	private SearchView mSearchView;
	private List<String> mList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler_view_filterable);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		mList = new ArrayList<>();
		for (int i = 0; i < 30; i++) { mList.add(String.valueOf(i)); }
		
		mCoordinatorLayoutRoot = findViewById(R.id.cl_root);
		
		mRecyclerView = findViewById(R.id.recycler_view);
		mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
		mRecyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayout.VERTICAL,
				20));
		mAdapter = new FilterableAdapter(this, mList);
		mRecyclerView.setAdapter(mAdapter);
		
		// adding item touch helper
		// only ItemTouchHelper.LEFT added to detect Right to Left swipe
		// if you want both Right -> Left and Left -> Right
		// add pass ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT as param
		ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,
				ItemTouchHelper.LEFT, this);
		new ItemTouchHelper(simpleCallback).attachToRecyclerView(mRecyclerView);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_search, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
		mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		mSearchView.setMaxWidth(Integer.MAX_VALUE);
		mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
			@Override
			public boolean onQueryTextSubmit(String query) {
				mAdapter.getFilter().filter(query);
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				mAdapter.getFilter().filter(newText);
				return false;
			}
		});
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_search:
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
		if (viewHolder instanceof FilterableAdapter.MyViewHolder) {
			String text = mList.get(viewHolder.getAdapterPosition());
			int deletedIndex = viewHolder.getAdapterPosition();
			String deletedItem = mList.get(deletedIndex);
			
			mAdapter.removeItem(deletedIndex);
			
			Snackbar snackbar = Snackbar.make(mCoordinatorLayoutRoot, text + " removed from list!",
					Snackbar.LENGTH_LONG);
			snackbar.setAction("UNDO", (view) -> {
				mAdapter.restoreItem(deletedItem, deletedIndex);
			});
			snackbar.setActionTextColor(Color.YELLOW);
			snackbar.show();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (!mSearchView.isIconified()) {
			mSearchView.setIconified(true);
			return;
		}
		super.onBackPressed();
	}
	
	
}
