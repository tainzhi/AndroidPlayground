package com.tainzhi.sample.api.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * @author: tainzhi
 * @mail: qfq61@qq.com
 * @date: 2019-10-11 09:03
 * @description:
 */
class GuidePageAdapter(private val mViewList: List<View>?) : PagerAdapter() {
    override fun getCount(): Int {
        return mViewList?.size ?: 0
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(mViewList!![position])
        return mViewList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(mViewList!![position])
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}