package com.tuann.mvvm.ui.common.mapper

import android.support.annotation.CheckResult
import com.tuann.mvvm.ui.Result
import com.tuann.mvvm.util.rx.SchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Single

@CheckResult
fun <T> Flowable<T>.toResult(schedulerProvider: SchedulerProvider):
        Flowable<Result<T>> {
    return compose { item ->
        item
                .map {
                    return@map Result.success(it) }
                .onErrorReturn { e -> Result.failure(e.message ?: "unknown", e) }
                .observeOn(schedulerProvider.ui())
                .startWith(Result.inProgress())
    }
}

@CheckResult fun <T> Single<T>.toResult(schedulerProvider: SchedulerProvider):
        Flowable<Result<T>> {
    return toFlowable().toResult(schedulerProvider)
}