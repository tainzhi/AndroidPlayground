package com.tainzhi.sample.customview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_verify_code.*

class VerificationCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_code)

        vfcView.onCodeFinishListener = object: VerificationCodeView.OnCodeFinishListener {
            override fun onTextChange(view: View, content: String) {
                verificodeTv.text = content
            }

            override fun onComplete(view: View, content: String) {
                verificodeTv.text = "验证码: $content"
            }
        }
    }
}