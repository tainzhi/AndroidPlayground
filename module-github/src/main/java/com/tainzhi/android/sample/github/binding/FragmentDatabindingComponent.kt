package com.tainzhi.android.sample.github.binding

import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingComponent

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/11 下午5:03
 * @description:
 **/

class FragmentDatabindingComponent(fragment: Fragment): DataBindingComponent{
    private val adapter = FragmentBindingAdapters(fragment)

    override fun getFragmentBindingAdapters()  = adapter
}