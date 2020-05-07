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
import com.tainzhi.sample.api.handler.HandlerActivity
import com.tainzhi.sample.api.widget.MyDividerItemDecoration
import java.util.*

@Route(path = "/api/main")
class MainActivity : AppCompatActivity() {
    var mToolbar: Toolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mToolbar = findViewById(R.id.my_toolbar)
        setSupportActionBar(mToolbar)
        val data =
            LinkedHashMap<String, Class<*>>()
        data["引导页"] = GuidePageActivity::class.java
        data["TabLayout"] = GoogleTabLayoutActivity::class.java
        data["RecyclerView"] = RecyclerViewEntranceActivity::class.java
        data["Handler的用法"] = HandlerActivity::class.java
        val keys: List<String> =
            ArrayList(data.keys)
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
        adapter.addData(keys)
        adapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent().setClass(
                    this@MainActivity,
                    data[keys[position]]!!
                )
            )
        }
    }
}