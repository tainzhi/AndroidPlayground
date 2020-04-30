package com.tainzhi.android.sample.github.vo

import androidx.room.Entity
import androidx.room.ForeignKey
import com.google.gson.annotations.SerializedName

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/10 下午4:37
 * @description:
 **/

@Entity(
    primaryKeys = ["repoName", "repoOwner", "login"],
    foreignKeys = [ForeignKey(
        entity = Repo::class,
        parentColumns = ["name", "owner_login"],
        childColumns = ["repoName", "repoOwner"],
        onUpdate = ForeignKey.CASCADE,
        deferred = true
    )]
)
data class Contributor(
    @field:SerializedName("login")
    val login: String,
    @field:SerializedName("contributions")
    val contributions: Int,
    @field:SerializedName("avatar_url")
    val avatarUrl: String?
) {

    // does not show up in the response but set in post processing.
    lateinit var repoName: String
    // does not show up in the response but set in post processing.
    lateinit var repoOwner: String
}
