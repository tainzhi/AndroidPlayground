package com.tainzhi.sample.api.recyclerview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.tainzhi.sample.api.R
import com.tainzhi.sample.api.adapter.CardScaleHelperAdapter
import kotlinx.android.synthetic.main.activity_card_scale_helper.*

class CardScaleHelperActivity : AppCompatActivity() {

    private val mList = MutableList<Int>(30
    ) { index -> index + 1 }

    private val adapter by lazy {
        CardScaleHelperAdapter(this, mList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_scale_helper)

        recyclerView.run {
            adapter = this@CardScaleHelperActivity.adapter
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }
}