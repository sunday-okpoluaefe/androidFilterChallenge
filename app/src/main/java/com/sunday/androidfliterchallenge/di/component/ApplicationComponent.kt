package com.sunday.androidfliterchallenge.di.component

import android.app.Application
import com.sunday.androidfliterchallenge.App
import com.sunday.androidfliterchallenge.di.module.ApplicationModule
import com.sunday.androidfliterchallenge.di.module.PresentationModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@Component(modules = [AndroidSupportInjectionModule::class, ApplicationModule::class, PresentationModule::class ])
interface ApplicationComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder
        fun build(): ApplicationComponent
    }

}