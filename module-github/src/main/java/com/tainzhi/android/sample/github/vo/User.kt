package com.tainzhi.android.sample.github.vo

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/10 下午3:19
 * @description:
 **/

@Entity(primaryKeys = ["login"])
data class User (
    @field:SerializedName("login")
    val login: String,
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("company")
    val company: String?,
    @field:SerializedName("repos_url")
    val reposUrl: String?,
    @field:SerializedName("blog")
    val blog: String?
)
