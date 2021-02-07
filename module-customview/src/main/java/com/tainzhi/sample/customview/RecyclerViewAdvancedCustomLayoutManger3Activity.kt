package com.tainzhi.sample.customview

import android.content.Context
import android.graphics.Rect
import android.hardware.SensorManager
import android.util.AttributeSet
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewConfiguration
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tainzhi.android.common.base.ui.BaseViewBindingActivity
import com.tainzhi.sample.customview.databinding.ActivityCustomLayoutManger3Binding

class RecyclerViewAdvancedCustomLayoutManger3Activity :
    BaseViewBindingActivity<ActivityCustomLayoutManger3Binding>() {
    override fun initView() {
        mBinding.recyclerV.run {
            layoutManager = AdvancedCustomLayoutManger3(context)
            adapter = ItemImageDecorationAdapter(generateFakeData())
        }
    }

    private fun generateFakeData(): List<String> {
        val datas = mutableListOf<String>()
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic3.16pic.com%2F00%2F06%2F94%2F16pic_694190_b.jpg&refer=http%3A%2F%2Fpic3.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=5367798c13d54b8594d54d6de37db8ed")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic32.nipic.com%2F20130816%2F8952533_165406191000_2.jpg&refer=http%3A%2F%2Fpic32.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=747ef63881939d9fa1d6dadf5989d0e8")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic3.16pic.com%2F00%2F06%2F94%2F16pic_694190_b.jpg&refer=http%3A%2F%2Fpic3.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=5367798c13d54b8594d54d6de37db8ed")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic14.nipic.com%2F20110614%2F3320946_062640365000_2.jpg&refer=http%3A%2F%2Fpic14.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=373a54a2f03204545dac1df6f7afde5a")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdpic.tiankong.com%2Fm9%2Fa7%2FQJ8572504715.jpg&refer=http%3A%2F%2Fdpic.tiankong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=a6cd7ad9e935371507c18c537fb8b762")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic115.nipic.com%2Ffile%2F20161122%2F23310207_083231795037_2.jpg&refer=http%3A%2F%2Fpic115.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=3054417e71b79f45a091f32fcf6904a0")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.weixinyunduan.com%2Fups%2F2015%2F05%2F248202%2F790de15c56b6b6c8cab38f578d26f63a.jpg&refer=http%3A%2F%2Fwww.weixinyunduan.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=c05c764e13a209b39d7c07d2d4435e8b")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdpic.tiankong.com%2Fr8%2F0z%2FQJ8297824309.jpg&refer=http%3A%2F%2Fdpic.tiankong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=9c1ee92d55d56b2c0f9f952ca7ed8ee8")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic165.nipic.com%2Ffile%2F20180514%2F10495910_141758111031_2.jpg&refer=http%3A%2F%2Fpic165.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=3f31bce050bd3db0fe5cb1ace9d0d461")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic17.nipic.com%2F20111011%2F2572670_114812065000_2.jpg&refer=http%3A%2F%2Fpic17.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=5d9e44ad8478c318718ab218ccb7413e")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic3.16pic.com%2F00%2F06%2F94%2F16pic_694190_b.jpg&refer=http%3A%2F%2Fpic3.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=5367798c13d54b8594d54d6de37db8ed")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic32.nipic.com%2F20130816%2F8952533_165406191000_2.jpg&refer=http%3A%2F%2Fpic32.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=747ef63881939d9fa1d6dadf5989d0e8")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic14.nipic.com%2F20110614%2F3320946_062640365000_2.jpg&refer=http%3A%2F%2Fpic14.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=373a54a2f03204545dac1df6f7afde5a")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdpic.tiankong.com%2Fm9%2Fa7%2FQJ8572504715.jpg&refer=http%3A%2F%2Fdpic.tiankong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=a6cd7ad9e935371507c18c537fb8b762")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic115.nipic.com%2Ffile%2F20161122%2F23310207_083231795037_2.jpg&refer=http%3A%2F%2Fpic115.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=3054417e71b79f45a091f32fcf6904a0")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.weixinyunduan.com%2Fups%2F2015%2F05%2F248202%2F790de15c56b6b6c8cab38f578d26f63a.jpg&refer=http%3A%2F%2Fwww.weixinyunduan.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=c05c764e13a209b39d7c07d2d4435e8b")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fdpic.tiankong.com%2Fr8%2F0z%2FQJ8297824309.jpg&refer=http%3A%2F%2Fdpic.tiankong.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=9c1ee92d55d56b2c0f9f952ca7ed8ee8")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic165.nipic.com%2Ffile%2F20180514%2F10495910_141758111031_2.jpg&refer=http%3A%2F%2Fpic165.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=3f31bce050bd3db0fe5cb1ace9d0d461")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic17.nipic.com%2F20111011%2F2572670_114812065000_2.jpg&refer=http%3A%2F%2Fpic17.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=5d9e44ad8478c318718ab218ccb7413e")
        return datas
    }
}

