package com.tainzhi.sample.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/7/16 16:39
 * @description:
 **/

class LevelProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var progressBarWidth: Float
    private var progressBarHeight: Float
    private var progressBarBackground: Int

    init {

        context.obtainStyledAttributes(
            attrs,
            R.styleable.LevelProgressBar, defStyleAttr, 0
        ).apply {
            progressBarWidth = getDimension(R.styleable.LevelProgressBar_progressBarWidth, 1080F)
            progressBarHeight = getDimension(R.styleable.LevelProgressBar_progressBarHeight, 60F)
            progressBarBackground = getColor(R.styleable.LevelProgressBar_progressBarBackground, 0xffedd1)

        }.recycle()
    }

    val paint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        val rect = RectF(0F, 0F, progressBarWidth, progressBarHeight)
        canvas.drawRect(rect, paint)
    }
}