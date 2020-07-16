package com.tainzhi.sample.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

/**
 * Created by muqing on 2019/7/7.
 * Email: qfq61@qq.com
 */
class CirclePercentView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var mRadius: Float
    private val mStripeWidth: Float
    private var mHeight = 0
    private var mWidth = 0
    private var mCurrentPercent: Int
    private var mPercent = 0
    private var centerX = 0f
    private var centerY = 0f
    private var mEndAngle = 0
    private val mSmallColor: Int
    private val mBigColor: Int
    private val mCenterTextSize: Float
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
            mRadius = widthSize / 2.toFloat()
            centerX = widthSize / 2.toFloat()
            centerY = heightSize / 2.toFloat()
            mWidth = widthSize
            mHeight = heightSize
        }
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            mWidth = (mRadius * 2).toInt()
            mHeight = (mRadius * 2).toInt()
            centerX = mRadius
            centerY = mRadius
        }
        setMeasuredDimension(mWidth, mHeight)
    }

    override fun onDraw(canvas: Canvas) {
        mEndAngle = (mCurrentPercent * 3.6).toInt()
        val bigCirclepaint = Paint()
        bigCirclepaint.isAntiAlias = true
        bigCirclepaint.color = mBigColor
        canvas.drawCircle(centerX, centerY, mRadius, bigCirclepaint)
        val sectorPaint = Paint()
        sectorPaint.color = mSmallColor
        sectorPaint.isAntiAlias = true
        val rect = RectF(0F, 0F, mWidth.toFloat(), mHeight.toFloat())
        canvas.drawArc(rect, 270f, mEndAngle.toFloat(), true, sectorPaint)
        val smallCirclePaint = Paint()
        smallCirclePaint.isAntiAlias = true
        smallCirclePaint.color = mBigColor
        canvas.drawCircle(centerX, centerY, mRadius - mStripeWidth, smallCirclePaint)
        val textPaint = Paint()
        val text = "$mCurrentPercent%"
        textPaint.textSize = mCenterTextSize
        val textLength = textPaint.measureText(text)
        textPaint.color = Color.WHITE
        canvas.drawText(text, centerX - textLength / 2, centerY, textPaint)
    }

    fun setPercent(percent: Int) {
        require(percent <= 100) { "percent must less than 100!" }
        setCurrentPercent(percent)
    }

    private fun setCurrentPercent(percent: Int) {
        mPercent = percent
        Thread(Runnable {
            var sleepTime = 1
            for (i in 0 until mPercent) {
                if (i % 20 == 0) {
                    sleepTime += 2
                }
                try {
                    Thread.sleep(sleepTime.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                mCurrentPercent = i
                this@CirclePercentView.postInvalidate()
            }
        }).start()
    }

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.CirclePercentView, defStyleAttr, 0
        )
        mStripeWidth = typedArray.getDimension(R.styleable.CirclePercentView_stripWidth, 30f)
        mCurrentPercent = typedArray.getInteger(
            R.styleable.CirclePercentView_percent,
            0
        )
        mSmallColor = typedArray.getColor(
            R.styleable.CirclePercentView_smallColor,
            -0x504b25
        )
        mBigColor = typedArray.getColor(
            R.styleable.CirclePercentView_bigColor,
            -0x96af5f
        )
        mCenterTextSize = typedArray.getDimensionPixelSize(
            R.styleable.CirclePercentView_centerTextSize,
            50
        ).toFloat()
        mRadius = typedArray.getDimensionPixelSize(
            R.styleable.CirclePercentView_radius,
            100
        ).toFloat()
    }
}