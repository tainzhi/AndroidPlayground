package com.tainzhi.android.sample.github.ui.common

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/14 11:31
 * @description:
 **/

class DataBoundViewHodler<out T: ViewDataBinding> constructor(val binding: T) : RecyclerView.ViewHolder(binding.root)
