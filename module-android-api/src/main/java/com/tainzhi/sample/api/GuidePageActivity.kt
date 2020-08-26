package com.tainzhi.sample.api

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.tainzhi.sample.api.adapter.GuidePageAdapter
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-10 20:13
 * @description:
 */
class GuidePageActivity : Activity(), OnPageChangeListener {
    private val imageIdArray = arrayOf(
        R.drawable.android_0, R.drawable.android_1, R.drawable.android_2,
        R.drawable.android_3, R.drawable.android_4, R.drawable.android_5
    )
    private var imageViews=  ArrayList<View>()
    private var mPointViews =  ArrayList<ImageView>()
    private var mViewPager: ViewPager? = null
    private var mLlPointContainer: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_page)
        mViewPager = findViewById(R.id.view_pager)
        mLlPointContainer = findViewById(R.id.ll_guide_dot)
        initViewPager()
        initPoint()
    }

    private fun initViewPager() {
        imageViews = ArrayList()
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        for (id in imageIdArray) {
            val imageView = ImageView(this)
            imageView.setImageResource(id)
            imageView.layoutParams = params
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageViews.add(imageView)
        }
        mViewPager!!.adapter = GuidePageAdapter(imageViews)
        mViewPager!!.addOnPageChangeListener(this)
    }

    private fun initPoint() {
        mPointViews = ArrayList()
        var i = 0
        val len = imageViews!!.size
        while (i < len) {
            val params = LinearLayout.LayoutParams(30, 30)
            params.setMargins(20, 0, 0, 0)
            val imageView = ImageView(this)
            imageView.layoutParams = params
            if (i == 0) {
                imageView.setImageResource(R.drawable.guide_page_selected_point)
            } else {
                imageView.setImageResource(R.drawable.guide_page_unselected_point)
            }
            mPointViews.add(imageView)
            mLlPointContainer!!.addView(imageView)
            i++
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {
        var i = 0
        val len = imageViews!!.size
        while (i < len) {
            val imageView = mPointViews!![i]
            imageView.setImageResource(R.drawable.guide_page_unselected_point)
            i++
        }
        mPointViews!![position].setImageResource(R.drawable.guide_page_selected_point)
    }

    override fun onPageScrollStateChanged(state: Int) {}
}