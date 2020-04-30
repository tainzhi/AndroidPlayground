package com.tainzhi.android.sample.github.di

import com.tainzhi.android.sample.github.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/11 下午8:32
 * @description:
 **/

@Suppress("unused")
@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}