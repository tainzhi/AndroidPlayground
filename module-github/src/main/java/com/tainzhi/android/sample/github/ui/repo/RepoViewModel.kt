package com.tainzhi.android.sample.github.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tainzhi.android.sample.github.repository.RepoRepository
import com.tainzhi.android.sample.github.util.AbsentLiveData
import com.tainzhi.android.sample.github.vo.Contributor
import com.tainzhi.android.sample.github.vo.Repo
import com.tainzhi.android.sample.github.vo.Resource
import javax.inject.Inject

class RepoViewModel @Inject constructor(repository: RepoRepository) : ViewModel() {
    private val _repoId: MutableLiveData<RepoId> = MutableLiveData()
    val repoId: LiveData<RepoId>
        get() = _repoId

    val repo: LiveData<Resource<Repo>> = Transformations.switchMap(_repoId) { input ->
        input.ifExists { owner, name ->
            repository.loadRepo(owner, name)
        }
    }

    val contributors: LiveData<Resource<List<Contributor>>> = Transformations.switchMap(_repoId) { input ->
        input.ifExists { owner, name ->
            repository.loadContributors(owner, name)
        }
    }

    fun retry() {
        val owner = _repoId.value?.owner
        val name = _repoId.value?.name
        if (owner != null && name != null) {
            _repoId.value = RepoId(owner, name)
        }
    }

    fun setId(owner: String, name: String) {
        val update = RepoId(owner, name)
        if (_repoId.value == update) {
            return
        }
        _repoId.value = update
    }

    data class RepoId(val owner: String, val name: String) {
        fun <T> ifExists(f: (String, String) -> LiveData<T>): LiveData<T> {
            return if (owner.isBlank() || name.isBlank()) {
                AbsentLiveData.create()
            } else {
                f(owner, name)
            }
        }
    }
}