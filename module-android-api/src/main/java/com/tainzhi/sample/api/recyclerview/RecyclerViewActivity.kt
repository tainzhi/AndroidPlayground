package com.tainzhi.sample.api.recyclerview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R
import com.tainzhi.sample.api.adapter.BasicAdapter
import com.tainzhi.sample.api.adapter.CenterHighlightAdapter
import com.tainzhi.sample.api.widget.CenterFirstLastItemDecoration
import com.tainzhi.sample.util.dpToPx

class RecyclerViewActivity : AppCompatActivity() {
    private val mList = MutableList(20) { index ->
        index
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        initView()
    }

    fun initView() {
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
    }

    private fun initQuickRecyclerView() {
        val itemOffset = this.dpToPx<Int>(10) // item之间的间距
        val rvQuick = findViewById<RecyclerView>(R.id.rv_quick).apply {
            adapter = BasicAdapter(mList)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        ItemDecorationSnapHelper(itemOffset, 1F).attachToRecyclerView(rvQuick)
        rvQuick.addItemDecoration( CenterFirstLastItemDecoration(itemOffset) )
    }

    private fun initCenterRecyclerView() {
        val itemOffset = this.dpToPx<Int>(10)
        val rvCenter = findViewById<RecyclerView>(R.id.rv_center).apply {
            adapter = BasicAdapter(mList)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }
        rvCenter.addItemDecoration(CenterFirstLastItemDecoration(itemOffset))
        ItemDecorationSnapHelper(itemOffset, 0.2f).attachToRecyclerView(rvCenter)
    }

    private fun initCenterHighlightRecyclerView() {
        val itemOffset = this.dpToPx<Int>(10)
        val centerAdapter = CenterHighlightAdapter(this, mList)
        val rvCenterHighlight = findViewById<RecyclerView>(R.id.rv_center_hightlight).apply {
            adapter = centerAdapter
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        }
        rvCenterHighlight.addItemDecoration(CenterFirstLastItemDecoration(itemOffset))
        ItemDecorationSnapHelper(itemOffset, 1f) { centerIndex ->
            centerAdapter.setCenterIndex(centerIndex)
        }.attachToRecyclerView(rvCenterHighlight)
    }
}