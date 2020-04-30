package com.tainzhi.sample.api

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tainzhi.sample.api.adapter.MultiItemAdapter
import kotlinx.android.synthetic.main.activity_nested_recycler_view.*
import kotlinx.android.synthetic.main.content_nested_recycler_view.*

class NestedRecyclerViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_recycler_view)
        setSupportActionBar(toolbar)

        initView()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun initView() {
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager
                .VERTICAL, false)
        val datas: List<MultiItem> = arrayListOf(MultiItem(MultiItem.FirstType))
        recycler_view.adapter = MultiItemAdapter(datas, recycler_view)
    }

}
