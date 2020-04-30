package com.tainzhi.android.sample.github.ui.repo

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tainzhi.android.sample.github.AppExecutors
import com.tainzhi.android.sample.github.R
import com.tainzhi.android.sample.github.binding.FragmentDatabindingComponent
import com.tainzhi.android.sample.github.databinding.RepoFragmentBinding
import com.tainzhi.android.sample.github.di.Injectable
import com.tainzhi.android.sample.github.ui.common.RetryCallback
import com.tainzhi.android.sample.github.util.autoCleared
import javax.inject.Inject

class RepoFragment : Fragment() , Injectable{

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val repoViewModel: RepoViewModel by viewModels {
        viewModelFactory
    }

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDatabindingComponent(this)
    var binding by autoCleared<RepoFragmentBinding>()

    private val params by navArgs<RepoFragmentArgs>()
    private var adapter by autoCleared<ContributorAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<RepoFragmentBinding>(
                inflater,
                R.layout.repo_fragment,
                container,
                false
        )
        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                repoViewModel.retry()
            }
        }
        binding = dataBinding
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R
                .transition.move)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repoViewModel.setId(params.owner, params.name)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.repo = repoViewModel.repo

        val adapter = ContributorAdapter(dataBindingComponent, appExecutors) {
            contributor, imageView ->
            val extras = FragmentNavigatorExtras(
                    imageView to contributor.login
            )
            findNavController().navigate(
                    RepoFragmentDirections.actionRepoFragmentToUserFragment(
                            contributor.login,
                            contributor.avatarUrl
                    ), extras
            )
        }
        this.adapter = adapter
        binding.contributorList.adapter = adapter
        postponeEnterTransition()
        binding.contributorList.doOnPreDraw {
            startPostponedEnterTransition()
        }
        initContributorList(repoViewModel)
    }

    private fun initContributorList(viewModel: RepoViewModel) {
        viewModel.contributors.observe(viewLifecycleOwner, Observer { listResource ->
            if (listResource?.data!= null) {
                adapter.submitList(listResource.data)
            } else {
                adapter.submitList(emptyList())
            }
        })
    }
}