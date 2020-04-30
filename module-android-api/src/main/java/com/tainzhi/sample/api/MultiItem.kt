package com.tainzhi.sample.api

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2019/12/13 19:58
 * @description:
 **/

class MultiItem( type: Int) : MultiItemEntity {


    override val itemType = type

    companion object {
        const val FirstType = 0
        const val SecondType = 1
    }
}