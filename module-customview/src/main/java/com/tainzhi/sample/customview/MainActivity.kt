package com.tainzhi.sample.customview

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = "/customview/main")
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_view_activity_main)
        findViewById<Button>(R.id.button)?.setOnClickListener(View.OnClickListener { View: View? ->
            findViewById<CirclePercentView>(R.id.circlePercentView)?.setPercent(
                (Math.random() * 100).toInt()
            )
        })
    }
}