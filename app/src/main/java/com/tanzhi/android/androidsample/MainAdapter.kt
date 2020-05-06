package com.tanzhi.android.androidsample

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/5/3 18:25
 * @description:
 **/

class MainAdapter: BaseQuickAdapter<MainBean, BaseViewHolder>(R.layout.item_main) {
    init {
        addChildClickViewIds(R.id.nameTv)
    }
    override fun convert(holder: BaseViewHolder, item: MainBean) {
        holder.setText(R.id.nameTv, item.name)
    }

}