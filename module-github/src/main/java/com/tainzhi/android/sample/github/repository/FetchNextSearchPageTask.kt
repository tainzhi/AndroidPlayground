package com.tainzhi.android.sample.github.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tainzhi.android.sample.github.api.ApiErrorResponse
import com.tainzhi.android.sample.github.api.ApiResponse
import com.tainzhi.android.sample.github.api.ApiSuccessResponse
import com.tainzhi.android.sample.github.api.GithubService
import com.tainzhi.android.sample.github.db.GithubDb
import com.tainzhi.android.sample.github.vo.RepoSearchResult
import com.tainzhi.android.sample.github.vo.Resource
import java.io.IOException

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/14 09:43
 * @description:
 **/

class FetchNextSearchPageTask constructor(
    private val query: String,
    private val githubService: GithubService,
    private val db: GithubDb
) : Runnable {
    private val _liveData = MutableLiveData<Resource<Boolean>>()
    val liveData : LiveData<Resource<Boolean>> = _liveData

    override fun run() {
        val current = db.repoDao().findSearchResult(query)
        if (current == null) {
            _liveData.postValue(null)
            return
        }
        val nextPage = current.next
        if (nextPage == null) {
            _liveData.postValue(Resource.success(false))
            return
        }
        val newValue = try {
            val response = githubService.searchRepos(query, nextPage).execute()
            val apiResponse = ApiResponse.create(response)
            when (apiResponse) {
                is ApiSuccessResponse -> {
                    val ids = arrayListOf<Int>()
                    ids.addAll(apiResponse.body.items.map {it.id})
                    val merged = RepoSearchResult(
                        query, ids,
                        apiResponse.body.total, apiResponse.nextPage
                    )
                    db.runInTransaction {
                        db.repoDao().insert(merged)
                        db.repoDao().insertRepos(apiResponse.body.items)
                    }
                    Resource.success(apiResponse.nextPage != null)
                }
                is ApiErrorResponse -> {
                    Resource.success(false)
                }
                is ApiErrorResponse -> {
                    Resource.error(apiResponse.errorMessage, true)
                }
                else -> Resource.error("error", null)
            }
        } catch (e : IOException) {
            Resource.error(e.message!!, true)
        }
        _liveData.postValue(newValue)
    }
}