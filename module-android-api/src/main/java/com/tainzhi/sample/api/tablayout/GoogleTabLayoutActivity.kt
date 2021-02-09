package com.tainzhi.sample.api.tablayout

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.tainzhi.sample.api.R
import com.tainzhi.sample.api.adapter.SimpleFragmentPagerAdapter

class GoogleTabLayoutActivity : AppCompatActivity() {
    private val mTabLayout: TabLayout by lazy { findViewById<TabLayout>(R.id.tablayout) }
    private val mViewPager: ViewPager by lazy { findViewById<ViewPager>(R.id.view_pager) }
    private val mFragmentList =  ArrayList<Fragment>()
    private val mTitleList = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_tab_layout)
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)
        init()
    }

    private fun init() {
        val titleString = arrayOf("TAB1", "TAB2", "TAB3", "TAB4", "TAB5")
        for (i in 0..4) {
            mTitleList.add(titleString[i])
            mFragmentList.add(MiniumFragment(titleString[i]))
        }
        mViewPager.setOffscreenPageLimit(2) //pre load
        mViewPager.setAdapter(
            SimpleFragmentPagerAdapter(
                supportFragmentManager,
                mTitleList,
                mFragmentList
            )
        )
        mTabLayout.setupWithViewPager(mViewPager) //必须添加
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_tablayout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_tab_with_icon) {
            startActivity(
                Intent().setClass(
                    this@GoogleTabLayoutActivity,
                    GoogleTabLayoutAdvanceActivity::class.java
                )
            )
        }
        return true
    }
}