package com.tainzhi.sample.api

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.tainzhi.sample.api.adapter.BasicVertialAdapter
import java.util.*

class RecyclerViewStaggeredGridLayoutActivity : AppCompatActivity(),
    RecyclerView.RecyclerListener {
    private var recyclerView: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_staggered_grid_layout)
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
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setRecyclerListener(this)
        val layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.setLayoutManager(layoutManager)
        val datas: MutableList<String> =
            ArrayList()
        for (i in 0..29) {
            datas.add(i.toString())
        }
        val adapter = BasicVertialAdapter(true)
        adapter.addData(datas)
        recyclerView.setAdapter(adapter)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        Log.d(
            "qfq",
            "position=" + holder.adapterPosition + ", height=" + recyclerView!!.height
        )
    }
}