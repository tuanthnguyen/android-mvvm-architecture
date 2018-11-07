package com.tuann.mvvm.data.repository

import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.model.Image
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

interface ImageRepository {
    val images : Flowable<List<Image>>
    fun loadImages(page: Int): Single<List<Image>>
    fun loadImagesApi(page: Int): Single<List<Image>>
    fun saveImages(images: List<ImageEntity>, page: Int): Completable
}