package com.tainzhi.android.sample.github.util

import androidx.lifecycle.LiveData
import com.tainzhi.android.sample.github.api.ApiResponse
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/10 下午5:00
 * @description:
 **/

class LiveDataCallAdapter<R>(private val responseType: Type) :
    CallAdapter<R, LiveData<ApiResponse<R>>> {
    override fun adapt(call: Call<R>): LiveData<ApiResponse<R>> {
        return object : LiveData<ApiResponse<R>>() {
            private var started = AtomicBoolean(false)
            override fun onActive() {
                super.onActive()
                if (started.compareAndSet(false, true)) {
                    call.enqueue(object : Callback<R> {
                        override fun onFailure(call: Call<R>, t: Throwable) {
                            postValue(ApiResponse.Companion.create(t))
                        }

                        override fun onResponse(call: Call<R>, response: Response<R>) {
                            postValue(ApiResponse.Companion.create(response))
                        }
                    })
                }
            }
        }
    }

    override fun responseType() = responseType
}