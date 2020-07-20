package com.tainzhi.sample.customview

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.tainzhi.sample.customview.Util.dp

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/7/16 16:39
 * @description:
 **/

class LevelProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var progress: Int = 50
        set(value) {
            invalidate()
            field = value
        }

    private var progressBarBackground: Int

    // 该空间的宽和高
    private var _width: Int = 0
    private var _height: Int = 0

    private val defaultProgressBarHeight: Float = 12.dp()
    private var progressBarHeight: Float
    private var progressBarWidth: Float = 0f
    private val progressBarRect = RectF()

    private val defaultLevelIconWidth: Float = 48.dp()
    private var levelIconWidth: Float

    // 5个icon的中心X坐标
    private var levelIconsCenterX = 0f

    // 5个icon的中心y坐标
    private var levelIconsCenterY = FloatArray(5)
    private var levelIconDrawable = arrayOfNulls<Drawable>(5)
    private var levellIconBitmap = arrayOfNulls<Bitmap>(5)

    // 5个icon的绘制位置
    private var levelIconRectF = arrayOfNulls<RectF>(5)
    private val levelIconSrcRect = Rect()

    // 进度图片
    private var progressDrawable: Drawable?
    private lateinit var progressBitmap: Bitmap

    // indicator
    private var indicatorDrawable: Drawable?
    private lateinit var indicatorBitmap: Bitmap
    private var indicatorWidth: Float = 16.dp()
    private var indicatorHeight: Float = 24.dp()
    private var indicatorPosition: Float = 30.dp()

    init {

        context.obtainStyledAttributes(
            attrs,
            R.styleable.LevelProgressBar, defStyleAttr, 0
        ).apply {
            progressBarHeight = getDimension(
                R.styleable.LevelProgressBar_progressBarHeight,
                defaultProgressBarHeight
            )
            progressBarBackground = getColor(
                R.styleable.LevelProgressBar_progressBarBackground,
                ContextCompat.getColor(context, R.color.levelProgressBarBackGround)
            )
            levelIconWidth =
                getDimension(R.styleable.LevelProgressBar_levelIconWidth, defaultLevelIconWidth)
            levelIconDrawable[0] = getDrawable(R.styleable.LevelProgressBar_firstLevelIcon)
            levelIconDrawable[1] = getDrawable(R.styleable.LevelProgressBar_secondLevelIcon)
            levelIconDrawable[2] = getDrawable(R.styleable.LevelProgressBar_thirdLevelIcon)
            levelIconDrawable[3] = getDrawable(R.styleable.LevelProgressBar_fourthLevelIcon)
            levelIconDrawable[4] = getDrawable(R.styleable.LevelProgressBar_fifthLevelIcon)
            progressDrawable = getDrawable(R.styleable.LevelProgressBar_progressBarProgressDrawable)

            indicatorDrawable = getDrawable(R.styleable.LevelProgressBar_indicatorDrawable)
            indicatorWidth = getDimension(R.styleable.LevelProgressBar_indicatorWidth, indicatorWidth)
            indicatorHeight = getDimension(R.styleable.LevelProgressBar_indicatorHeight, indicatorHeight)
            indicatorPosition = getDimension(R.styleable.LevelProgressBar_indicatorPosition, indicatorPosition)

        }.recycle()

        for (i in 0 until 5) {
            initLevelIconBitmap(i)
        }

        initProgressBitmap()

        initIndicatorBitmap()

    }

    private fun initLevelIconBitmap(index: Int) {
        val bitmap = Bitmap.createBitmap(
            levelIconWidth.toInt(),
            levelIconWidth.toInt(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        levelIconDrawable[index]?.setBounds(0, 0, canvas.width, canvas.height)
        levelIconDrawable[index]?.draw(canvas)
        levellIconBitmap[index] = bitmap
    }

    private fun initProgressBitmap() {
        val height = progressBarHeight
        val width: Float =
            progressBarHeight * progressDrawable!!.intrinsicWidth / progressDrawable!!.intrinsicHeight
        val bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        progressDrawable?.setBounds(0, 0, canvas.width, canvas.height)
        progressDrawable?.draw(canvas)
        progressBitmap = bitmap
    }

    private fun initIndicatorBitmap() {
        val bitmap = Bitmap.createBitmap(indicatorWidth.toInt(), indicatorHeight.toInt(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        indicatorDrawable?.setBounds(0, 0, canvas.width, canvas.height)
        indicatorDrawable?.draw(canvas)
        indicatorBitmap = bitmap
    }

    val paint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        color = progressBarBackground
        isAntiAlias = true
        style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        _width = widthSize

        _height = (indicatorHeight / 2 + indicatorPosition + levelIconWidth / 2).toInt()
        setMeasuredDimension(_width, _height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val halfIconWidth = levelIconWidth / 2f
        levelIconsCenterX = indicatorPosition + indicatorHeight / 2
        // 中间有4个间隔
        val interval = (_width - halfIconWidth * 2) / 4
        levelIconsCenterY[0] = levelIconWidth / 2
        levelIconsCenterY[1] = levelIconsCenterY[0] + interval
        levelIconsCenterY[2] = levelIconsCenterY[1] + interval
        levelIconsCenterY[3] = levelIconsCenterY[2] + interval
        levelIconsCenterY[4] = _width - halfIconWidth

        progressBarWidth = width - paddingEnd - levelIconWidth
        progressBarRect.set(
            paddingStart + halfIconWidth,
            levelIconsCenterX - progressBarHeight / 2f,
            _width - paddingEnd - halfIconWidth,
            levelIconsCenterX + progressBarHeight / 2f
        )

        levelIconSrcRect.set(0, 0, levellIconBitmap[0]!!.width, levellIconBitmap[0]!!.height)
        for (i in 0 until 5) {
            levelIconRectF[i] = RectF(
                levelIconsCenterY[i] - halfIconWidth,
                levelIconsCenterX - halfIconWidth,
                levelIconsCenterY[i] + halfIconWidth,
                levelIconsCenterX + halfIconWidth
            )
        }

    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(progressBarRect, paint)

        // progressDrawable绘制
        val progressDrawableWidth = progressBarWidth * progress / 100
        var progressDrawableStart = levelIconWidth / 2
        val progressDrawableTop = levelIconsCenterX - (progressBarHeight / 2)
        // 先画出整数个progressDrawable
        for (i in 0 until (progressDrawableWidth / progressBitmap.width).toInt()) {
            canvas.drawBitmap(progressBitmap, progressDrawableStart, progressDrawableTop, paint)
            progressDrawableStart += progressBitmap.width
        }
        canvas.drawBitmap(
            progressBitmap,
            Rect(
                0,
                0,
                (levelIconWidth / 2 +  progressDrawableWidth - progressDrawableStart).toInt(),
                progressBarHeight.toInt()
            ),
            RectF(
                progressDrawableStart,
                progressDrawableTop,
                levelIconWidth / 2 +  progressDrawableWidth,
                progressDrawableTop + progressBarHeight
            )
            , paint
        )

        for (i in 0 until 5) {
            canvas.drawBitmap(levellIconBitmap[i]!!, levelIconSrcRect, levelIconRectF[i]!!, paint)
        }

        canvas.drawBitmap(
            indicatorBitmap,
            Rect(0, 0, indicatorBitmap!!.width, indicatorBitmap!!.height),
            RectF(
                levelIconWidth / 2 + progressDrawableWidth - (indicatorBitmap!!.width) / 2,
                0f,
                indicatorBitmap!!.width / 2 + progressDrawableWidth + levelIconWidth / 2,
                indicatorBitmap!!.height.toFloat()
            ),
            paint
        )
    }
}