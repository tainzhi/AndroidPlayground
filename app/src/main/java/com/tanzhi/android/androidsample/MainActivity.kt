package com.tanzhi.android.androidsample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.tanzhi.android.androidsample.databinding.ActivityMainBinding
import com.tanzhi.android.playground.router.RouterPath

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainAdapter by lazy {
        MainAdapter()
    }

    private val mainData = ArrayList<MainBean>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initARouter()

        binding.homeRecyclerView.run {
            layoutManager = GridLayoutManager(context, 2);
            adapter = mainAdapter
        }
        mainData.add(MainBean("android api", navigateToApi))
        mainData.add(MainBean("自定义view", navigateToCustomView))
        mainData.add(MainBean("MVVM实现的简易github", navigateToGithub))

        mainAdapter.run {
            data = mainData
            setOnItemClickListener { _, _, position ->
                mainData[position].navigation.invoke()
            }
        }
    }

    private val navigateToApi: () -> Unit = {
        ARouter.getInstance().build(RouterPath.PATH_API)
            .navigation()
    }

    private val navigateToCustomView: () -> Unit = {
        ARouter.getInstance().build(RouterPath.PATH_CUSTOM_VIEW)
            .navigation()
    }

    private val navigateToGithub: () -> Unit = {
         ARouter.getInstance().build(RouterPath.PATH_GITHUB)
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