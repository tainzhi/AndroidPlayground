package com.tainzhi.sample.util

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/7/20 13:59
 * @description:
 **/

inline fun <reified T> Activity.dpToPx(value: Int): T {
    val result = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value.toFloat(), this.resources.displayMetrics)

    return when (T::class) {
        Float::class -> result as T
        Int::class -> result.toInt() as T
        else -> throw IllegalStateException("Type not supported")
    }
}

inline fun <reified T> Context.dpToPx(value: Int): T {
    val result = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(), this.resources.displayMetrics)

    return when (T::class) {
        Float::class -> result as T
        Int::class -> result.toInt() as T
        else -> throw IllegalStateException("Type not supported")
    }
}

inline fun <reified T> Activity.spToPx(value: Int): T {
    val result = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        value.toFloat(), this.resources.displayMetrics)

    return when (T::class) {
        Float::class -> result as T
        Int::class -> result.toInt() as T
        else -> throw IllegalStateException("Type not supported")
    }
}

fun Activity.screenWidth(): Int {
    val dm = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

fun Activity.screenHeight(): Int {
    val dm = DisplayMetrics()
    this.windowManager.defaultDisplay.getMetrics(dm)
    return dm.heightPixels
}
