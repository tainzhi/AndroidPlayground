package com.tainzhi.sample.api

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.tainzhi.sample.api.RecyclerViewEntranceActivity
import com.tainzhi.sample.api.adapter.BasicHorizontalAdapter
import com.tainzhi.sample.api.widget.MyDividerItemDecoration
import java.util.*

class RecyclerViewEntranceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_entrance)
        val toolbar =
            findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val fab =
            findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(
                view,
                "Replace with your own action",
                Snackbar.LENGTH_LONG
            )
                .setAction("Action", null).show()
        }
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
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
        val datas =
            LinkedHashMap<String, Class<*>>()
        datas["horizontal/middle"] = RecyclerViewActivity::class.java
        datas["GridLayoutManager"] = RecyclerViewGridLayoutActivity::class.java
        datas["StaggeredGridLayoutManager"] = RecyclerViewStaggeredGridLayoutActivity::class.java
        datas["Search Filter/Swpie delete"] = RecyclerViewFilterableActivity::class.java
        val keys: List<String> =
            ArrayList(datas.keys)
        adapter.addData(keys)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent().setClass(
                    this@RecyclerViewEntranceActivity,
                    datas[keys[position]]!!
                )
            )
        }
    }
}