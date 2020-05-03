package com.tainzhi.sample.api;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.tainzhi.sample.api.adapter.SimpleFragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class GoogleTabLayoutActivity extends AppCompatActivity {
	
	private TabLayout mTabLayout;
	private ViewPager mViewPager;
	private List<Fragment> mFragmentList;
	private List<String> mTitleList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_tab_layout);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		toolbar.setTitle(getClass().getSimpleName());
		init();
		
	}
	
	private void init() {
		mTabLayout = findViewById(R.id.tablayout);
		mViewPager = findViewById(R.id.view_pager);
		String[] titleString = {"TAB1", "TAB2", "TAB3", "TAB4", "TAB5"};
		mFragmentList = new ArrayList<>();
		mTitleList = new ArrayList<>();
		for (int i = 0; i< 5; i++) {
			mTitleList.add(titleString[i]);
			mFragmentList.add(new MiniumFragment(titleString[i]));
		}
		mViewPager.setOffscreenPageLimit(2); //pre load
		mViewPager.setAdapter(new SimpleFragmentPagerAdapter(
				getSupportFragmentManager(),
				mTitleList,
				mFragmentList));
		mTabLayout.setupWithViewPager(mViewPager); //必须添加
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_tablayout, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.action_tab_with_icon) {
			startActivity(new Intent().setClass(GoogleTabLayoutActivity.this,
					GoogleTabLayoutAdvanceActivity.class));
		}
		return true;
	}
}
