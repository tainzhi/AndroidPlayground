package com.tainzhi.android.sample.github

import android.app.Activity
import android.app.Application
import com.tainzhi.android.sample.github.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/10 上午11:50
 * @description:
 **/

class GithubApp: Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}

