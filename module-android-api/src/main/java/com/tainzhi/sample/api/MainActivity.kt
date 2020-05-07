package com.tainzhi.sample.api

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.tainzhi.sample.api.adapter.BasicHorizontalAdapter
import com.tainzhi.sample.api.adapter.NameClass
import com.tainzhi.sample.api.handler.HandlerActivity
import com.tainzhi.sample.api.widget.MyDividerItemDecoration

@Route(path = "/api/main")
class MainActivity : AppCompatActivity() {
    var mToolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mToolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(mToolbar)
        val data = arrayListOf(
            NameClass("引导页", GuidePageActivity::class.java),
            NameClass("TabLayout", GoogleTabLayoutActivity::class.java),
            NameClass("RecyclerView", RecyclerViewEntranceActivity::class.java),
            NameClass("Handler", HandlerActivity::class.java)
        )

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,
            false
        )
        recyclerView.addItemDecoration(
            MyDividerItemDecoration(
                this, LinearLayout.VERTICAL,
                20
            )
        )
        val adapter = BasicHorizontalAdapter()
        recyclerView.adapter = adapter
        adapter.setNewInstance(data)
        adapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent().setClass(
                    this@MainActivity,
                    data[position].clazz
                )
            )
        }
    }
}