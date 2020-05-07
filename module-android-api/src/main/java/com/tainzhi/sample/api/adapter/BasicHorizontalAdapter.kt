package com.tainzhi.sample.api.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tainzhi.sample.api.R

/**
 * Created by muqing on 2019-10-08.
 * Email: qfq61@qq.com
 */
class BasicHorizontalAdapter :
    BaseQuickAdapter<NameClass, BaseViewHolder>(R.layout.item_basic_list) {
    override fun convert(baseViewHolder: BaseViewHolder, item: NameClass) {
        baseViewHolder.setText(R.id.activity_name, item.className)
    }
}

data class NameClass(val className: String, val clazz: Class<*>)