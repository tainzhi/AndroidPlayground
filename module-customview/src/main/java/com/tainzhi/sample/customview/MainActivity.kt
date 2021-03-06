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
            NameClass("验证码填入框", VerificationCodeActivity::class.java),
            NameClass("波浪形态的进度条", WaveProgressActivity::class.java),
            NameClass("水平滚动选择器", ScrollPickerActivity::class.java),
            NameClass("购物车动画", ShopCarAnimationActivity::class.java),
            NameClass("自定义ItemDecoration", RecyclerViewItemDecorationActivity::class.java),
            NameClass("自定义LayoutManager", RecyclerViewCustomLayoutMangerActivity::class.java),
            NameClass("自定义高级LayoutManager, 简单复用", RecyclerViewAdvancedCustomLayoutMangerActivity::class.java),
            NameClass("自定义高级LayoutManager, 高级复用", RecyclerViewAdvancedCustomLayoutManger2Activity::class.java),
            NameClass("终极LayoutManager, 酷炫3D效果", RecyclerViewAdvancedCustomLayoutManger3Activity::class.java),
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
                    setOnItemClickListener { _, _, position ->
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
