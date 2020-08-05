package com.tainzhi.sample.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.Transformation
import com.tainzhi.sample.customview.Util.dp

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/7/31 22:32
 * @description: 第二层波浪覆盖第一层波浪上面. 第一层波浪向左波动, 第二层波浪向右波动
 * 波浪上升通过Animation实现
 * 波浪平移通过绘制实现
 *
 * [参考: 贝塞尔曲线和水波浪进度框][https://juejin.im/post/6844903529623224333]
 **/

class WaveProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    // 最外层圆形画笔
    private var circlePaint: Paint
    // 波浪画笔
    private var wavePaint: Paint
    // 波浪画笔
    private var wavePath: Path
    // 第二个波浪画笔
    private var secondWavePaint: Paint
    // 进度文字画笔
    private var textPaint: Paint

    private lateinit var bitmap: Bitmap
    private lateinit var bitmapCanvas: Canvas

    private var waveProgressAnim: WaveProgressAnim
//    private var textView: TextView

    private var waveWidth = 0f
    private var waveHeight = 0f
    private var waveNum = 0 // 波浪组的数量, 一次起伏为一组
    private var waveMovingDistance = 0f // 波浪平移距离

    private var viewSize = 0 // 重新测量后View实际的宽高
    private var defaultSize = 100.dp() // 自定义View默认的宽高

    private var percent = 0f // 进度条占比
    private var maxNum = 100f // 进度条最大值

    private var waveColor = 0 // 波浪颜色
    private var secondWaveColor = 0 // 第二层波浪颜色
    private var bgColor = 0 // 背景颜色

    private var textSize = 0 // 字体大小
    private var textColor = 0 // 字体颜色

    var onAnimationListener: OnAnimationListener? = null

    // 是否绘制第二层播放
    var isDrawSecondWave = false
    var progressNum = 0f // 可以更新的进度条数值
        set(value) {
            field = value
            percent = 0f
            waveProgressAnim.run {
                duration = 5000
                // repeatCount = Animation.INFINITE // 无限循环动画
                repeatCount = 1
                interpolator = AccelerateDecelerateInterpolator() // 平稳播放动画
            }
            startAnimation(waveProgressAnim)
        }

    init {

        context.obtainStyledAttributes(
            attrs,
            R.styleable.WaveProgressView, defStyleAttr, 0
        ).apply {
            waveWidth = getDimension(R.styleable.WaveProgressView_wave_width, 40.dp())
            waveHeight = getDimension(R.styleable.WaveProgressView_wave_height, 15.dp())
            waveColor = getColor(R.styleable.WaveProgressView_wave_color, Color.parseColor("#4ba34f") )
            secondWaveColor = getColor(R.styleable.WaveProgressView_second_wave_color, Color.parseColor("#800de6e8"))
            bgColor = getColor(R.styleable.WaveProgressView_wave_bg_color, Color.GRAY)
            textSize = getDimensionPixelSize(R.styleable.WaveProgressView_text_size, 100)
            textColor = getColor(R.styleable.WaveProgressView_text_color, Color.WHITE)

        }.recycle()

        wavePath = Path()

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
        textPaint = Paint().apply {
            color = textColor
            textSize = this@WaveProgressView.textSize.toFloat()
            isAntiAlias = true
            isDither = true
        }

        waveProgressAnim = WaveProgressAnim().apply {
            setAnimationListener(object: Animation.AnimationListener {
                override fun onAnimationRepeat(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                }

                override fun onAnimationStart(animation: Animation?) {
                }
            })
        }
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = measureSize(defaultSize.toInt(), widthMeasureSpec)
        val height = measureSize(defaultSize.toInt(), heightMeasureSpec)
        viewSize = Math.min(width, height)
        setMeasuredDimension(viewSize, viewSize)
        waveNum = (viewSize / waveWidth / 2).toInt()
    }

    private fun measureSize(defaultSize: Int, measureSpec: Int): Int {
        var result = defaultSize
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize)
        }
        return result
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        bitmap = Bitmap.createBitmap(viewSize, viewSize, Bitmap.Config.ARGB_8888)
        bitmapCanvas = Canvas(bitmap)

        bitmapCanvas.drawCircle((viewSize/2).toFloat(), viewSize/2f, viewSize/2f, circlePaint)
        bitmapCanvas.drawPath(getWavePath(), wavePaint)
        if (isDrawSecondWave) {
            bitmapCanvas.drawPath(getSecondWavePath(), secondWavePaint)
        }
        
        // 把bitmap绘制到canvas上
        canvas.drawBitmap(bitmap, 0f, 0f, null)


        val text = String.format("%.2f%%", percent * 100)
        val textWidth = textPaint.measureText(text)
        val fontMetrics = textPaint.fontMetrics
        val baseLine = viewSize / 2 + (fontMetrics.ascent -   fontMetrics.descent) / 2 - fontMetrics.ascent
        canvas.drawText(text,viewSize / 2 - textWidth / 2, baseLine, textPaint)
    }

    private fun getWavePath(): Path {
        // 波浪高度越来越低
        var changeWaveHeight = (1 - percent) * waveHeight
//        onAnimationListener?.let {
//
//            if ((it.howToChangeWaveHeight(percent, waveHeight).toInt() == 0) && (percent < 1)) {
//                changeWaveHeight = waveHeight
//            } else {
//                it.howToChangeWaveHeight(percent, waveHeight)
//            }
//        }

        return wavePath.apply {
            reset()
            // 移动到右上方
            moveTo(viewSize.toFloat(), (1 - percent) * viewSize)
            // 移动到右下方
            lineTo(viewSize.toFloat(), viewSize.toFloat())
            // 移动到左下方
            lineTo(0f, viewSize.toFloat())
            // 移动到左上方
            lineTo(-waveMovingDistance, (1 - percent) * viewSize)

            // 绘制波浪曲线
            for (i in 0 until waveNum * 2) {
                rQuadTo(waveWidth/2, changeWaveHeight, waveWidth, 0f)
                rQuadTo(waveWidth/2, -changeWaveHeight, waveWidth, 0f)
            }

            // path封闭起来
            close()
        }
    }

    private fun getSecondWavePath() : Path{
        var changeWaveHeight = (1 - percent) * waveHeight
        // ?.let {
        //     changeWaveHeight = if (it.howToChangeWaveHeight(percent, waveHeight).toInt() == 0 && percent < 1) waveHeight else it.howToChangeWaveHeight(percent, waveHeight)
        // }

        return wavePath.apply {
            reset()
            // 移动到左上方
            moveTo(0f, (1-percent) * viewSize)
            // 移动到左下方
            lineTo(0f, viewSize.toFloat())
            // 移动到右下方
            lineTo(viewSize.toFloat(), viewSize.toFloat())
            lineTo(viewSize + waveMovingDistance, (1-percent) * viewSize)

            for (i in 0 until waveNum * 2) {
                rQuadTo(-waveWidth/2, changeWaveHeight, -waveWidth, 0f)
                rQuadTo(-waveWidth/2, -changeWaveHeight, -waveWidth, 0f)
            }
            close()
        }
    }

    interface OnAnimationListener {

        /**
         * 如何处理要显示的进度数字
         * @param interpolatedTime 从0渐变到1, 到1时结束动画
         * @param updateNum 角度条数值
         * @param maxNum 进度条最大值
         */
        fun howToChangeText(interpolatedTime: Float, updateNum: Float, maxNum: Float): String

        /**
         * 如何处理波浪高度
         * @param percent 进度占比
         * @param waveHeight 波浪高度
         */
        fun howToChangeWaveHeight(percent: Float, waveHeight: Float):  Float
    }

    inner class WaveProgressAnim: Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
            super.applyTransformation(interpolatedTime, t)
            if (percent < progressNum / maxNum) {
                percent = interpolatedTime * progressNum / maxNum
            }
            waveMovingDistance = interpolatedTime * waveNum * waveWidth * 2
            postInvalidate()
        }
    }

}