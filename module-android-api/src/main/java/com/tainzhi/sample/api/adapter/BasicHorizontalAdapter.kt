package com.tainzhi.sample.api.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tainzhi.sample.api.R

/**
 * Created by muqing on 2019-10-08.
 * Email: qfq61@qq.com
 */
class BasicHorizontalAdapter :
    BaseQuickAdapter<String?, BaseViewHolder>(R.layout.item_basic_list) {
    override fun convert(baseViewHolder: BaseViewHolder, s: String?) {
        baseViewHolder.setText(R.id.activity_name, s)
    }
}