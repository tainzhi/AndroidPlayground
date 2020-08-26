package com.tainzhi.sample.api.recyclerview

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R
import com.tainzhi.sample.api.adapter.BasicVertialAdapter
import java.util.*

class RecyclerViewGridLayoutActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    private lateinit var recyclerView: RecyclerView
    private lateinit var toTop: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_grid_layout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toTop = findViewById(R.id.iv_to_top)
        toTop.setOnClickListener(View.OnClickListener { view: View? ->
            recyclerView
                    .scrollToPosition(0)
        })
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.addOnScrollListener(MyScrollListener())
        val layoutManager = GridLayoutManager(this, 2,
                GridLayoutManager.VERTICAL, false)
        recyclerView.setLayoutManager(layoutManager)
        val datas: MutableList<String> = ArrayList()
        for (i in 1..3000) {
            datas.add(i.toString())
        }
        val adapter = BasicVertialAdapter(false)
        adapter.addData(datas)
        recyclerView.setAdapter(adapter)
        recyclerView.setRecyclerListener { holder ->
            Log.d(TAG, "recycle position=${holder
                    .layoutPosition}")
        }
    }

    private inner class MyScrollListener : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val manager = recyclerView.layoutManager as LinearLayoutManager?
            val first = manager!!.findFirstVisibleItemPosition()
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                toTop.visibility = View.VISIBLE
            } else {
                toTop
            }
            Log.d(TAG, "recyclerview height=${recyclerView.height}")
        }
    }
}