package com.tainzhi.sample.api.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tainzhi.sample.api.R

/**
 * Created by muqing on 2019-08-15.
 * Email: qfq61@qq.com
 */
class QuickAdapter(data: List<Int?>?) :
    BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_basic_horizontal_view, data) {
    override fun convert(helper: BaseViewHolder, item: Int) {
        helper.setText(R.id.tv_basic_id, item.toString())
    }
}