package com.tuann.mvvm.di.activitymodule

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import com.tuann.mvvm.di.ViewModelKey
import com.tuann.mvvm.presentation.MainActivity
import com.tuann.mvvm.presentation.MainViewModel
import com.tuann.mvvm.presentation.images.ImagesFragment
import com.tuann.mvvm.presentation.images.ImagesViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(ImagesViewModel::class)
    fun bindImagesViewModel(
        imagesViewModel: ImagesViewModel
    ): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(
        mainViewModel: MainViewModel
    ): ViewModel
}