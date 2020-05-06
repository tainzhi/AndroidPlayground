package com.tanzhi.android.androidsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mainAdapter by lazy {
        MainAdapter()
    }

    private val mainData = ArrayList<MainBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initARouter()

        homeRecyclerView.run {
            layoutManager = GridLayoutManager(context, 2);
            adapter = mainAdapter
        }
        mainData.add(MainBean("android api", navigateToApi))
        mainData.add(MainBean("自定义view", navigateToCustomView))
        mainData.add(MainBean("MVVM实现的简易github", navigateToGithub))
        mainData.add(MainBean("rxjava2学习", navigateToRxjava2))

        mainAdapter.run {
            data = mainData
            setOnItemClickListener { _, _, position ->
                mainData[position].navigation.invoke()
            }
        }
    }

    private val navigateToApi: () -> Unit = {
        ARouter.getInstance().build("/api/main")
            .navigation()
    }

    private val navigateToCustomView: () -> Unit = {
        ARouter.getInstance().build("/customview/main")
            .navigation()
    }

    private val navigateToGithub: () -> Unit = {
         ARouter.getInstance().build("/github/main")
            .navigation()
    }

    private val navigateToRxjava2: () -> Unit = {
        ARouter.getInstance().build("/rxjava2/main")
            .navigation()
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(application)
    }


    override fun onDestroy() {
        ARouter.getInstance().destroy()
        super.onDestroy()
    }
}