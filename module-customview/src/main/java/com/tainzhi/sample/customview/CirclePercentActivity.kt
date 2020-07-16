package com.tainzhi.sample.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class CirclePercentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_circle_percent)
        findViewById<Button>(R.id.button)?.setOnClickListener(View.OnClickListener { View: View? ->
            findViewById<CirclePercentView>(R.id.circlePercentView)?.setPercent(
                (Math.random() * 100).toInt()
            )
        })
    }
}