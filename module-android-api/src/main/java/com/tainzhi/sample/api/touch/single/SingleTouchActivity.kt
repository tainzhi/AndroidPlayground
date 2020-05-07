package com.tainzhi.sample.api.touch.single

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import com.tainzhi.sample.api.R
import kotlinx.android.synthetic.main.activity_single_touch.*

class SingleTouchActivity : AppCompatActivity() {
    private val seekbarListener: SeekBar.OnSeekBarChangeListener = object: SeekBar.OnSeekBarChangeListener {

        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            singleTouchView.setColor(redSeekBar.progress, greenSeekBar.progress, blueSeekBar.progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_touch)

        redSeekBar.setOnSeekBarChangeListener(seekbarListener)
        greenSeekBar.setOnSeekBarChangeListener(seekbarListener)
        blueSeekBar.setOnSeekBarChangeListener(seekbarListener)
    }
}