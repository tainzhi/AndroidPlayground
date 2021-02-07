package com.tainzhi.sample.customview

import android.content.Context
import android.graphics.*
import android.view.View
import android.widget.TextView
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tainzhi.android.common.base.ui.BaseViewBindingActivity
import com.tainzhi.android.common.util.BitmapUtil
import com.tainzhi.android.common.util.dp
import com.tainzhi.sample.customview.databinding.ActivityRecyclerViewItemDecorationBinding

class RecyclerViewItemDecorationActivity : BaseViewBindingActivity<ActivityRecyclerViewItemDecorationBinding>() {

    override fun initView() {
        mBinding.recyclerV.run {
            layoutManager = LinearLayoutManager(context)
            adapter = ItemDecorationAdapter(generateFakeData())
            // addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL).apply {
            //     setDrawable(getDrawable(R.drawable.item_divider)!!)
            // })
            addItemDecoration(LinearItemDecoration(context))
        }
    }

    private fun generateFakeData(): List<String> {
        return List(30) { index -> "Item $index"}
    }
}

class ItemDecorationAdapter(datas: List<String>): BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_decoration,
    datas as MutableList<String>
) {
    override fun convert(holder: BaseViewHolder, item: String) {
        holder.getView<TextView>(R.id.nameTextV).text = item
    }

}

class LinearItemDecoration(val context: Context): RecyclerView.ItemDecoration() {
    private val mPaint: Paint = Paint().apply {
        color = Color.GREEN
        }
    
    private val badgeBitmap: Bitmap
    private val circleRadius = 10.dp()
    private val badgeWidth = 48.dp()
    init {
        val options = BitmapFactory.Options()
        badgeBitmap = BitmapUtil.getBitmap(context, R.drawable.badge)
    }
    
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.left = 200
        outRect.bottom = 5
    }
    
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val layoutManager = parent.layoutManager
        parent.children.forEach { child->
            // child为Recyclerview的item
            // 在ItemDecoration的左空间画一个圆
            val cx = layoutManager!!.getLeftDecorationWidth(child) / 2
            val cy = child.top + child.height / 2
            c.drawCircle(cx.toFloat(), cy.toFloat(), circleRadius.toFloat(), mPaint)
            }
    }
    
    // ItemDecoration与Item的绘制关系: decoration.onDraw() -> item.onDraw() -> decoration.onDrawOver()
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val childCount = parent.childCount
        val layoutManager = parent.layoutManager
        for (i in 0 until childCount) {
            // child为Recyclerview的item
            val child = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(child)
            // item 起始位置
            val itemX = layoutManager!!.getLeftDecorationWidth(child)
            // item中心点的 y 坐标
            val itemCenterY = child.top + child.height / 2
            if (index % 5 == 0) {
                val srcRect = Rect(0, 0, badgeBitmap.width, badgeBitmap.height)
                val destRect = Rect(itemX - badgeWidth/2,  itemCenterY - badgeWidth/2, itemX+badgeWidth/2,itemCenterY + badgeWidth/2)
                c.drawBitmap(badgeBitmap, srcRect, destRect, mPaint)
            }
        }
    }
}
