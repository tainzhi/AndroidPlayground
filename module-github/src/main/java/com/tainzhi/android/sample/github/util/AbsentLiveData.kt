package com.tainzhi.android.sample.github.util

import androidx.lifecycle.LiveData

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/10 下午4:51
 * @description:
 **/

class AbsentLiveData<T: Any?> private constructor(): LiveData<T> (){
    init {
        postValue(null)
    }

    companion object {
        fun<T> create(): LiveData<T> {
            return AbsentLiveData()
        }
    }
}