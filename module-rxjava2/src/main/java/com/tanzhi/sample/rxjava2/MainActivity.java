package com.tanzhi.sample.rxjava2;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.tanzhi.android.playground.router.RouterPath;
import com.tanzhi.sample.rxjava2.adapter.TypeAdapter;
import com.tanzhi.sample.rxjava2.rxbus.RxbusActivity;
import com.tanzhi.sample.rxjava2.search.SearchActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Route(path = RouterPath.PATH_RXJAVA2)
public class MainActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rxjava2_activity_main);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		
		RecyclerView rv = findViewById(R.id.recycler_view);
		TypeAdapter adapter = new TypeAdapter();
		rv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
		rv.setAdapter(adapter);
		
		LinkedHashMap<String, Class> activitySet = new LinkedHashMap<>();
		activitySet.put("后台更新 UI", BackgroundUpdateUIActivity.class);
//		activitySet.put("rxjava实现简单rxbus", RxbusActivity.class);
		activitySet.put("Search", SearchActivity.class);
		activitySet.put("Retrofit + 分页加载", PaginationActivity.class);
		activitySet.put("温度数据计算平均值", BufferActivity.class);
		activitySet.put("基于错误类型的重试请求", RetryActivity.class);
		activitySet.put("基于combineLatest实现的输入表单验证", CombineLastActivity.class);
		List<String> dataList = new ArrayList<>(activitySet.keySet());
		
		adapter.addData(dataList);
		adapter.setOnItemClickListener((_adapter, v, position) -> {
			startActivity(new Intent(MainActivity.this, activitySet.get(dataList.get(position))));
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
