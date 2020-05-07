package com.tainzhi.sample.api

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.RecyclerViewActivity
import com.tainzhi.sample.api.Util.Dimens
import com.tainzhi.sample.api.adapter.BasicAdapter
import com.tainzhi.sample.api.adapter.CenterAdapter
import com.tainzhi.sample.api.adapter.CenterHighlightAdapter
import com.tainzhi.sample.api.adapter.QuickAdapter
import com.tainzhi.sample.api.widget.HorizontalSpaceItemDecoration
import java.util.*

class RecyclerViewActivity : AppCompatActivity() {
    private var rvBasic: RecyclerView? = null
    private var rvQuick: RecyclerView? = null
    private var rvCenter: RecyclerView? = null
    private var rvCenterHighlight: RecyclerView? = null
    private var mList: MutableList<Int>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
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
            override fun onClick(view: View, position: Int) {
                Toast.makeText(
                    this@RecyclerViewActivity, "click $position",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onItemLongClick(
                view: View,
                position: Int
            ) {
                Toast.makeText(
                    this@RecyclerViewActivity, "long click $position",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
        rvBasic = findViewById(R.id.rv_basic)
        rvBasic.setAdapter(basicAdapter)
        rvBasic.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        rvBasic.addItemDecoration(
            HorizontalSpaceItemDecoration(
                Dimens.dpToPx(this@RecyclerViewActivity, 10f).toInt()
            )
        )
    }

    private fun initQuickRecyclerView() {
        rvQuick = findViewById(R.id.rv_quick)
        rvQuick.setAdapter(QuickAdapter(mList))
        rvQuick.setLayoutManager(
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        rvQuick.addItemDecoration(
            HorizontalSpaceItemDecoration(
                Dimens.dpToPx(this@RecyclerViewActivity, 10f).toInt()
            )
        )
    }

    private fun initCenterRecyclerView() {
        // 首尾item居中显示
        rvCenter = findViewById(R.id.rv_center)
        val layoutParams =
            rvCenter.getLayoutParams() as ConstraintLayout.LayoutParams
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
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        )
        //添加间隔空白
        rvCenter.addItemDecoration(
            HorizontalSpaceItemDecoration(
                Dimens.dpToPx(this@RecyclerViewActivity, 10f).toInt()
            )
        )
        // 必须添加SnapHelper
        val linearSnapHelper = LinearSnapHelper()
        linearSnapHelper.attachToRecyclerView(rvCenter)
        rvCenter.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
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
                    val scale = 0.8f
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
        rvCenterHighlight = findViewById(R.id.rv_center_hightlight)
        val layoutParams = rvCenterHighlight.getLayoutParams()
        var recyclerWidth = layoutParams.width
        if (recyclerWidth == -1) {
            val recyclerLeftMargin = Dimens.dpToPxInt(
                this,
                resources.getDimension(R.dimen.base_margin)
            )
            recyclerWidth =
                Util.Display.getScreenWidth(this) - recyclerLeftMargin * 2
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
        rvCenterHighlight.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val manager =
                        rvCenterHighlight.getLayoutManager() as LinearLayoutManager?
                    val firstVisibleItemPosition =
                        manager!!.findFirstCompletelyVisibleItemPosition()
                    val lastVisibleItemPosition =
                        manager.findLastCompletelyVisibleItemPosition()
                    val recyclerViewCenterX = manager.width / 2 + manager.paddingLeft
                    var centerIndex = 0
                    for (i in firstVisibleItemPosition..lastVisibleItemPosition) {
                        val view = manager.findViewByPosition(i)
                        val location = IntArray(2)
                        view!!.getLocationOnScreen(location)
                        val itemViewCenterX = view.left + view.width / 2
                        if (Math.abs(itemViewCenterX - recyclerViewCenterX) < 30) {
                            centerIndex = i
                            break
                        }
                    }
                    adapter.setCenterIndex(centerIndex)
                }
            }
        })
    }
}