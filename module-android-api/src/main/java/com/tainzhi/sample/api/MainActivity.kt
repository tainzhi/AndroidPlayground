package com.tainzhi.sample.api

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.tainzhi.sample.api.adapter.BasicHorizontalAdapter
import com.tainzhi.sample.api.adapter.NameClass
import com.tainzhi.sample.api.handler.HandlerActivity
import com.tainzhi.sample.api.recyclerview.RecyclerViewEntranceActivity
import com.tainzhi.sample.api.tablayout.GoogleTabLayoutActivity
import com.tainzhi.sample.api.touch.TouchEntranceActivity
import com.tainzhi.sample.api.widget.MyDividerItemDecoration
import com.tanzhi.android.playground.router.RouterPath
import kotlinx.android.synthetic.main.api_activity_main.*

@Route(path = RouterPath.PATH_API)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.api_activity_main)
        val mToolbar = findViewById<Toolbar>(R.id.my_toolbar)
        mToolbar.title = "Android Api"
        setSupportActionBar(mToolbar)
        val data = arrayListOf(
            NameClass("引导页", GuidePageActivity::class.java),
            NameClass("TabLayout", GoogleTabLayoutActivity::class.java),
            NameClass("RecyclerView", RecyclerViewEntranceActivity::class.java),
            NameClass("Handler", HandlerActivity::class.java),
            NameClass("触摸事件", TouchEntranceActivity::class.java)
        )

        recycler_view.run {
            layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.VERTICAL,
                false
            )
            addItemDecoration(
                MyDividerItemDecoration(
                    this@MainActivity, LinearLayout.VERTICAL,
                    20
                )
            )
            adapter = BasicHorizontalAdapter().apply {
                setNewInstance(data)
                setOnItemClickListener { adapter, view, position ->
                    startActivity(
                        Intent().setClass(
                            this@MainActivity,
                            data[position].clazz
                        )
                    )
                }
            }
        }
    }
}