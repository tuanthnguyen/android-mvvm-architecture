package com.tuann.mvvm.di

import android.app.Application
import android.content.Context
import com.tuann.mvvm.data.api.ImageApi
import com.tuann.mvvm.data.db.ImageDatabase
import com.tuann.mvvm.data.repository.ImageDataRepository
import com.tuann.mvvm.data.repository.ImageRepository
import com.tuann.mvvm.util.rx.AppSchedulerProvider
import com.tuann.mvvm.util.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @Singleton
    @Provides
    @JvmStatic
    fun provideContext(application: Application): Context = application

    @Singleton
    @Provides
    @JvmStatic
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Singleton
    @Provides
    @JvmStatic
    fun provideImageRepository(
        imageApi: ImageApi,
        imageDatabase: ImageDatabase,
        schedulerProvider: SchedulerProvider
    ): ImageRepository =
        ImageDataRepository(imageApi, imageDatabase, schedulerProvider)
}