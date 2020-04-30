package com.tainzhi.android.sample.github.ui.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tainzhi.android.sample.github.AppExecutors

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/14 11:29
 * @description:
 **/

abstract class DataBoundListAdapter<T, V : ViewDataBinding>(
    appExecutors: AppExecutors,
    diffCallback: DiffUtil.ItemCallback<T>
) : ListAdapter<T, DataBoundViewHodler<V>>(
    AsyncDifferConfig.Builder<T>(diffCallback)
        .setBackgroundThreadExecutor(appExecutors.diskIO())
        .build()
){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHodler<V> {
        val binding = createBinding(parent)
        return DataBoundViewHodler(binding)
    }

    protected abstract fun createBinding(parent: ViewGroup) : V

    override fun onBindViewHolder(holder: DataBoundViewHodler<V>, position: Int) {
        bind(holder.binding, getItem(position))
        holder.binding.executePendingBindings()
    }

    protected abstract fun bind(binding: V, item: T)
}