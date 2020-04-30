package com.tainzhi.android.sample.github.repository

import androidx.lifecycle.LiveData
import com.tainzhi.android.sample.github.AppExecutors
import com.tainzhi.android.sample.github.api.ApiResponse
import com.tainzhi.android.sample.github.api.GithubService
import com.tainzhi.android.sample.github.db.UserDao
import com.tainzhi.android.sample.github.vo.Resource
import com.tainzhi.android.sample.github.vo.User
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/14 10:06
 * @description:
 **/

@Singleton
class UserRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val userDao: UserDao,
    private val githubService: GithubService
    ) {
    fun loadUser(login: String): LiveData<Resource<User>> {
        return object : NetworkBoundResource<User, User>(appExecutors) {
            override fun saveCallResult(item: User) {
                userDao.insert(item)
            }

            override fun shouldFetch(data: User?) = data == null

            override fun loadFromDb() = userDao.findByLogin(login)

            override fun createAll() = githubService.getUser(login)
        }.asLiveData()
    }
}