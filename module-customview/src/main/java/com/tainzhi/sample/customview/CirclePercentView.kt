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
 * Description: 圆环进度条
 * 先画出大圆, 再画出扇形, 然后小圆(和大圆同色)覆盖扇形(展示位圆环), 最后绘制出数字
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

    init {
        context.obtainStyledAttributes(
            attrs,
            R.styleable.CirclePercentView, defStyleAttr, 0
        ).apply {
            mStripeWidth = getDimension(R.styleable.CirclePercentView_stripWidth, 30f)
            mCurrentPercent = getInteger(
                R.styleable.CirclePercentView_percent,
                0
            )
            mSmallColor = getColor(
                R.styleable.CirclePercentView_smallColor,
                -0x504b25
            )
            mBigColor = getColor(
                R.styleable.CirclePercentView_bigColor,
                -0x96af5f
            )
            mCenterTextSize = getDimensionPixelSize(
                R.styleable.CirclePercentView_centerTextSize,
                50
            ).toFloat()
            mRadius = getDimensionPixelSize(
                R.styleable.CirclePercentView_radius,
                100
            ).toFloat()
        }.recycle()
    }

    private val bigCirclePaint = Paint().apply {
        isAntiAlias = true
        color = mBigColor
    }

    private val sectorPaint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        color = mSmallColor
    }

    private val smallCirclePaint = Paint().apply {
        isAntiAlias = true
        color = mBigColor
    }

    private val textPaint = Paint().apply {
        textSize = mCenterTextSize
        color = Color.WHITE
    }

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
        // 进度是从0-100, 而弧度是从0-360故放大系数为 3.6
        mEndAngle = (mCurrentPercent * 3.6).toInt()
        // 绘制大圆
        canvas.drawCircle(centerX, centerY, mRadius, bigCirclePaint)
    
        // 绘制扇形
        // 大坑, 必须在onMeasure()获取mWidth和mHeight后, 再设置rect
        // rect为drawArc绘制的圆环所在范围
        val rect = RectF(0F, 0F, mWidth.toFloat(), mHeight.toFloat())
        canvas.drawArc(rect, 270f, mEndAngle.toFloat(), true, sectorPaint)
        // 绘制小圆
        canvas.drawCircle(centerX, centerY, mRadius - mStripeWidth, smallCirclePaint)
        val text = "$mCurrentPercent%"
        val textLength = textPaint.measureText(text)
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
            // 模拟类似依次变化(进度增加)的效果
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

}