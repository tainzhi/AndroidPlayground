package com.tainzhi.sample.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_wave_progress.*
import kotlin.random.Random

class WaveProgressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wave_progress)

        waveProgressView.run {
            isDrawSecondWave = true
            progressNum = 50f
            onAnimationListener = object: WaveProgressView.OnAnimationListener {
                override fun howToChangeText(
                    interpolatedTime: Float,
                    updateNum: Float,
                    maxNum: Float
                ): String {
                    TODO("Not yet implemented")
                }

                override fun howToChangeWaveHeight(percent: Float, waveHeight: Float): Float {
                    TODO("Not yet implemented")
                }
            }
            setOnClickListener {
                progressNum = Random.nextFloat()* 100
            }
        }
    }
}