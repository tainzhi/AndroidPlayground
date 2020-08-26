package com.tainzhi.sample.api.tablayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout
import com.tainzhi.sample.api.R
import com.tainzhi.sample.api.adapter.NoTitleFragmentPagerAdapter
import java.util.*

class GoogleTabLayoutAdvanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_tab_layout_advance)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        initView()
    }

    private fun initView() {
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        val strings = arrayOf("fragment1", "fragment2", "fragment3", "fragment4", "fragment5")
        val fragmentList: MutableList<Fragment> = ArrayList()
        var i = 0
        val length = strings.size
        while (i < length) {
            fragmentList.add(MiniumFragment(strings[i]))
            i++
        }
        val fragmentPagerAdapter = NoTitleFragmentPagerAdapter(supportFragmentManager, fragmentList)
        viewPager.adapter = fragmentPagerAdapter
        // tabLayout.setupWithViewPager(viewPager); // 会覆盖掉xml文件的tabitem
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                tabLayout.setScrollPosition(position, positionOffset, true)
            }

            override fun onPageSelected(position: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}
        })
    }
}