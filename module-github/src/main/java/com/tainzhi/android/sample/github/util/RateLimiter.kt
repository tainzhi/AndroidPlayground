package com.tainzhi.android.sample.github.util

import android.os.SystemClock
import android.util.ArrayMap
import java.util.concurrent.TimeUnit

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/11 下午2:41
 * @description:
 **/
 
class RateLimiter<in KEY>(timeout: Int, timeUnit: TimeUnit) {
    private val timestamps = ArrayMap<KEY, Long>()
    private val timeout = timeUnit.toMillis(timeout.toLong())

    private fun now() = SystemClock.uptimeMillis()

    @Synchronized
    fun shouldFetch(key: KEY):Boolean {
        val lastFetched = timestamps[key]
        val now = now()
        if (lastFetched == null){
            timestamps[key] = now
            return true
        }
        if (now - lastFetched >timeout) {
            timestamps[key] = now
            return true
        }
        return false
    }

    @Synchronized
    fun reset(key: KEY) {
        timestamps.remove(key)
    }
}