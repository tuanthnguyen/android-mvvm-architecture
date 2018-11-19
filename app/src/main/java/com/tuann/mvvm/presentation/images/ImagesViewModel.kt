package com.tuann.mvvm.presentation.images

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.Transformations
import com.tuann.mvvm.data.model.Image
import com.tuann.mvvm.data.repository.ImageRepository
import com.tuann.mvvm.presentation.Result
import com.tuann.mvvm.presentation.common.mapper.toResult
import com.tuann.mvvm.util.ext.map
import com.tuann.mvvm.util.ext.toLiveData
import com.tuann.mvvm.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ImagesViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), LifecycleObserver {

    private val compositeDisposable = CompositeDisposable()
    private val page = MutableLiveData<Int>()
    private val _isLoadingRefresh = ObservableBoolean()
    val isLoadingRefresh: ObservableBoolean = _isLoadingRefresh
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean> = _refresh

    private val _images: LiveData<Result<List<Image>>> = Transformations
        .switchMap(page) { page ->
            return@switchMap repository.loadImages(page)
                .toResult(schedulerProvider)
                .toLiveData()
        }
    val images: LiveData<Result<List<Image>>> = _images

    private val _isLoading: LiveData<Boolean> by lazy {
        _images.map {
            _isLoadingRefresh.set(it.inProgress)
            it.inProgress
        }
    }
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadImages(page: Int = 1) {
        this.page.value = page
    }

    fun onRefresh() {
        _refresh.value = true
        page.value = 1
    }

    fun retry() {
        page.value = page.value
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}