class ItemImageDecorationAdapter(datas: List<String>) : BaseQuickAdapter<String, BaseViewHolder>(
    R.layout.item_image,
    datas as MutableList<String>
) {
    override fun convert(holder: BaseViewHolder, item: String) {
        Glide.with(context)
            .load(item)
            .into(holder.getView<ImageView>(R.id.itemImageV))
    }

}

// 参考: https://blog.csdn.net/harvic880925/article/details/84979161
// 水平的
class AdvancedCustomLayoutManger3(context: Context) : RecyclerView.LayoutManager() {
    // 总共的滑动距离
    private var mSumDx = 0

    // item的宽度和
    private var mTotalWidth = 0
    private var mItemWidth = 0
    private var mItemHeight = 0

    // item的一半宽度
    private var mInternalWidth = 0

    // item移动后的位置
    private var mStartX = 0

    // 记录每个item的位置
    private val mItemRects = SparseArray<Rect>()

    // 保存已经布局好的item
    private val mHasAttachedItems = SparseBooleanArray()
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.MATCH_PARENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        if (itemCount == 0) { //没有Item，界面空着吧
            detachAndScrapAttachedViews(recycler)
            return
        }
        mHasAttachedItems.clear()
        mItemRects.clear()

        detachAndScrapAttachedViews(recycler)

        //将item的位置存储起来
        val childView: View = recycler.getViewForPosition(0)
        measureChildWithMargins(childView, 0, 0)
        mItemWidth = getDecoratedMeasuredWidth(childView)
        mItemHeight = getDecoratedMeasuredHeight(childView)
        mInternalWidth = mItemWidth / 2

        mStartX = width / 2 - mInternalWidth

        //定义水平方向的偏移量
        var offsetX = 0

        for (i in 0 until itemCount) {
            val rect = Rect(mStartX + offsetX, 0, mStartX + offsetX + mItemWidth, mItemHeight)
            mItemRects.put(i, rect)
            mHasAttachedItems.put(i, false)
            offsetX += mInternalWidth
        }
        offsetX += mStartX

        val visibleCount: Int = getHorizontalSpace() / mInternalWidth
        val visibleRect = getVisibleArea()
        for (i in 0..visibleCount) {
            insertView(i, visibleRect, recycler, false)
        }

        //如果所有子View的宽度和没有填满RecyclerView的宽度，
        // 则将宽度设置为RecyclerView的宽度

