package com.tainzhi.android.sample.github.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.tainzhi.android.sample.github.AppExecutors
import com.tainzhi.android.sample.github.R
import com.tainzhi.android.sample.github.databinding.RepoItemBinding
import com.tainzhi.android.sample.github.vo.Repo

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/11 下午5:19
 * @description:
 **/

class RepoListAdapter(
    private val dataBindingComponent: androidx.databinding.DataBindingComponent,
    appExecutors: AppExecutors,
    private val showFullName: Boolean,
    private val repoClickCallback: ((Repo) -> Unit)?
) : DataBoundListAdapter<Repo, RepoItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.owner == newItem.owner
                    && oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.description == newItem.description
                    && oldItem.stars == newItem.stars
        }
    }
) {

    override fun createBinding(parent: ViewGroup): RepoItemBinding {
        val binding = DataBindingUtil.inflate<RepoItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.repo_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.showFullName = showFullName
        binding.root.setOnClickListener {
            binding.repo?.let {
                repoClickCallback?.invoke(it)
            }
        }
        return binding
    }


    override fun bind(binding: RepoItemBinding, item: Repo) {
        binding.repo = item
    }
}