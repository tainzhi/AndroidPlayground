package com.tainzhi.sample.api.recyclerview

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.tainzhi.sample.api.R
import com.tainzhi.sample.api.adapter.BasicHorizontalAdapter
import com.tainzhi.sample.api.adapter.NameClass
import com.tainzhi.sample.api.widget.MyDividerItemDecoration

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
        val datas = arrayListOf(
            NameClass("horizontal/middle", RecyclerViewActivity::class.java),
            NameClass("GridLayoutManager", RecyclerViewGridLayoutActivity::class.java),
            NameClass("StaggeredGridLayoutManager", RecyclerViewStaggeredGridLayoutActivity::class.java),
            NameClass("Search Filter/Swipe delete", RecyclerViewFilterableActivity::class.java)
        )
        adapter.setNewInstance(datas)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent().setClass(
                    this@RecyclerViewEntranceActivity,
                    datas[position].clazz
                )
            )
        }
    }
}