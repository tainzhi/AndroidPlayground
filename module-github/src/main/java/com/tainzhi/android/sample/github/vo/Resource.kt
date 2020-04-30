package com.tainzhi.android.sample.github.vo

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/10 下午3:22
 * @description:
 **/

data class Resource<out T>(val status: Status, val message: String?, val data: T?) {
    companion object {
        fun <T>success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, null, data)
        }

        fun <T>error( message: String?, data: T?): Resource<T> {
            return Resource(Status.ERROR, message, data)
        }

        fun <T>loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, null,  data)
        }
    }
}
