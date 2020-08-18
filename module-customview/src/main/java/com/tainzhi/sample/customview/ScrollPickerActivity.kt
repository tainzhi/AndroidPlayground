package com.tainzhi.sample.customview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_scroll_picker.*
import java.util.concurrent.CopyOnWriteArrayList

class ScrollPickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll_picker)

        val bitmaps = CopyOnWriteArrayList<Bitmap>()
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.ic_ceo_first_level))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.ic_ceo_first_level))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.ic_ceo_first_level))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.ic_ceo_first_level))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.ic_ceo_first_level))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.ic_ceo_first_level))
        bitmaps.add(BitmapFactory.decodeResource(resources, R.drawable.ic_ceo_first_level))
        bitmapScrollPicker.data = bitmaps
        bitmapScrollPicker.setOnSelectedListener { _, position ->
            scrollView.selectedPosition = position
        }

        scrollView.data = arrayListOf<CharSequence>(
            "one", "two", "three", "four", "five", "six", "seven"
        )
        scrollView.setOnSelectedListener { _, position ->
            bitmapScrollPicker.selectedPosition = position
        }

    }
}