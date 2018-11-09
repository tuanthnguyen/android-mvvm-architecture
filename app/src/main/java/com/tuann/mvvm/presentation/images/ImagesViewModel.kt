package com.tuann.mvvm.presentation.images

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.*
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
    val isLoadingRefresh = ObservableBoolean()
    var page = MutableLiveData<Int>()
    val refresh = MutableLiveData<Boolean>()

    var images: LiveData<Result<List<Image>>> = Transformations
        .switchMap(page) { page ->
            return@switchMap repository.loadImages(page)
                .toResult(schedulerProvider)
                .toLiveData()
        }

    fun loadImages(page: Int = 1) {
        this.page.value = page
    }

    val isLoading: LiveData<Boolean> by lazy {
        images.map {
            isLoadingRefresh.set(it.inProgress)
            it.inProgress
        }
    }

    fun onRefresh() {
        refresh.value = true
        this.page.value = 1
    }

    fun retry() {
        this.page.value = page.value
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}