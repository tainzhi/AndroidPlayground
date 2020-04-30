package com.tainzhi.android.sample.github.ui.search

import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.tainzhi.android.sample.github.repository.RepoRepository
import com.tainzhi.android.sample.github.util.AbsentLiveData
import com.tainzhi.android.sample.github.vo.Repo
import com.tainzhi.android.sample.github.vo.Resource
import com.tainzhi.android.sample.github.vo.Status
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(repository: RepoRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    private val nextPageHandler = NextPageHandler(repository)

    val query : LiveData<String> = _query

    val results : LiveData<Resource<List<Repo>>> = Transformations
        .switchMap(_query) {search ->
            if (search.isNullOrBlank()) {
                AbsentLiveData.create()
            } else {
                repository.search(search)
            }
        }

    val loadMoreStatus : LiveData<LoadMoreState>
        get() = nextPageHandler.loadMoreState

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query.value) {
            return
        }
        nextPageHandler.reset()
        _query.value = input
    }

    fun loadNextPage() {
        _query.value?.let {
            if (it.isNotBlank()) {
                nextPageHandler.queryNextPage(it)
            }
        }
    }

    fun refresh() {
        _query.value?.let{
            _query.value = it
        }
    }

    class LoadMoreState(val isRunning: Boolean, val errorMessage: String?) {
        private var handledError = false

        val errorMessageIfNotHandled: String?
            get() {
                if (handledError) {
                    return null
                }
                handledError = true
                return errorMessage

            }
    }
    class NextPageHandler(private val repository: RepoRepository) : Observer<Resource<Boolean>> {
        private var nextPageLiveData : LiveData<Resource<Boolean>>? = null
        val loadMoreState = MutableLiveData<LoadMoreState>()
        private var query: String? = null
        private var _hashMore : Boolean = false
        val hasMore
            get() = _hashMore
        init {
            reset()
        }

        fun queryNextPage(query: String) {
            if (this.query == query) {
                return
            }
            unregister()
            this.query = query
            nextPageLiveData = repository.searchNextPage(query)
            loadMoreState.value = LoadMoreState(
                isRunning = true,
                errorMessage = null
            )
            nextPageLiveData?.observeForever(this)
        }

        override fun onChanged(result: Resource<Boolean>?) {
            if (result == null) {
                reset()
            } else {
                when (result.status) {
                    Status.SUCCESS -> {
                        _hashMore = result.data == true
                        unregister()
                        loadMoreState.setValue(
                            LoadMoreState(
                                isRunning = false,
                                errorMessage = null
                            )
                        )
                    }
                    Status.ERROR -> {
                        _hashMore = true
                        unregister()
                        loadMoreState.setValue(
                            LoadMoreState(
                                isRunning = false,
                                errorMessage = result.message
                            )
                        )
                    }
                    Status.LOADING -> {
                        // ignore
                    }
                }
            }

        }

        private fun unregister() {
            nextPageLiveData?.removeObserver(this)
            nextPageLiveData = null
            if (_hashMore) {
                query = null
            }
        }

        fun reset() {
            unregister()
            _hashMore = true
            loadMoreState.value = LoadMoreState(
                isRunning = false,
                errorMessage = null
            )
        }
    }
}