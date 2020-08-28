package com.tainzhi.sample.api.recyclerview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.tainzhi.sample.api.R
import com.tainzhi.sample.api.Util
import com.tainzhi.sample.api.Util.Dimens
import com.tainzhi.sample.api.adapter.BasicAdapter
import com.tainzhi.sample.api.adapter.CenterAdapter
import com.tainzhi.sample.api.adapter.CenterHighlightAdapter
import com.tainzhi.sample.api.adapter.QuickAdapter
import com.tainzhi.sample.api.widget.HorizontalSpaceItemDecoration
import com.tainzhi.sample.util.dp

class RecyclerViewActivity : AppCompatActivity() {
    private var mList = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        initView()
    }

    fun initView() {
        mList = ArrayList()
        for (i in 0..19) {
            mList.add(i)
        }
        initBasicRecyclerView()
        initQuickRecyclerView()
        // 首尾item居中显示
        initCenterRecyclerView()
        initCenterHighlightRecyclerView()
    }

    private fun initBasicRecyclerView() {
        val basicAdapter = BasicAdapter(mList)
        basicAdapter.setOnItemClickListener(object : BasicAdapter.OnItemClickListener {
            override fun onClick(view: View?, position: Int) {
                Toast.makeText(
                    this@RecyclerViewActivity, "click $position",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onItemLongClick(view: View?, position: Int) {
                Toast.makeText(
                    this@RecyclerViewActivity, "long click $position",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        val rvBasic = findViewById<RecyclerView>(R.id.rv_basic).apply {
            adapter = basicAdapter
            layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        val linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(rvBasic)
        // rvBasic.addItemDecoration(new HorizontalSpaceItemDecoration(
        // 		(int) Util.Dimens.dpToPx(RecyclerViewActivity.this, 10)));
    }

    private fun initQuickRecyclerView() {
        val rvQuick = findViewById<RecyclerView>(R.id.rv_quick)
        rvQuick.setAdapter(BasicAdapter(mList))
        rvQuick.setLayoutManager(
            // LinearLayoutManager(
            //     this,
            //     LinearLayoutManager.HORIZONTAL,
            //     false
            // )
            CustomLayoutManager(this, Util.Display.getScreenWidth(this), 100.dp().toInt())
        )
        SnapCenter().attachToRecyclerView(rvQuick)
        rvQuick.addItemDecoration(
            HorizontalSpaceItemDecoration(
                Dimens.dpToPx(this@RecyclerViewActivity, 10f).toInt()
            )
        )
    }

    private fun initCenterRecyclerView() {
        // 首尾item居中显示
        val rvCenter = findViewById<RecyclerView>(R.id.rv_center)
        val layoutParams = rvCenter.getLayoutParams() as ConstraintLayout.LayoutParams
        val left = rvCenter.getLeft()
        val right = rvCenter.getRight()
        var recyclerWidth = layoutParams.width
        if (recyclerWidth == -1) {
            recyclerWidth =
                Util.Display.getScreenWidth(this) - layoutParams.leftMargin - layoutParams.rightMargin
        }
        rvCenter.setAdapter(CenterAdapter(mList, recyclerWidth))
        // 添加LayoutManger, 使得横向显示
        rvCenter.setLayoutManager(
            CustomLayoutManager(this, Util.Display.getScreenWidth(this), 90.dp().toInt())
        )
        //添加间隔空白
        rvCenter.addItemDecoration(
            HorizontalSpaceItemDecoration(
                Dimens.dpToPx(this@RecyclerViewActivity, 10f).toInt()
            )
        )
        // 必须添加SnapHelper
        SnapCenter().attachToRecyclerView(rvCenter)
        rvCenter.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val childCount = rvCenter.getChildCount()
                val location = IntArray(2)
                for (i in 0 until childCount) {
                    rvCenter.getLocationOnScreen(location)
                    val recyclerViewCenterX = location[0] + rvCenter.getWidth() / 2
                    val v = rvCenter.getChildAt(i)
                    v.getLocationOnScreen(location)
                    val itemCenterX = location[0] + v.width / 2
                    //                   ★ 两边的图片缩放比例
                    val scale = 0.9f
                    //                     ★某个item中心X坐标距recyclerview中心X坐标的偏移量
                    val offX = Math.abs(itemCenterX - recyclerViewCenterX)
                    //                    ★ 在一个item的宽度范围内，item从1缩放至scale，那么改变了（1-scale），从下列公式算出随着offX变化，item的变化缩放百分比
                    val percent = offX * (1 - scale) / v.width
                    //                   ★  取反哟
                    var interpretateScale = 1 - percent
                    //                    这个if不走的话，得到的是多级渐变模式
                    if (interpretateScale < scale) {
                        interpretateScale = scale
                    }
                    v.scaleX = interpretateScale
                    v.scaleY = interpretateScale
                }
            }
        })
    }

    private fun initCenterHighlightRecyclerView() {
        val rvCenterHighlight = findViewById<RecyclerView>(R.id.rv_center_hightlight)
        val layoutParams = rvCenterHighlight.getLayoutParams()
        var recyclerWidth = layoutParams.width
        if (recyclerWidth == -1) {
            val recyclerLeftMargin = Dimens.dpToPxInt(
                this,
                resources.getDimension(R.dimen.base_margin)
            )
            recyclerWidth = Util.Display.getScreenWidth(this) - recyclerLeftMargin * 2
        }
        val adapter = CenterHighlightAdapter(this, mList)
        rvCenterHighlight.setAdapter(adapter)
        rvCenterHighlight.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        rvCenterHighlight.addItemDecoration(
            HorizontalSpaceItemDecoration(
                Dimens.dpToPx(this@RecyclerViewActivity, 10f).toInt()
            )
        )
        val linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(rvCenterHighlight)
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