package com.tainzhi.sample.api;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.tainzhi.sample.api.adapter.BasicAdapter;
import com.tainzhi.sample.api.adapter.CenterAdapter;
import com.tainzhi.sample.api.adapter.CenterHighlightAdapter;
import com.tainzhi.sample.api.adapter.QuickAdapter;
import com.tainzhi.sample.api.widget.HorizontalSpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
	
	private RecyclerView rvBasic;
	private RecyclerView rvQuick;
	private RecyclerView rvCenter;
	private RecyclerView rvCenterHighlight;
	private List<Integer> mList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recycler_view);
		Toolbar toolbar = findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		
		initView();
	}
	
	public void initView() {
		mList = new ArrayList<>();
		for (int i = 0; i < 20; i++) { mList.add(i); }
		
		initBasicRecyclerView();
		initQuickRecyclerView();
		// 首尾item居中显示
		initCenterRecyclerView();
		initCenterHighlightRecyclerView();
	}
	
	private void initBasicRecyclerView() {
		BasicAdapter basicAdapter = new BasicAdapter(mList);
		basicAdapter.setOnItemClickListener(new BasicAdapter.OnItemClickListener() {
			@Override
			public void onClick(View view, int position) {
				Toast.makeText(RecyclerViewActivity.this, "click " + position,
						Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onItemLongClick(View view, int position) {
				Toast.makeText(RecyclerViewActivity.this, "long click " + position,
						Toast.LENGTH_SHORT).show();
			}
		});
		rvBasic = findViewById(R.id.rv_basic);
		rvBasic.setAdapter(basicAdapter);
		rvBasic.setLayoutManager(new LinearLayoutManager(
				this,
				LinearLayoutManager.HORIZONTAL,
				false));
		final PagerSnapHelper linearSnapHelper = new PagerSnapHelper();
		linearSnapHelper.attachToRecyclerView(rvBasic);
		// rvBasic.addItemDecoration(new HorizontalSpaceItemDecoration(
		// 		(int) Util.Dimens.dpToPx(RecyclerViewActivity.this, 10)));
	}
	
	private void initQuickRecyclerView() {
		rvQuick = findViewById(R.id.rv_quick);
		rvQuick.setAdapter(new QuickAdapter(mList));
		rvQuick.setLayoutManager(new LinearLayoutManager(
				this,
				LinearLayoutManager.HORIZONTAL,
				false));
		rvQuick.addItemDecoration(new HorizontalSpaceItemDecoration(
				(int) Util.Dimens.dpToPx(RecyclerViewActivity.this, 10)));
	}
	
	private void initCenterRecyclerView() {
		// 首尾item居中显示
		rvCenter = findViewById(R.id.rv_center);
		ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) rvCenter.getLayoutParams();
		int left = rvCenter.getLeft();
		int right = rvCenter.getRight();
		int recyclerWidth = layoutParams.width;
		if (recyclerWidth == -1) {
			recyclerWidth =
					Util.Display.getScreenWidth(this) - layoutParams.leftMargin - layoutParams.rightMargin;
		}
		rvCenter.setAdapter(new CenterAdapter(mList, recyclerWidth));
		// 添加LayoutManger, 使得横向显示
		rvCenter.setLayoutManager(new LinearLayoutManager(
				this,
				LinearLayoutManager.HORIZONTAL,
				false));
		//添加间隔空白
		rvCenter.addItemDecoration(new HorizontalSpaceItemDecoration(
				(int) Util.Dimens.dpToPx(RecyclerViewActivity.this, 10)));
		// 必须添加SnapHelper
		final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
		linearSnapHelper.attachToRecyclerView(rvCenter);
		rvCenter.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				int childCount = rvCenter.getChildCount();
				int[] location = new int[2];
				for (int i = 0; i < childCount; i++) {
					rvCenter.getLocationOnScreen(location);
					int recyclerViewCenterX = location[0] + rvCenter.getWidth() / 2;
					View v = rvCenter.getChildAt(i);
					v.getLocationOnScreen(location);
					int itemCenterX = location[0] + v.getWidth() / 2;
					//                   ★ 两边的图片缩放比例
					float scale = 0.8f;
					//                     ★某个item中心X坐标距recyclerview中心X坐标的偏移量
					int offX = Math.abs(itemCenterX - recyclerViewCenterX);
					//                    ★ 在一个item的宽度范围内，item从1缩放至scale，那么改变了（1-scale），从下列公式算出随着offX变化，item的变化缩放百分比
					float percent = offX * (1 - scale) / v.getWidth();
					//                   ★  取反哟
					float interpretateScale = 1 - percent;
					//                    这个if不走的话，得到的是多级渐变模式
					if (interpretateScale < scale) {
						interpretateScale = scale;
					}
					v.setScaleX((interpretateScale));
					v.setScaleY((interpretateScale));
				}
			}
			
		});
	}
	
	private void initCenterHighlightRecyclerView() {
		rvCenterHighlight = findViewById(R.id.rv_center_hightlight);
		ViewGroup.LayoutParams layoutParams = rvCenterHighlight.getLayoutParams();
		int recyclerWidth = layoutParams.width;
		if (recyclerWidth == -1) {
			int recyclerLeftMargin = Util.Dimens.dpToPxInt(this,
					getResources().getDimension(R.dimen.base_margin));
			recyclerWidth = Util.Display.getScreenWidth(this) - recyclerLeftMargin * 2;
		}
		CenterHighlightAdapter adapter = new CenterHighlightAdapter(this, mList);
		rvCenterHighlight.setAdapter(adapter);
		rvCenterHighlight.setLayoutManager(new LinearLayoutManager(
				this,
				LinearLayoutManager.HORIZONTAL,
				false));
		rvCenterHighlight.addItemDecoration(new HorizontalSpaceItemDecoration(
				(int) Util.Dimens.dpToPx(RecyclerViewActivity.this, 10)));
		final LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
		linearSnapHelper.attachToRecyclerView(rvCenterHighlight);
//		rvCenterHighlight.addOnScrollListener(new RecyclerView.OnScrollListener() {
//			@Override
//			public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//				super.onScrollStateChanged(recyclerView, newState);
//				if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//					LinearLayoutManager manager =
//							(LinearLayoutManager) rvCenterHighlight.getLayoutManager();
//					int firstVisibleItemPosition = manager.findFirstCompletelyVisibleItemPosition();
//					int lastVisibleItemPosition = manager.findLastCompletelyVisibleItemPosition();
//					int recyclerViewCenterX = manager.getWidth() / 2 + manager.getPaddingLeft();
//					int centerIndex = 0;
//					for (int i = firstVisibleItemPosition; i <= lastVisibleItemPosition; i++) {
//						View view = manager.findViewByPosition(i);
//						int[] location = new int[2];
//						view.getLocationOnScreen(location);
//						int itemViewCenterX = view.getLeft() + view.getWidth() / 2;
//						if (Math.abs(itemViewCenterX - recyclerViewCenterX) < 30) {
//							centerIndex = i;
//							break;
//						}
//					}
//					adapter.setCenterIndex(centerIndex);
//				}
//			}
//		});
	}
}
