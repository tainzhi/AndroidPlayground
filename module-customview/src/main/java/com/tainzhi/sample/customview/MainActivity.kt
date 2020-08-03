package com.tainzhi.sample.customview

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.tainzhi.sample.customview.adapter.BasicHorizontalAdapter
import com.tainzhi.sample.customview.adapter.NameClass
import com.tainzhi.sample.customview.widget.MyDividerItemDecoration
import com.tanzhi.android.playground.router.RouterPath
import kotlinx.android.synthetic.main.content_main.*

@Route(path = RouterPath.PATH_CUSTOM_VIEW)
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_main)
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        mToolbar.title = "Android Api"
        setSupportActionBar(mToolbar)
        val data = arrayListOf(
            NameClass("环形进度条", CirclePercentActivity::class.java),
            NameClass("图片指示器的进度条", LevelUpActivity::class.java),
            NameClass("波浪进度", WaveProgressActivity::class.java)
        )

        customRV.run {
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
            adapter = BasicHorizontalAdapter()
                .apply {
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
