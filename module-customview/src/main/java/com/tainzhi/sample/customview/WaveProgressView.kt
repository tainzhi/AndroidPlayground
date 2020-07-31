package com.tainzhi.sample.customview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.TextView

/**
 * @author:      tainzhi
 * @mail:        qfq61@qq.com
 * @date:        2020/7/31 22:32
 * @description:
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
    private lateinit var secondWavePath: Path

    private lateinit var bitmap: Bitmap
    private lateinit var bitmapCanvas: Canvas

    private lateinit var waveProgressAnim: WaveProgressAnim
    private lateinit var textView: TextView
    private lateinit var onAni

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