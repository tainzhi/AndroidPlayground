package com.tainzhi.sample.customview.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tainzhi.sample.customview.R

/**
 * Created by muqing on 2019-10-08.
 * Email: qfq61@qq.com
 */
class BasicHorizontalAdapter :
    BaseQuickAdapter<NameClass, BaseViewHolder>(
        R.layout.item_basic_list
    ) {
    override fun convert(holder: BaseViewHolder, item: NameClass) {
        holder.setText(R.id.activity_name, item.className)
    }
}

data class NameClass(val className: String, val clazz: Class<*>)