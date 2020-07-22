package com.tainzhi.sample.customview

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.android.synthetic.main.activity_level_up.*
import java.util.*

class LevelUpActivity : AppCompatActivity() {

    private val picSrc = arrayOf(
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595408352767&di=db6743590b4ed4229272810c35dea63f&imgtype=0&src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F02%2F08%2F98%2F599e7948b7840_610.jpg",
        "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595413777784&di=4f424294a8b9858490452d5daea831f9&imgtype=0&src=http%3A%2F%2Fd.lanrentuku.com%2Fdown%2Fpng%2F0907%2FTango-Emote%2FTango-Emote-10.png",
        "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=925765617,1844225427&fm=26&gp=0.png"
    )

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_up)
        levelProgressBar.setOnClickListener {
//            val progressValue = Random().nextInt(100)
//            currentProgress.text = levelProgressBar.point.toString()
//            levelProgressBar.point = Random().nextInt(20010)
        }
        levelProgressBar.setAllInfo(5000,
            arrayOf(0, 500, 2000, 10000, 20000),
            Array<String>(5) {index->
                "等级$index"
            }
        )
        inputProgress.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                levelProgressBar.point =
                    if (s.toString().trim().isNullOrEmpty()) 0 else s.toString().trim().toInt()
                currentProgress.text = levelProgressBar.point.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        updatePicButton.setOnClickListener {

            for (index in 0 until 5) {
                updateImage(index, picSrc[Random().nextInt(picSrc.size)])
            }
        }

    }

    private fun updateImage(index: Int, url: String) {
        Glide.with(levelProgressBar.context)
            .asBitmap()
            .load(url).into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    levelProgressBar.updateIcon(index, resource)
                }
            })
    }
}