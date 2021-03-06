package com.tainzhi.sample.api.touch

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R
import com.tainzhi.sample.api.adapter.BasicHorizontalAdapter
import com.tainzhi.sample.api.adapter.NameClass
import com.tainzhi.sample.api.touch.multi.MultiTouchActivity
import com.tainzhi.sample.api.touch.single.SingleTouchActivity
import com.tainzhi.sample.api.widget.MyDividerItemDecoration

class TouchEntranceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touch_entrance)
        val mToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        setSupportActionBar(mToolbar)
        val recyclerView = findViewById<RecyclerView>(R.id.touchRecyclerView)
        recyclerView.addItemDecoration(
            MyDividerItemDecoration(
                this, LinearLayout.VERTICAL,
                20
            )
        )
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,
            false
        )
        val adapter = BasicHorizontalAdapter()
        val datas = arrayListOf(
            NameClass("Single Touch", SingleTouchActivity::class.java),
            NameClass("Multi Touch", MultiTouchActivity::class.java)
        )
        adapter.setNewInstance(datas)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent().setClass(
                    this@TouchEntranceActivity,
                    datas[position].clazz
                )
            )
        }
    }
}