package com.tuann.mvvm.data.db.entity.mapper

import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.model.Image
import io.reactivex.Flowable

fun Flowable<List<ImageEntity>>.toImages(): Flowable<List<Image>> = map {
    return@map it.toImages()
}

fun List<ImageEntity>.toImages(): List<Image> =
        map { Image(it.id, it.urls.small, it.urls.full, it.userEntity?.name ?: "") }

fun List<com.tuann.mvvm.data.api.response.Image>.toImagesModel() =
        map { Image(it.id, it.urls.small, it.urls.full, it.user.name) }