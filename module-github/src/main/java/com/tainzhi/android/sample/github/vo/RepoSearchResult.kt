package com.tainzhi.android.sample.github.vo

import androidx.room.Entity
import androidx.room.TypeConverters
import com.tainzhi.android.sample.github.db.GithubTypeConverers

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/10 下午4:31
 * @description:
 **/

@Entity(primaryKeys = ["query"])
@TypeConverters(GithubTypeConverers::class)
data class RepoSearchResult (
    val query: String,
    val repoIds: List<Int>,
    val totalCount: Int,
    val next: Int?
)