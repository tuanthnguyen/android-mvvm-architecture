package com.tuann.mvvm.presentation

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.tuann.mvvm.R
import com.tuann.mvvm.di.ViewModelFactory
import com.tuann.mvvm.presentation.common.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity() {
    @Inject
    lateinit var navigator: Navigator
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigator.navigateToImages()
        }
    }
}