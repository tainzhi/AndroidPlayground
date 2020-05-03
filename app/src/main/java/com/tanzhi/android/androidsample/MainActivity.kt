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
        mainData.add(MainBean("android api", navigateToApi()))
        mainData.add(MainBean("自定义view", Unit))
        mainData.add(MainBean("MVVM实现的简易github", Unit))
        mainData.add(MainBean("rxjava2学习", Unit))

        mainAdapter.data = mainData
    }

    private fun navigateToApi()  {
        ARouter.getInstance().build("/api/main")
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