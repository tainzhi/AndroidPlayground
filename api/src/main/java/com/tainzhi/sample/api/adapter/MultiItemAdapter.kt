package com.tainzhi.sample.api.adapter

import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.tainzhi.sample.api.MultiItem
import com.tainzhi.sample.api.R

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2019/12/13 19:57
 * @description:
 **/

class MultiItemAdapter(data: List<MultiItem>?, rvParent: RecyclerView) :
        BaseMultiItemQuickAdapter<MultiItem,
                BaseViewHolder>
(data) {

    private val rvParent = rvParent

    init {
        addItemType(MultiItem.FirstType, R.layout.item_nested_recyclerview)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItem?) {
        when (item?.itemType) {
            MultiItem.FirstType -> convertFirst(helper)
            else -> Unit
        }
    }

    private fun convertFirst(helper: BaseViewHolder) {
        val nestedRecyclerView = helper.getView<RecyclerView>(R.id.nested_recycler_view)
        nestedRecyclerView.layoutManager = StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL)
        var datas = ArrayList<String>();
        for (i in 0..120) {
            datas.add(i.toString())
        }
        var adapter = BasicVertialAdapter(true)
        adapter.addData(datas);
        nestedRecyclerView.adapter = adapter
        nestedRecyclerView.setRecyclerListener { holder -> Log.d("qfq", "son position=${holder.adapterPosition}") }
        nestedRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                Log.d("qfq", "newstate=$newState")
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("qfq onScrolled", "recyclerview height=${nestedRecyclerView.height}")
            }
        })
        nestedRecyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                Log.d("qfq", "touch, recyclerview_height=${nestedRecyclerView.height}")
            }

            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                Log.d("qfq", "touch, recyclerview height=${nestedRecyclerView.height}")
                return true
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                Log.d("qfq", "touch")
            }
        })
        rvParent.isNestedScrollingEnabled = false
        rvParent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                Log.d("qfq", "dx=$dx, dy=$dy");
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
}