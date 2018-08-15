package com.tuann.mvvm.presentation

import com.jakewharton.threetenabp.AndroidThreeTen
import com.tuann.mvvm.di.DaggerAppComponent
import com.tuann.mvvm.di.DatabaseModule
import com.tuann.mvvm.di.NetworkModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
                .application(this)
                .networkModule(NetworkModule())
                .databaseModule(DatabaseModule())
                .build()
    }
}