        //如果所有子View的宽度和没有填满RecyclerView的宽度，
        // 则将宽度设置为RecyclerView的宽度
        mTotalWidth = Math.max(offsetX, getHorizontalSpace())
    }

    override fun canScrollHorizontally(): Boolean {
        return true
    }

    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State?
    ): Int {
        if (childCount <= 0) {
            return dx
        }

        var travel = dx
        //如果滑动到最顶部
        //如果滑动到最顶部
        if (mSumDx + dx < 0) {
            travel = -mSumDx
        } else if (mSumDx + dx > getMaxOffset()) {
            //如果滑动到最底部
            travel = getMaxOffset() - mSumDx
        }

        mSumDx += travel

        val visibleRect = getVisibleArea()

        //回收越界子View
        for (i in childCount - 1 downTo 0) {
            val child = getChildAt(i)?: continue
            val position = getPosition(child)
            val rect: Rect = mItemRects.get(position)
            if (!Rect.intersects(rect, visibleRect)) {
                removeAndRecycleView(child, recycler)
                mHasAttachedItems.put(position, false)
            } else {
                layoutDecoratedWithMargins(
                    child,
                    rect.left - mSumDx,
                    rect.top,
                    rect.right - mSumDx,
                    rect.bottom
                )
                handleChildView(child, rect.left - mStartX - mSumDx)
                mHasAttachedItems.put(position, true)
            }
        }

        //填充空白区域

        //填充空白区域
        val lastView = getChildAt(childCount - 1)
        val firstView = getChildAt(0)
        if (travel >= 0) {
            val minPos = getPosition(firstView!!)
            for (i in minPos until itemCount) {
                insertView(i, visibleRect, recycler, false)
            }
        } else {
            val maxPos = getPosition(lastView!!)
            for (i in maxPos downTo 0) {
                insertView(i, visibleRect, recycler, true)
            }
        }
        return travel
    }

    fun getCenterPosition(): Int {
        var pos = mSumDx / mInternalWidth
        val more = mSumDx % mInternalWidth
        if (more > mInternalWidth * 0.5f) pos++
        return pos
    }

    fun getFirstVisiblePosition(): Int {
        if (childCount <= 0) return 0
        val view = getChildAt(0) ?: return 0
        return getPosition(view)
    }

    fun calculateDistance(velocityX: Int, distance: Double): Double {
        val extra: Int = mSumDx % mInternalWidth
        val realDistance: Double
        realDistance = if (velocityX > 0) {
            if (distance < mInternalWidth) {
                (mInternalWidth - extra).toDouble()
            } else {
                distance - distance % mInternalWidth- extra
            }
        } else {
            if (distance < mInternalWidth) {
                extra.toDouble()
            } else {
                distance - distance % mInternalWidth + extra
            }
        }
        return realDistance
    }

    // 新增travel移动后, 当前屏幕所在的位置
    // sumDy 上次移动距离
    private fun getVisibleArea(): Rect {
        return Rect(
            paddingLeft + mSumDx, paddingTop,
            width + mSumDx - paddingRight, height - paddingBottom
        )
    }

    // 当前RecyclerView可见垂直高度
    private fun getHorizontalSpace(): Int {
        return width - paddingLeft - paddingRight
    }

    private fun insertView(
        pos: Int,
        visibleRect: Rect,
        recycler: RecyclerView.Recycler,
        isFirstPos: Boolean
    ) {
        val rect = mItemRects[pos]
        if (Rect.intersects(visibleRect, rect) && !mHasAttachedItems[pos]) {
            val child = recycler.getViewForPosition(pos)
            if (isFirstPos) {
                addView(child, 0)
            } else {
                addView(child)
            }
            measureChildWithMargins(child, 0, 0)
            layoutDecoratedWithMargins(
                child,
                rect.left - mSumDx,
                rect.top,
                rect.right - mSumDx,
                rect.bottom
            )
            handleChildView(child, rect.left - mStartX - mSumDx)
            mHasAttachedItems.put(pos, true)
        }
    }

    private fun handleChildView(child: View, moveX: Int) {
        val radio = computeScale(moveX)
        child.scaleX = radio
        child.scaleY = radio

        val rotation = computeRotationY(moveX);
        child.rotationY = rotation
    }

    private fun computeScale(x: Int): Float {
        var scale: Float = 1 - Math.abs(x * 1.0f / (8f * mInternalWidth))
        if (scale < 0) scale = 0f
        if (scale > 1) scale = 1f
        return scale
    }

    private fun getMaxOffset(): Int {
        return (itemCount - 1) * mInternalWidth
    }

    private val M_MAX_ROTATION_Y = 30.0f

    private fun computeRotationY(x: Int): Float {
        var rotationY: Float
        rotationY = -M_MAX_ROTATION_Y * x / mInternalWidth
        if (Math.abs(rotationY) > M_MAX_ROTATION_Y) {
            rotationY = if (rotationY > 0) {
                M_MAX_ROTATION_Y
            } else {
                -M_MAX_ROTATION_Y
            }
        }
        return rotationY
    }

}

