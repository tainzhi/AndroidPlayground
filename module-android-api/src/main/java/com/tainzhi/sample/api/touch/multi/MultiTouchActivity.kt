package com.tainzhi.sample.api.touch.multi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MultiTouchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(MultiTouchView(this))
    }
}
