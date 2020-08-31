package com.tainzhi.sample.util

import android.graphics.Color
import kotlin.random.Random

/**
 * Created by muqing on 2019-08-15.
 * Email: qfq61@qq.com
 */

object ColorUtils {
    fun randomColor() = Color.argb(
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255)
    )
}