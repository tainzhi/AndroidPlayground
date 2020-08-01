package com.tainzhi.sample.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.TextView
import com.tainzhi.sample.customview.Util.dp

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/7/31 22:32
 * @description: 第二层波浪覆盖第一层波浪上面
 **/

class WaveProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 最外层圆形画笔
    private lateinit var circlePaint: Paint
    // 波浪画笔
    private lateinit var wavePaint: Paint
    // 波浪画笔
    private lateinit var wavePath: Path
    // 第二个波浪画笔
    private lateinit var secondWavePaint: Paint

    private lateinit var bitmap: Bitmap
    private lateinit var bitmapCanvas: Canvas

    private lateinit var waveProgressAnim: WaveProgressAnim
    private lateinit var textView: TextView
    private lateinit var onAnimationListener: OnAnimationListener

    private var waveWidth = 0f
    private var waveHeight = 0f
    private var waveNum = 0 // 波浪组的数量, 一次起伏为一组
    private var waveMovingDistance = 0f // 波浪平移距离

    private var viewSize = 0 // 重新测量后View实际的宽高
    private var defaultSize = 100.dp() // 自定义View默认的宽高

    private var percent = 0f // 进度条占比
    private var progressNum = 0f // 可以更新的进度条数值
    private var maxNum = 100f // 进度条最大值

    private var waveColor = 0 // 波浪颜色
    private var secondWaveColor = 0 // 第二层波浪颜色
    private var bgColor = 0 // 背景颜色

    private var isDrawSecondWave = false

    init {

        context.obtainStyledAttributes(
            attrs,
            R.styleable.WaveProgressView, defStyleAttr, 0
        ).apply {
            waveWidth = getDimension(R.styleable.WaveProgressView_wave_width, 25.dp())
            waveHeight = getDimension(R.styleable.WaveProgressView_wave_height, 5.dp())
            waveColor = getColor(R.styleable.WaveProgressView_wave_color, Color.GREEN)
            secondWaveColor = getColor(R.styleable.WaveProgressView_second_wave_color, Color.BLUE)
            bgColor = getColor(R.styleable.WaveProgressView_wave_bg_color, Color.GRAY)
        }.recycle()

        waveNum = (defaultSize / waveWidth / 2).toInt()

        wavePaint = Paint().apply {
            color = waveColor
            isAntiAlias = true // 抗锯齿
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        }
        secondWavePaint = Paint().apply {
            color = secondWaveColor
            isAntiAlias = true
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP) // 覆盖在第一层波浪上面, 切要半透明
        }
        circlePaint = Paint().apply {
            color = bgColor
            isAntiAlias = true
        }
        waveProgressAnim = WaveProgressAnim().apply {
            setAnimationListener(object: Animation.AnimationListener {

            })
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measureSize(defaultSize, widthMeasureSpec)
        val height = measureSize(defaultSize, heightMeasureSpec)
        viewSize = Math.min(width, height)
        setMeasuredDimension(viewSize, viewSize)
        waveNum = (viewSize / waveWidth / 2).toInt()
    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int {
        var result = defaultSize
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize)
        }
        return result
    }

    interface OnAnimationListener {

        /**
         * 如何处理要显示的进度数字
         * @param interpolatedTime 从0渐变到1, 到1时结束动画
         * @param updateNum 角度条数值
         * @param maxNUm 进度条最大值
         */
        fun howToChangeText(interpolatedTime: Float, updateNum: Float, maxNum: Float): String

        /**
         * 如何处理波浪高度
         * @param percent 进度占比
         * @param waveHeight 波浪高度
         */
        fun howToChangeWaveHeight(percent: Float, waveHeight: Float):  Float
    }

    class WaveProgressAnim: Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            if (percent < progressNum / maxNum ) {

            }
        }
    }

}