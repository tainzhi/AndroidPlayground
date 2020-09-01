package com.tainzhi.sample.util

import android.graphics.Color
import kotlin.random.Random

/**
 * Created by muqing on 2019-08-15.
 * Email: qfq61@qq.com
 */

object ColorUtils {
    // 随机取出一种颜色
    fun randomColor() = Color.argb(
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255)
    )

    /**
     * 从开始颜色过渡到结束颜色
     *
     * @param startColor
     * @param endColor
     * @param rate 过渡比率
     */
    fun computeGradientColor(startColor: Int, endColor: Int, rate: Float): Int {
        var rate = rate
        if (rate < 0) {
            rate = 0f
        }
        if (rate > 1) {
            rate = 1f
        }
        val alpha = Color.alpha(endColor) - Color.alpha(startColor)
        val red = Color.red(endColor) - Color.red(startColor)
        val green = Color.green(endColor) - Color.green(startColor)
        val blue = Color.blue(endColor) - Color.blue(startColor)
        return Color.argb(
            Math.round(Color.alpha(startColor) + alpha * rate),
            Math.round(Color.red(startColor) + red * rate),
            Math.round(Color.green(startColor) + green * rate),
            Math.round(Color.blue(startColor) + blue * rate)
        )
    }

}