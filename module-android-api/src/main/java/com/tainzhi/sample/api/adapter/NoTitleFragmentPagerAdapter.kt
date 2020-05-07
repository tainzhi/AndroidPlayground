package com.tainzhi.sample.api.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * Created by muqing on 9/1/2019.
 * Email: qfq61@qq.com
 */
class NoTitleFragmentPagerAdapter(
    fm: FragmentManager?,
    private val mFragmentList: List<Fragment>
) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

}