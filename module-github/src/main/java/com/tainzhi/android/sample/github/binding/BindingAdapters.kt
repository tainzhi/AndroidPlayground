package com.tainzhi.android.sample.github.binding

import android.view.View
import androidx.databinding.BindingAdapter

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/11 下午5:01
 * @description:
 **/

object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show :Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}