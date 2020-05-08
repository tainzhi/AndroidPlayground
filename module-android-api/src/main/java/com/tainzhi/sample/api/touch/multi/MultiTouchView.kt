package com.tainzhi.sample.api.touch.multi

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.widget.ConstraintSet

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/5/7 21:04
 * @description:
 **/

class MultiTouchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val size = 60
    private val activePoints = SparseArray<PointF>()
    private val paint = Paint()
    private val textPaint = Paint()
    private val colors = arrayOf(Color.BLUE, Color.GREEN, Color.MAGENTA, Color.BLACK,
        Color.CYAN, Color.RED, Color.DKGRAY, Color.LTGRAY, Color.YELLOW)

    init {
        paint.run {
            isAntiAlias = true
            color = Color.BLUE
            style = Paint.Style.FILL_AND_STROKE
        }
        textPaint.run {
            isAntiAlias = true
            textSize = 20f
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val pointIndex = event.actionIndex
        val pointId = event.getPointerId(pointIndex)

        when(event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val f = PointF(event.getX(pointIndex), event.getY(pointIndex))
                activePoints.put(pointId, f)
            }
            MotionEvent.ACTION_MOVE -> {
                for (i in 0 until event.pointerCount) {
                    val point = activePoints[event.getPointerId(i)]
                    point?.set(event.getX(i), event.getY(i))
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP, MotionEvent.ACTION_CANCEL -> {
                activePoints.remove(pointId)
            }
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until activePoints.size()) {
            val point = activePoints.valueAt(i)
            if (point != null) {
                paint.color = colors[i % 9]
            }
            canvas.drawCircle(point.x, point.y, size.toFloat(), paint)
        }
        canvas.drawText("Total pointers: " + activePoints.size(), 10F, 40F, textPaint)
    }
}