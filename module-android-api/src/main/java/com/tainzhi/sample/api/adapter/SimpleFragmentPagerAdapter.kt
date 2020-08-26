package com.tainzhi.sample.api.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by muqing on 2019-08-03.
 * Email: qfq61@qq.com
 */
class SimpleFragmentPagerAdapter(
    fm: FragmentManager?,
    private val mTitleList: List<String>,
    private val mFragmentList: List<Fragment>
) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }
}