package com.tainzhi.sample.api.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.tainzhi.sample.api.R

/**
 * Created by muqing on 2019-08-03.
 * Email: qfq61@qq.com
 */
class MiniumFragment(private val title: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_minium, container, false)
        view.findViewById<TextView>(R.id.tv_title).text = title
        return view
    }
}