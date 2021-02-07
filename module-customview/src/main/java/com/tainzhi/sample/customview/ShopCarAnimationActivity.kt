package com.tainzhi.sample.customview

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.tainzhi.android.common.base.ui.BaseViewBindingActivity
import com.tainzhi.sample.customview.databinding.ActivityShopCarAnimationBinding
import com.tainzhi.sample.customview.widget.Add2ShopCarAnim
import com.tainzhi.sample.customview.widget.Add2ShopCarAnimListener
import com.tainzhi.sample.customview.widget.ShopCarAnchor

class ShopCarAnimationActivity : BaseViewBindingActivity<ActivityShopCarAnimationBinding>(),
    ShopCarAnchor
{
    override fun initView() {
        val data = generateMockData()
        with(mBinding) {
            shopcarRv.run{
                layoutManager = LinearLayoutManager(context)
                adapter = ShopCarAdapter(data) { view, url->
                    add2ShopCar(view, url)
                }
            }
        }
    }

    private fun generateMockData(): MutableList<String> {
        val datas = mutableListOf<String>()
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic3.16pic.com%2F00%2F06%2F94%2F16pic_694190_b.jpg&refer=http%3A%2F%2Fpic3.16pic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=5367798c13d54b8594d54d6de37db8ed")
        datas.add("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpic32.nipic.com%2F20130816%2F8952533_165406191000_2.jpg&refer=http%3A%2F%2Fpic32.nipic.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1614758033&t=747ef63881939d9fa1d6dadf5989d0e8")
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

    private fun add2ShopCar(view: View, url: String) {
        Add2ShopCarAnim(
            this,
            view,
            url
        ).apply {
            add2ShopCarAnimListener = object: Add2ShopCarAnimListener {
                override fun onAnimStart() {
                }

                override fun onAnimEnd() {
                    shopCarCount += 1
                    mBinding.shopcarCountTv.text = "${shopCarCount}"
                }
            }
        }.start()
    }

    private var shopCarCount = 0

    override fun shopCarView(): View {
        return mBinding.shopcarIv
    }
}

class ShopCarAdapter(
    data: MutableList<String>,
     addCallback:(view: View, url: String) -> Unit
) :
    BaseQuickAdapter<String, BaseViewHolder>(
        R.layout.item_shopcar,
        data
    ) {
    init {
        setOnItemChildClickListener { _, view, position ->
            addCallback(getViewByPosition(position, R.id.itemIv) ?: view, data[position])
        }
        addChildClickViewIds(R.id.addIv)
    }
    override fun convert(holder: BaseViewHolder, item: String) {
        Glide.with(context)
            .load(item)
            .into(object: CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    holder.getView<ImageView>(R.id.itemIv).setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })
//
//        thread(true) {
//            val target: FutureTarget<File> = Glide.with(context)
//                .downloadOnly()
//                .load(item)
//                .submit()
//            val downloadFile = target.get()
//        }
    }
}
