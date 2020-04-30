package com.tainzhi.android.sample.github.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tainzhi.android.sample.github.vo.Contributor
import com.tainzhi.android.sample.github.vo.Repo
import com.tainzhi.android.sample.github.vo.RepoSearchResult
import com.tainzhi.android.sample.github.vo.User

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/11 下午8:40
 * @description:
 **/

@Database(
    entities = [
    User::class,
    Repo::class,
    Contributor::class,
    RepoSearchResult::class],
    version = 1,
    exportSchema =  false
)
abstract class GithubDb :RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repoDao(): RepoDao
}