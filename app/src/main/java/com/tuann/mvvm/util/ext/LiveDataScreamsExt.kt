package com.tuann.mvvm.util.ext

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.LiveDataReactiveStreams
import android.arch.lifecycle.Transformations
import org.reactivestreams.Publisher

fun <T> Publisher<T>.toLiveData() = LiveDataReactiveStreams.fromPublisher(this) as LiveData<T>

inline fun <X, Y> LiveData<X>.map(crossinline transformer: (X) -> Y): LiveData<Y> =
        Transformations.map(this) { transformer(it) }