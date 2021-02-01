package com.tainzhi.sample.customview.widget

import android.animation.*
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.tainzhi.android.common.util.screenHeight
import com.tainzhi.android.common.util.screenWidth
import com.tainzhi.sample.customview.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * File:     Add2ShopCarAnim
 * Author:   tainzhi
 * Created:  2021/2/1 14:21
 * Mail:     QFQ61@qq.com
 * Description:
 */
class Add2ShopCarAnim(
    val context: Context,
    val startView: View,
    val startViewUrl: String
) {
    var add2ShopCarAnimListener: Add2ShopCarAnimListener? = null
    var animTime = 1000

    private val rootView: ViewGroup = (context as Activity).window.decorView as ViewGroup

    // 添加一个透明层覆盖住窗口上, 包括statusbar
    private val animLayout = FrameLayout(context).apply {
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
        )
        id = R.id.add_2_shop_car_anim
        setBackgroundResource(android.R.color.transparent)
    }
    private val circleImageView: CircleImageView
    private val endView: View = (context as ShopCarAnchor).shopCarView()
    private val startLocation: PointF
    private val endLocation: PointF

    private var scale: Double = 0.0

    init {
        val location = IntArray(2)
        startView.getLocationInWindow(location)
        startLocation = PointF(location[0].toFloat(), location[1].toFloat())
        endView.getLocationInWindow(location)
        endLocation = PointF(location[0].toFloat(), location[1].toFloat())

        val minWidth = min(startView.measuredWidth, startView.measuredHeight)
        circleImageView = CircleImageView(context).apply {
            borderColor = Color.WHITE
            borderWidth = 3
            layoutParams = FrameLayout.LayoutParams(
                minWidth, minWidth
            )
        }
    }

    fun start() {
        Glide.with(context)
            .load(startViewUrl)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    setAnim()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    setAnim()
                    return false
                }
            })
            .into(circleImageView)
    }

    private fun setAnim() {
        rootView.addView(animLayout)
        animLayout.addView(circleImageView)
        circleImageView.run {
            translationX = startLocation.x
            translationY = startLocation.y
        }

        // startView经过缩小后, 移动到endView
        val adjustEndLocation = PointF(
            endLocation.x - startView.width / 2 + endView.width / 2,
            endLocation.y - startView.height / 2 + endView.height / 2,
        )
        // 起始点和调整后的终点之间的距离  / 屏幕对角线的距离
        scale = sqrt(
            (startLocation.x - adjustEndLocation.x).toDouble().pow(2) +
                    (startLocation.y - adjustEndLocation.y).toDouble().pow(2)
                    ) / sqrt(
                          (context as Activity).screenWidth().toDouble().pow(2) +
                                (context as Activity).screenHeight().toDouble().pow(2)
                    )
        val controlPoint = PointF(
            min(startLocation.x, adjustEndLocation.x),
            min(startLocation.y, adjustEndLocation.y)
        )

        val translateAnimator = ValueAnimator.ofObject(
            BezierTypeEvaluator(controlPoint),
            startLocation,
            adjustEndLocation
        ).apply {
            interpolator = AccelerateInterpolator()
            addUpdateListener { value ->
                val point = value.animatedValue as PointF
                circleImageView.x = point.x
                circleImageView.y = point.y
            }
        }
        val scaleXAnimator = ObjectAnimator.ofFloat(circleImageView, "scaleX", 1.0f, 0.1f)
        val scaleYAnimator = ObjectAnimator.ofFloat(circleImageView, "scaleY", 1.0f, 0.1f)
        val animatorSet = AnimatorSet().apply {
            duration = if (animTime * scale < 1000) 1000 else (animTime * scale).toLong()
            playTogether(translateAnimator, scaleXAnimator, scaleYAnimator)
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    add2ShopCarAnimListener?.onAnimStart()
                }

                override fun onAnimationEnd(animation: Animator?) {
                    add2ShopCarAnimListener?.onAnimEnd()
                    startEndViewAnim()
                    rootView.post {
                        rootView.removeView(animLayout)
                    }
                }

                override fun onAnimationCancel(animation: Animator?) {
                }

                override fun onAnimationRepeat(animation: Animator?) {
                }

            })
        }
        animatorSet.start()
    }

    private fun startEndViewAnim() {
        endView.startAnimation(
            TranslateAnimation(0f, 0f, 20f, 0f).apply {
                interpolator = BounceInterpolator()
                duration = 700
            }
        )
    }
}

class BezierTypeEvaluator(private val mControlPoint: PointF) :
    TypeEvaluator<PointF> {
    override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
        val pointCur = PointF()
        pointCur.x =
            (1 - fraction) * (1 - fraction) * startValue.x + 2 * fraction * (1 - fraction) * mControlPoint.x + fraction * fraction * endValue.x
        pointCur.y =
            (1 - fraction) * (1 - fraction) * startValue.y + 2 * fraction * (1 - fraction) * mControlPoint.y + fraction * fraction * endValue.y
        return pointCur
    }
}

// 用于获取Toolbar等, 其他位置的ShopCar图标
interface ShopCarAnchor {
    fun shopCarView(): View
}

interface Add2ShopCarAnimListener {
    fun onAnimStart()
    fun onAnimEnd()
}
