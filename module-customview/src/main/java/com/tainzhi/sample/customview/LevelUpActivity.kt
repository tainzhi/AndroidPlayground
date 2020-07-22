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

class LevelUpActivity : AppCompatActivity() {

    private val picSrc = mapOf(
        0 to "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595408352767&di=db6743590b4ed4229272810c35dea63f&imgtype=0&src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F02%2F08%2F98%2F599e7948b7840_610.jpg",
        1 to "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595408352767&di=db6743590b4ed4229272810c35dea63f&imgtype=0&src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F02%2F08%2F98%2F599e7948b7840_610.jpg",
        2 to "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595408352767&di=db6743590b4ed4229272810c35dea63f&imgtype=0&src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F02%2F08%2F98%2F599e7948b7840_610.jpg",
        3 to "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595408352767&di=db6743590b4ed4229272810c35dea63f&imgtype=0&src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F02%2F08%2F98%2F599e7948b7840_610.jpg",
        4 to "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1595408352767&di=db6743590b4ed4229272810c35dea63f&imgtype=0&src=http%3A%2F%2Fpic.51yuansu.com%2Fpic3%2Fcover%2F02%2F08%2F98%2F599e7948b7840_610.jpg"
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
        val points = arrayOf(0, 500, 2000, 10000, 20000)
        levelProgressBar.levelPoints = arrayListOf(0, 500, 2000, 10000, 20000)
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
            picSrc.forEach { (index, url) ->
                updateImage(index, url)
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