package com.tainzhi.android.sample.github.ui.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.tainzhi.android.sample.github.AppExecutors
import com.tainzhi.android.sample.github.R
import com.tainzhi.android.sample.github.databinding.ContributorItemBinding
import com.tainzhi.android.sample.github.ui.common.DataBoundListAdapter
import com.tainzhi.android.sample.github.vo.Contributor

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/15 22:26
 * @description:
 **/

class ContributorAdapter(
    private val databindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val callback: ((Contributor, ImageView) -> Unit)?
) :DataBoundListAdapter<Contributor, ContributorItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object: DiffUtil.ItemCallback<Contributor>() {

        override fun areItemsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.login == newItem.login
        }

        override fun areContentsTheSame(oldItem: Contributor, newItem: Contributor): Boolean {
            return oldItem.avatarUrl == newItem.avatarUrl
                    && oldItem.contributions == newItem.contributions
        }

    }
){
    override fun createBinding(parent: ViewGroup): ContributorItemBinding {
        val binding = DataBindingUtil
            .inflate<ContributorItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.contributor_item,
                parent,
                false,
                databindingComponent
            )
        binding.root.setOnClickListener {
            binding.contributor?.let {
                callback?.invoke(it, binding.imageView)
            }
        }
        return binding
    }

    override fun bind(binding: ContributorItemBinding, item: Contributor) {
        binding.contributor = item
    }
}