package com.tainzhi.sample.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tainzhi.sample.customview.R
import kotlinx.android.synthetic.main.activity_level_up.*
import kotlinx.android.synthetic.main.activity_level_up.view.*
import java.util.*

class LevelUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_up)
        levelProgressBar.setOnClickListener {
            levelProgressBar.progress = Random().nextInt(100)
        }
    }
}