package com.tuann.mvvm.data.db.entity.mapper

import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.db.entity.ImageWithUser
import com.tuann.mvvm.data.model.Image
import io.reactivex.Flowable

fun Flowable<List<ImageWithUser>>.toImages(): Flowable<List<Image>> = map {
    return@map it.toImages()
}

fun List<ImageEntity>.toImagesFromEntity(): List<Image> =
        map { Image(it.id, it.urls.small, it.urls.full, it.userEntity?.name ?: "") }

fun List<ImageWithUser>.toImages(): List<Image> =
        map { Image(it.id, it.small, it.full, it.authorName) }