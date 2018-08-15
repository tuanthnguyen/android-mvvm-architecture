package com.tuann.mvvm.di.activitymodule

import com.tuann.mvvm.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface MainActivityBuilder {
    @ContributesAndroidInjector(modules = [
        MainActivityModule::class
    ])
    fun contributeMainActivity(): MainActivity
}