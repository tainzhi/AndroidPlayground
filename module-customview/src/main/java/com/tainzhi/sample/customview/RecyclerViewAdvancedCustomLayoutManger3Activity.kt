package com.tainzhi.sample.customview

import android.content.Context
import android.graphics.Rect
import android.util.SparseArray
import android.util.SparseBooleanArray
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tainzhi.android.common.base.ui.BaseViewBindingActivity
import com.tainzhi.sample.customview.databinding.ActivityCustomLayoutManger3Binding
import kotlin.math.max

class RecyclerViewAdvancedCustomLayoutManger3Activity : BaseViewBindingActivity<ActivityCustomLayoutManger3Binding>() {
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

class ItemImageDecorationAdapter(datas: List<String>): BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_image,
    datas as MutableList<String>
) {
    override fun convert(holder: BaseViewHolder, item: String) {
        Glide.with(context)
            .load(item)
            .into( holder.getView<ImageView>(R.id.itemImageV))
    }

}

// 参考: https://blog.csdn.net/harvic880925/article/details/84979161
// 水平的
class AdvancedCustomLayoutManger3(context: Context): RecyclerView.LayoutManager() {
    // 总共的滑动距离
    private var sumDx = 0
    // item的宽度和
    private var totalWidth = 0
    private var itemWidth = 0
    private var itemHeight = 0
    // 记录每个item的位置
    private val itemRects = SparseArray<Rect>()
    // 保存已经布局好的item
    private val hasAttachedItems = SparseBooleanArray()
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT)
    }
    
    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State?) {
        // adapter中没有数据, 将所有的item从屏幕上剥离, 清空当前屏幕
        if (itemCount == 0) {
            detachAndScrapAttachedViews(recycler)
            return
        }
        hasAttachedItems.clear()
        itemRects.clear()
        // 将所有的item view从RecyclerView剥离, 然后再重新添加addView
        detachAndScrapAttachedViews(recycler)
        
        // 申请一个 HolderView
        val childView = recycler.getViewForPosition(0)
        // 测量HolderView后, 方便得出 width & height
        measureChildWithMargins(childView, 0, 0)
        itemWidth = getDecoratedMeasuredWidth(childView)
        itemHeight = getDecoratedMeasuredHeight(childView)
        
        var offsetX = 0
        // item大小固定, 为了布局方便, 记录下来其位置
        for (i in 0 until itemCount) {
            itemRects.put(i, Rect(offsetX, 0, offsetX +itemWidth, itemHeight))
            hasAttachedItems.put(i, false)
            offsetX += itemWidth
        }
        
        // 可见item 数目
        val visibleCount = getHorizontalSpace() / itemWidth
        for (i in 0..visibleCount) {
            insertView(i, getVisibleArea(), recycler, false)
        }
        // offsetY是所有item的总高度，而当item填不满RecyclerView时，
        // offsetY应该是比RecyclerView的真正高度小的，而此时的真正的高度应该是RecyclerView本身所设置的高度。
        totalWidth = max(offsetX, getHorizontalSpace())
    }
    
    override fun canScrollHorizontally(): Boolean {
        return true
    }
    
    override fun scrollHorizontallyBy(
        dx: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State?
    ): Int {
        if (childCount == 0) return dx
        // dx 表示手指在屏幕上的滑动位移
        // -dx <0 向上滑动
        // dx >0  向下滑动
        var travel = dx
        // 已经到顶, 不能再移动
        if (sumDx + dx < 0) {
            travel = -sumDx
        } else if (sumDx + dx > totalWidth - getHorizontalSpace()) {
            // msumDx + dx 表示当前的移动距离，
            // mTotalHeight - getVerticalSpace()表示当滑动到底时滚动的总距离；
            // 已经到底, 不能在滑动
            travel = totalWidth - getHorizontalSpace() - sumDx
        }
    
        sumDx += travel
    
        // travel >= 0 向上滚动
        // travel < 0  向下滚动
        // 回收越界的子view
        for (i in (childCount - 1) downTo 0) {
            val child = getChildAt(i) ?: continue
            val pos = getPosition(child)
            val rect = itemRects[pos]
            if (!Rect.intersects(rect, getVisibleArea())) {
                removeAndRecycleView(child, recycler)
                hasAttachedItems.put(pos, false)
            } else {
                // 屏幕的item直接变化, 不需要remove
                layoutDecoratedWithMargins(child, rect.left - sumDx, rect.top, rect.right- sumDx, rect.bottom )
                hasAttachedItems.put(pos, true)
            }
        }
    
        val lastView = getChildAt(childCount - 1) ?: return dx
        val firstView = getChildAt(0) ?: return dx
        if (travel >= 0) {
            val minPos = getPosition(firstView)
            for (i in minPos until itemCount) {
                insertView(i, getVisibleArea(), recycler, false)
            }
        } else {
            val maxPos = getPosition(lastView)
            for (i in maxPos downTo 0) {
                insertView(i, getVisibleArea(), recycler, true)
            }
        }
    
        return travel
    }
    
    
    // 新增travel移动后, 当前屏幕所在的位置
    // sumDy 上次移动距离
    private fun getVisibleArea(): Rect {
        return Rect(paddingLeft + sumDx, paddingTop,
            width + sumDx - paddingRight, height - paddingBottom)
    }
    
    // 当前RecyclerView可见垂直高度
    private fun getHorizontalSpace(): Int {
        return width - paddingLeft - paddingRight
    }
    
    private fun insertView(pos: Int, visibleRect: Rect, recycler: RecyclerView.Recycler, isFirstPos: Boolean) {
        val rect = itemRects[pos]
        if (Rect.intersects(visibleRect, rect) && !hasAttachedItems[pos]) {
            val child = recycler.getViewForPosition(pos)
            if (isFirstPos) {
                addView(child, 0)
            } else {
                addView(child)
            }
            measureChildWithMargins(child, 0, 0)
            layoutDecoratedWithMargins(child, rect.left - sumDx, rect.top , rect.right - sumDx, rect.bottom)
            hasAttachedItems.put(pos, true)
        }
    }
}