// https://blog.csdn.net/harvic880925/article/details/86606873
class CustomRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    init {
        isChildrenDrawingOrderEnabled = true
    }

    override fun getChildDrawingOrder(childCount: Int, i: Int): Int {
        val center: Int = (getAdvancedCustomLayoutManager3().getCenterPosition()
                - getAdvancedCustomLayoutManager3().getFirstVisiblePosition()) //计算正在显示的所有Item的中间位置
        val order: Int = if (i == center) {
            childCount - 1
        } else if (i > center) {
            center + childCount - 1 - i
        } else {
            i
        }
        return order
    }
    fun getAdvancedCustomLayoutManager3(): AdvancedCustomLayoutManger3 {
        return layoutManager as AdvancedCustomLayoutManger3
    }


    override fun fling(velocityX: Int, velocityY: Int): Boolean {
        //缩小滚动距离
        var flingX = (velocityX * 0.40f).toInt()
        val manger: AdvancedCustomLayoutManger3 = getAdvancedCustomLayoutManager3()
        val distance = getSplineFlingDistance(flingX)
        val newDistance: Double = manger.calculateDistance(velocityX, distance)
        val fixVelocityX = getVelocity(newDistance)
        flingX = if (velocityX > 0) {
            fixVelocityX
        } else {
            -fixVelocityX
        }
        return super.fling(flingX, velocityY)
    }


    /**
     * 根据松手后的滑动速度计算出fling的距离
     *
     * @param velocity
     * @return
     */
    private fun getSplineFlingDistance(velocity: Int): Double {
        val l = getSplineDeceleration(velocity)
        val decelMinusOne = DECELERATION_RATE - 1.0
        return mFlingFriction * getPhysicalCoeff() * Math.exp(DECELERATION_RATE / decelMinusOne * l)
    }

    /**
     * 根据距离计算出速度
     *
     * @param distance
     * @return
     */
    private fun getVelocity(distance: Double): Int {
        val decelMinusOne = DECELERATION_RATE - 1.0
        val aecel =
            Math.log(distance / (mFlingFriction * mPhysicalCoeff)) * decelMinusOne / DECELERATION_RATE
        return Math.abs((Math.exp(aecel) * (mFlingFriction * mPhysicalCoeff) / INFLEXION).toInt())
    }

    /**
     * --------------flling辅助类---------------
     */
    private val INFLEXION = 0.35f // Tension lines cross at (INFLEXION, 1)

    private val mFlingFriction = ViewConfiguration.getScrollFriction()
    private val DECELERATION_RATE = (Math.log(0.78) / Math.log(0.9)).toFloat()
    private var mPhysicalCoeff = 0f

    private fun getSplineDeceleration(velocity: Int): Double {
        val ppi = this.resources.displayMetrics.density * 160.0f
        val mPhysicalCoeff = (SensorManager.GRAVITY_EARTH // g (m/s^2)
                * 39.37f // inch/meter
                * ppi
                * 0.84f) // look and feel tuning
        return Math.log((INFLEXION * Math.abs(velocity) / (mFlingFriction * mPhysicalCoeff)).toDouble())
    }

    private fun getPhysicalCoeff(): Float {
        if (mPhysicalCoeff == 0f) {
            val ppi = this.resources.displayMetrics.density * 160.0f
            mPhysicalCoeff = (SensorManager.GRAVITY_EARTH // g (m/s^2)
                    * 39.37f // inch/meter
                    * ppi
                    * 0.84f) // look and feel tuning
        }
        return mPhysicalCoeff
    }

}

