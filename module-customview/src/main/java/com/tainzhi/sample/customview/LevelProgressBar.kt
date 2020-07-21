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

    var levelPoints = ArrayList<Int>()
        set(value) {
            invalidate()
            field = value
        }
    var point = 0
        set(value) {
            post { computeProgress() }
            postInvalidate()
            field = value
        }

    private var progressBarBackground: Int

    // 该空间的宽和高
    private var _width: Int = 0
    private var _height: Int = 0

    // 长进度条背景的宽和高
    private var progressBarHeight: Float
    private var progressBarWidth: Float = 0f
    private var progressBarStartX = 0f
    private var progressBarEndY = 0f
    private val progressBarRect = RectF()

    // 进度条 进度图片
    private var progressDrawable: Drawable?
    private lateinit var progressBitmap: Bitmap

    // 等级图片 宽高相等
    private var levelIconWidth: Float

    // 5个icon的中心y坐标
    private var levelIconsCenterY = 0f

    // 5个icon的中心x坐标
    private var levelIconsCenterX = FloatArray(5)
    private var levelIconDrawable = arrayOfNulls<Drawable>(5)
    private var levellIconBitmap = arrayOfNulls<Bitmap>(5)

    // 5个icon的绘制位置
    private var levelIconRectF = arrayOfNulls<RectF>(5)
    private val levelIconSrcRect = Rect()


    // indicator
    private var indicatorDrawable: Drawable?
    private lateinit var indicatorBitmap: Bitmap
    private var indicatorWidth: Float = 16.dp()
    private var indicatorHeight: Float = 24.dp()
    private var indicatorPosition: Float = 30.dp()

    // text距离progressBar垂直中心线的距离
    private val textPadding = 5 // fuck, 绘制text总有边界没有绘制出来
    private var textPosition = 0
    private var textSize: Int = 0
    private var textBounds = arrayOf(Rect(), Rect(), Rect(), Rect(), Rect())
    var levelTextSequences = arrayOf<String>(
        "新晋CEO",
        "进阶CEO",
        "王牌CEO",
        "金牌CEO",
        "至尊CEO"
    )

    private var pointTextSize: Int = 0
    private var pointTextPostion = 0
    private var pointTextColor = 0
    private var pointTextBound = Rect()

    init {

        context.obtainStyledAttributes(
            attrs,
            R.styleable.LevelProgressBar, defStyleAttr, 0
        ).apply {
            progressBarHeight =
                getDimension(R.styleable.LevelProgressBar_progressBarHeight, 12.dp())
            progressBarBackground = getColor(
                R.styleable.LevelProgressBar_progressBarBackground,
                ContextCompat.getColor(context, R.color.levelProgressBarBackGround)
            )
            levelIconWidth =
                getDimension(R.styleable.LevelProgressBar_levelIconWidth, 48.dp())
            levelIconDrawable[0] = getDrawable(R.styleable.LevelProgressBar_firstLevelIcon)
            levelIconDrawable[1] = getDrawable(R.styleable.LevelProgressBar_secondLevelIcon)
            levelIconDrawable[2] = getDrawable(R.styleable.LevelProgressBar_thirdLevelIcon)
            levelIconDrawable[3] = getDrawable(R.styleable.LevelProgressBar_fourthLevelIcon)
            levelIconDrawable[4] = getDrawable(R.styleable.LevelProgressBar_fifthLevelIcon)
            progressDrawable = getDrawable(R.styleable.LevelProgressBar_progressBarProgressDrawable)

            indicatorDrawable = getDrawable(R.styleable.LevelProgressBar_indicatorDrawable)
            indicatorWidth =
                getDimension(R.styleable.LevelProgressBar_indicatorWidth, indicatorWidth)
            indicatorHeight =
                getDimension(R.styleable.LevelProgressBar_indicatorHeight, indicatorHeight)
            indicatorPosition =
                getDimension(R.styleable.LevelProgressBar_indicatorPosition, indicatorPosition)

            textSize = getDimensionPixelSize(R.styleable.LevelProgressBar_textSize, 13.dp().toInt())
            textPosition =
                getDimensionPixelSize(R.styleable.LevelProgressBar_textPosition, 16.dp().toInt())

            pointTextSize =
                getDimensionPixelSize(R.styleable.LevelProgressBar_pointTextSize, 8.dp().toInt())
            pointTextColor =
                getColor(R.styleable.LevelProgressBar_pointTextColor, Color.parseColor("#000000"))
            pointTextPostion = getDimensionPixelSize(
                R.styleable.LevelProgressBar_pointTextPosition,
                20.dp().toInt()
            )

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

    private val strokePaint = Paint().apply {
        strokeCap = Paint.Cap.ROUND
        color = progressBarBackground
        isAntiAlias = true
        style = Paint.Style.FILL

    }

    private val textPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = this@LevelProgressBar.textSize.toFloat()
        color = Color.parseColor("#8a8f96")

        for (i in 0 until 5) {
            this.getTextBounds(
                levelTextSequences[i],
                0,
                levelTextSequences[i].length,
                textBounds[i]
            )
        }

        this.getTextBounds("0", 0, 1, pointTextBound)
    }

    private val pointTextPaint = Paint().apply {
        isAntiAlias = true
        textAlign = Paint.Align.CENTER
        textSize = this@LevelProgressBar.pointTextSize.toFloat()
        color = pointTextColor
        this.getTextBounds("0", 0, 1, pointTextBound)
    }

    private fun computeProgress() {
        // 求出所在等级
        var pointLevel = 0
        if (levelPoints.size == 0) return
        for (i in 0 until levelPoints.size) {
            if (point >= levelPoints[i]) {
                pointLevel = i
            }
        }
        // 再该等级页下一等级之间求出所占百分比
        if (pointLevel < 4) {
            val percent =
                (point - levelPoints[pointLevel]) * 1f / (levelPoints[pointLevel + 1] - levelPoints[pointLevel])
            val paddingProgressBarStart = (levelIconsCenterX[pointLevel] - levelIconsCenterX[0]) +
                    percent * (levelIconsCenterX[pointLevel + 1] - levelIconsCenterX[pointLevel])
            progress = (paddingProgressBarStart * 100 / progressBarWidth).toInt()
        } else {
            // 已经是最高等级
            progress = 100
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        _width = widthSize
        _height = (indicatorHeight / 2 + indicatorPosition
                + textPosition
                + pointTextPostion
                + pointTextBound.height() / 2
                + textPadding
                ).toInt()
        setMeasuredDimension(_width, _height)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val halfIconWidth = levelIconWidth / 2f
        levelIconsCenterY = indicatorPosition + indicatorHeight / 2
        // 尽量使得text和icon都能放置下
        progressBarStartX =
            Math.max(levelIconWidth / 2, textBounds[0].width().toFloat() / 2) + textPadding
        progressBarEndY = Math.min(
            _width.toFloat() - levelIconWidth / 2,
            _width.toFloat() - textBounds[4].width() / 2
        ) - textPadding
//        progressBarStartX = textBounds[0].width() / 2.toFloat()
//        progressBarEndY = _width.toFloat() - textBounds[4].width() / 2
        // 中间有4个间隔
        val interval = (progressBarEndY - progressBarStartX) / 4
        levelIconsCenterX[0] = progressBarStartX
        levelIconsCenterX[1] = levelIconsCenterX[0] + interval
        levelIconsCenterX[2] = levelIconsCenterX[1] + interval
        levelIconsCenterX[3] = levelIconsCenterX[2] + interval
        levelIconsCenterX[4] = progressBarEndY

        progressBarWidth = progressBarEndY - progressBarStartX
        progressBarRect.set(
            progressBarStartX,
            levelIconsCenterY - progressBarHeight / 2f,
            progressBarEndY,
            levelIconsCenterY + progressBarHeight / 2f
        )

        levelIconSrcRect.set(0, 0, levellIconBitmap[0]!!.width, levellIconBitmap[0]!!.height)
        for (i in 0 until 5) {
            levelIconRectF[i] = RectF(
                levelIconsCenterX[i] - halfIconWidth,
                levelIconsCenterY - halfIconWidth,
                levelIconsCenterX[i] + halfIconWidth,
                levelIconsCenterY + halfIconWidth
            )
        }

    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(progressBarRect, strokePaint)

        // progressDrawable绘制
        val progressDrawableWidth = progressBarWidth * progress / 100
        var progressDrawableStart = progressBarStartX
        val progressDrawableTop = levelIconsCenterY - (progressBarHeight / 2)
        // 先画出整数个progressDrawable
        for (i in 0 until (progressDrawableWidth / progressBitmap.width).toInt()) {
            canvas.drawBitmap(
                progressBitmap,
                progressDrawableStart,
                progressDrawableTop,
                strokePaint
            )
            progressDrawableStart += progressBitmap.width
        }
        canvas.drawBitmap(
            progressBitmap,
            Rect(
                0,
                0,
                (progressDrawableWidth + progressBarStartX - progressDrawableStart).toInt(),
                progressBarHeight.toInt()
            ),
            RectF(
                progressDrawableStart,
                progressDrawableTop,
                progressDrawableWidth + progressBarStartX + 1,
                progressDrawableTop + progressBarHeight
            )
            , strokePaint
        )

        for (i in 0 until 5) {
            canvas.drawBitmap(levellIconBitmap[i]!!, levelIconSrcRect, levelIconRectF[i]!!, strokePaint)
        }

        canvas.drawBitmap(
            indicatorBitmap,
            Rect(0, 0, indicatorBitmap!!.width, indicatorBitmap!!.height),
            RectF(
                progressBarStartX + progressDrawableWidth - (indicatorBitmap!!.width) / 2,
                0f,
                progressBarStartX + progressDrawableWidth + indicatorBitmap!!.width / 2,
                indicatorBitmap!!.height.toFloat()
            ),
            strokePaint
        )

        val textY =
            levelIconsCenterY + textPosition + textBounds[0].height() / 2
        for (i in 0 until 5) {
            canvas.drawText(levelTextSequences[i], levelIconsCenterX[i], textY, textPaint)
        }

        val pointTextY =
            levelIconsCenterY + textPosition + pointTextPostion + pointTextBound.height() / 2
        for (i in 0 until levelPoints.size) {
            canvas.drawText(
                levelPoints[i].toString(),
                levelIconsCenterX[i],
                pointTextY,
                pointTextPaint
            )
        }
    }
}