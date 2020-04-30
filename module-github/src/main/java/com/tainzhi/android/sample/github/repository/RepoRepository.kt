package com.tainzhi.android.sample.github.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.tainzhi.android.sample.github.AppExecutors
import com.tainzhi.android.sample.github.api.ApiResponse
import com.tainzhi.android.sample.github.api.ApiSuccessResponse
import com.tainzhi.android.sample.github.api.GithubService
import com.tainzhi.android.sample.github.api.RepoSearchResponse
import com.tainzhi.android.sample.github.db.GithubDb
import com.tainzhi.android.sample.github.db.RepoDao
import com.tainzhi.android.sample.github.util.AbsentLiveData
import com.tainzhi.android.sample.github.util.RateLimiter
import com.tainzhi.android.sample.github.vo.Contributor
import com.tainzhi.android.sample.github.vo.Repo
import com.tainzhi.android.sample.github.vo.RepoSearchResult
import com.tainzhi.android.sample.github.vo.Resource
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/13 17:04
 * @description:
 **/

@Singleton
class RepoRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: GithubDb,
    private val repoDao: RepoDao,
    private val githubService: GithubService
) {

    private val repolistRateLimit = RateLimiter<String>(10, TimeUnit.MINUTES)

    fun loadRepos(owner: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, List<Repo>>(appExecutors) {
            override fun onFetchFailed() {
                repolistRateLimit.reset(owner)
            }

            override fun saveCallResult(item: List<Repo>) {
                repoDao.insertRepos(item)
            }

            override fun shouldFetch(data: List<Repo>?): Boolean {
                return data == null || data.isEmpty() || repolistRateLimit.shouldFetch(owner)
            }

            override fun loadFromDb() = repoDao.loadRepositories(owner)

            override fun createAll() = githubService.getRepos(owner)
        }.asLiveData()
    }

    fun loadRepo(owner: String, name: String): LiveData<Resource<Repo>> {
        return object : NetworkBoundResource<Repo, Repo>(appExecutors) {
            override fun saveCallResult(item: Repo) {
                repoDao.insert(item)
            }

            override fun shouldFetch(data: Repo?) = data == null

            override fun loadFromDb() = repoDao.load(
                ownerLogin = owner,
                name = name
            )

            override fun createAll() = githubService.getRepo(
                owner = owner,
                name = name
            )
        }.asLiveData()
    }

    fun loadContributors(owner: String, name: String): LiveData<Resource<List<Contributor>>> {
        return object : NetworkBoundResource<List<Contributor>, List<Contributor>>(appExecutors) {
            override fun saveCallResult(item: List<Contributor>) {
                item.forEach {
                    it.repoName = name
                    it.repoOwner = owner
                }
                db.runInTransaction {
                    repoDao.createRepoIfNotExists(
                        Repo(
                            id = Repo.UNKNOWN_ID,
                            name = name,
                            fullName = "$owner/$name",
                            description = "",
                            owner = Repo.Owner(owner, null),
                            stars = 0
                        )
                    )
                    repoDao.insertContributors(item)
                }
            }

            override fun shouldFetch(data: List<Contributor>?): Boolean {
                return data == null || data.isEmpty()
            }

            override fun loadFromDb() = repoDao.loadContributors(owner, name)

            override fun createAll(): LiveData<ApiResponse<List<Contributor>>> {
                return githubService.getContributors(owner, name)
            }
        }.asLiveData()
    }

    fun searchNextPage(query: String): LiveData<Resource<Boolean>> {
        val fetchNextSearchPageTask = FetchNextSearchPageTask(
            query = query,
            githubService = githubService,
            db = db
        )
        appExecutors.networkIO().execute(fetchNextSearchPageTask)
        return fetchNextSearchPageTask.liveData
    }

    fun search(query: String): LiveData<Resource<List<Repo>>> {
        return object : NetworkBoundResource<List<Repo>, RepoSearchResponse>(appExecutors) {
            override fun saveCallResult(item: RepoSearchResponse) {
                val repoIds = item.items.map { it.id }
                val repoSearchResult = RepoSearchResult(
                    query = query,
                    repoIds = repoIds,
                    totalCount = item.total,
                    next = item.nextPage
                )
                db.runInTransaction {
                    repoDao.insertRepos(item.items)
                    repoDao.insert(repoSearchResult)
                }
            }

            override fun shouldFetch(data: List<Repo>?) = data == null

            override fun loadFromDb(): LiveData<List<Repo>> {
                return Transformations.switchMap(repoDao.search(query)) { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        repoDao.loadOrdered(searchData.repoIds)
                    }
                }
            }

            override fun createAll() =  githubService.searchRepos(query)

            override fun processResponse(response: ApiSuccessResponse<RepoSearchResponse>)
                    : RepoSearchResponse {
                val body = response.body
                body.nextPage = response.nextPage
                return body
            }
        }.asLiveData()
    }
}