package com.tainzhi.sample.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.tainzhi.sample.customview.R
import kotlinx.android.synthetic.main.activity_level_up.*
import kotlinx.android.synthetic.main.activity_level_up.view.*
import java.util.*

class LevelUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_up)
        levelProgressBar.setOnClickListener {
//            val progressValue = Random().nextInt(100)
//            currentProgress.text = levelProgressBar.point.toString()
//            levelProgressBar.point = Random().nextInt(20010)
        }
        val points = arrayOf(0, 500, 2000, 10000, 20000)
        levelProgressBar.levelPoints = arrayListOf(0, 500, 2000, 10000, 20000)
        inputProgress.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        submitProgress.setOnClickListener {
            levelProgressBar.point = inputProgress.text?.trim().toString().toInt()
            currentProgress.text = levelProgressBar.point.toString()
        }
    }
}