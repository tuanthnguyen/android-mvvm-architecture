package com.tuann.mvvm.di.activitymodule

import android.arch.lifecycle.ViewModel
import android.support.v7.app.AppCompatActivity
import com.tuann.mvvm.di.ViewModelKey
import com.tuann.mvvm.ui.MainActivity
import com.tuann.mvvm.ui.MainViewModel
import com.tuann.mvvm.ui.images.ImagesFragment
import com.tuann.mvvm.ui.images.ImagesViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
interface MainActivityModule {
    @Binds
    fun providesAppCompatActivity(mainActivity: MainActivity): AppCompatActivity

    @ContributesAndroidInjector
    fun contributeImagesFragment(): ImagesFragment

    @Binds @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    fun bindImagesViewModel(
            imagesViewModel: ImagesViewModel
    ): ViewModel

    @Binds @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
            mainViewModel: MainViewModel
    ): ViewModel
}