package com.tainzhi.android.sample.github.ui.search

import android.content.Context
import android.os.Bundle
import android.os.IBinder
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.tainzhi.android.sample.github.AppExecutors
import com.tainzhi.android.sample.github.R
import com.tainzhi.android.sample.github.binding.FragmentDatabindingComponent
import com.tainzhi.android.sample.github.databinding.SearchFragmentBinding
import com.tainzhi.android.sample.github.di.Injectable
import com.tainzhi.android.sample.github.ui.common.RepoListAdapter
import com.tainzhi.android.sample.github.ui.common.RetryCallback
import com.tainzhi.android.sample.github.util.autoCleared
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDatabindingComponent(this)
    var binding by autoCleared<SearchFragmentBinding>()
    var adapter by autoCleared<RepoListAdapter>()

    val searchViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.search_fragment,
            container,
            false,
            dataBindingComponent
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
        val tAdapter = RepoListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors,
            showFullName = true
        ) { repo ->
            navController().navigate(
                SearchFragmentDirections.actionSearchFragmentToRepoFragment(repo.owner.login, repo.name)
            )
        }

        binding.query = searchViewModel.query
        binding.repoList.adapter = tAdapter
        adapter = tAdapter

        initSearchInputListener()

        binding.callback = object : RetryCallback {
            override fun retry() {
                searchViewModel.refresh()
            }
        }
    }

    private fun initSearchInputListener() {
        binding.input.setOnEditorActionListener { view: View, actionId: Int, _: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                doSearch(view)
                true
            } else {
                false
            }
        }
        binding.input.setOnKeyListener { view: View, keyCode: Int, event: KeyEvent ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                doSearch(view)
                true
            } else {
                false
            }
        }
    }

    private fun doSearch(v : View) {
        val query = binding.input.text.toString()
        dismissKeyboard(v.windowToken)
        searchViewModel.setQuery(query)
    }

    private fun initRecyclerView() {
        binding.repoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                    searchViewModel.loadNextPage()
                }
            }
        })
        binding.searchResult = searchViewModel.results
        searchViewModel.results.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result?.data)
        })

        searchViewModel.loadMoreStatus.observe(viewLifecycleOwner, Observer { loadingMore ->
            if (loadingMore == null) {
                binding.loadingMore = false
            } else {
                binding.loadingMore = loadingMore.isRunning
                val error = loadingMore.errorMessageIfNotHandled
                if (error != null) {
                    Snackbar.make(binding.loadMoreBar, error, Snackbar.LENGTH_SHORT).show()
                }
            }
         })
    }


    private fun dismissKeyboard(windowToken: IBinder) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(windowToken, 0)
    }

    private fun navController() = findNavController()
}