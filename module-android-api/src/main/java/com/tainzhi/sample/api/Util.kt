package com.tainzhi.sample.api

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.DisplayMetrics
import kotlin.random.Random

/**
 * Created by muqing on 2019-08-15.
 * Email: qfq61@qq.com
 */
class Util {
    object Dimens {
        fun dpToPx(context: Context, dp: Float): Float {
            return dp * context.resources.displayMetrics.density
        }

        fun pxToDp(context: Context, px: Float): Float {
            return px / context.resources.displayMetrics.density
        }

        fun dpToPxInt(context: Context, dp: Float): Int {
            return (dpToPx(context, dp) + 0.5f).toInt()
        }

        fun pxToDpCeilInt(context: Context, px: Float): Int {
            return (pxToDp(context, px) + 0.5f).toInt()
        }
    }

    object Display {
        fun getScreenWidth(context: Activity): Int {
            val dm = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(dm)
            return dm.widthPixels
        }

        fun getScreenHeight(context: Activity): Int {
            val dm = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(dm)
            return dm.heightPixels
        }
    }
}

object ColorUtils {
    fun randomColor() = Color.argb(
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255),
        Random.nextInt(255)
    )
}