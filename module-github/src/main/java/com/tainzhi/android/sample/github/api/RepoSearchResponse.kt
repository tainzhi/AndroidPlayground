package com.tainzhi.android.sample.github.api

import com.google.gson.annotations.SerializedName
import com.tainzhi.android.sample.github.vo.Repo

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/11 下午8:38
 * @description:
 **/

data class RepoSearchResponse(
    @SerializedName("total_count")
    val total: Int = 0,
    @SerializedName("items")
    val items: List<Repo>

) {
    var nextPage: Int? = null
}