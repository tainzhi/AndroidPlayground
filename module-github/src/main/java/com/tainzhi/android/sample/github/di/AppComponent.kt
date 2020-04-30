package com.tainzhi.android.sample.github.di

import android.app.Application
import com.tainzhi.android.sample.github.GithubApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * @author:       tainzhi
 * @mail:         qfq61@qq.com
 * @date:         2020/1/11 下午8:45
 * @description:
 **/

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(githubApp: GithubApp)
}