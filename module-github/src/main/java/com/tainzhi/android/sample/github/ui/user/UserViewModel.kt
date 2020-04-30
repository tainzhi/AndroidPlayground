package com.tainzhi.android.sample.github.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.tainzhi.android.sample.github.repository.RepoRepository
import com.tainzhi.android.sample.github.repository.UserRepository
import com.tainzhi.android.sample.github.util.AbsentLiveData
import com.tainzhi.android.sample.github.vo.Repo
import com.tainzhi.android.sample.github.vo.Resource
import com.tainzhi.android.sample.github.vo.User
import javax.inject.Inject

class UserViewModel
    @Inject constructor(userRepository: UserRepository, repoRepository: RepoRepository): ViewModel() {
    private val _login = MutableLiveData<String?>()
    val login: LiveData<String?>
        get() = _login
    val repositories: LiveData<Resource<List<Repo>>> = Transformations
        .switchMap(login) { login ->
            if (login == null) {
                AbsentLiveData.create()
            } else {
                repoRepository.loadRepos(login)
            }
    }
    val user: LiveData<Resource<User>> = Transformations
        .switchMap(login) { login ->
            if (login == null) {
                AbsentLiveData.create()
            } else {
                userRepository.loadUser(login)
            }
        }
    fun setLogin(login: String?) {
        if (_login.value != login) {
            _login.value = login
        }
    }

    fun retry() {
        _login.value?.let {
            _login.value = it
        }
    }


}