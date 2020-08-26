package com.tainzhi.sample.api.touch.single

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/5/7 14:59
 * @description:
 **/

class SingleTouchEventView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint()
    private val path = Path()
    private val gestureDetector by lazy {
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                path.reset()
                invalidate()
                return true
            }
        })
    }

    init {
        paint.run {
            isAntiAlias = true
            strokeWidth = 6f
            color = Color.BLACK
            style = Paint.Style.STROKE
            strokeJoin = Paint.Join.ROUND
        }
    }

    override fun onDraw(canvas: Canvas) {
//        canvas?.drawARGB(255, 0, 0, 255);
//        canvas?.drawLine(0f, 0f,200f, 200f, paint)
        canvas.drawPath(path, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        gestureDetector.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(x, y)
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                path.lineTo(x, y)
            }

            MotionEvent.ACTION_UP -> {
                path.lineTo(x, y)
            }
            else -> {
                return false
            }
        }
        invalidate()
        return true
    }

    fun setColor(r: Int, g: Int, b: Int) {
        paint.color = Color.rgb(r, g, b)
        invalidate()
    }


}