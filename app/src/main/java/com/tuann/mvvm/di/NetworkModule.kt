package com.tuann.mvvm.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import com.tuann.mvvm.data.api.ImageApi
import com.tuann.mvvm.data.api.response.mapper.ApplicationJsonAdapterFactory
import com.tuann.mvvm.data.api.response.mapper.InstantAdapter
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.threeten.bp.Instant
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @NetworkLogger @Singleton @Provides @IntoSet
    fun provideStetho() : Interceptor = StethoInterceptor()

    @NetworkLogger @Singleton @Provides @IntoSet
    fun provideNetworkLogger(): Interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Singleton @Provides
    fun provideOkHttpClient(@NetworkLogger loggingInterceptors: Set<@JvmSuppressWildcards
    Interceptor>):
            OkHttpClient =
            OkHttpClient.Builder().apply {
                loggingInterceptors.forEach {
                    addNetworkInterceptor(it)
                }
            }.build()

    @Singleton @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder().apply {
                client(okHttpClient)
                baseUrl("https://api.unsplash.com/")
                addConverterFactory(MoshiConverterFactory.create(Moshi.Builder()
                        .apply {
                            add(ApplicationJsonAdapterFactory.INSTANCE)
                            add(Instant::class.java, InstantAdapter())
                        }.build()))
                addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            }.build()

    @Singleton @Provides
    fun provideImageApi(retrofit: Retrofit) : ImageApi =
            retrofit.create(ImageApi::class.java)
}