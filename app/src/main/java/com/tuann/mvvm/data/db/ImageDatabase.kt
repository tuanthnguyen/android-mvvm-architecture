package com.tuann.mvvm.data.db

import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.db.entity.ImageWithUser
import io.reactivex.Flowable
import io.reactivex.Single

interface ImageDatabase {
    fun saveImageEntities(images: List<ImageEntity>, page: Int)
    fun getAllImages(): Flowable<List<ImageWithUser>>
    fun getImagesLessThanAndEqualPage(page: Int): Single<List<ImageEntity>>
    fun getImagesLessThanPage(page: Int): List<ImageEntity>
}