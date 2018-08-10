package com.tuann.mvvm.data.db

import com.tuann.mvvm.data.db.entity.ImageEntity
import io.reactivex.Flowable
import io.reactivex.Single

interface ImageDatabase {
    fun saveImageEntities(images: List<ImageEntity>)
    fun getAllImages(): Flowable<List<ImageEntity>>
    fun getImagesLessThanAndEqualPage(page: Int): Single<List<ImageEntity>>
    fun getImagesLessThanPage(page: Int): List<ImageEntity>
}