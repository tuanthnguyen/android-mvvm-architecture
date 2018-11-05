package com.tuann.mvvm.data.repository

import com.tuann.mvvm.data.api.ImageApi
import com.tuann.mvvm.data.api.response.mapper.toImageEntities
import com.tuann.mvvm.data.db.ImageDatabase
import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.db.entity.mapper.toImages
import com.tuann.mvvm.data.model.Image
import com.tuann.mvvm.util.rx.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject

class ImageDataRepository @Inject constructor(
        private val api: ImageApi,
        private val imageDatabase: ImageDatabase,
        private val schedulerProvider: SchedulerProvider
) : ImageRepository {
    override val images: Flowable<List<Image>>
        get() = imageDatabase.getAllImages().toImages()

    override fun loadImages(page: Int): Single<List<Image>> =
            Single.zip(loadImagesApi(page), loadImagesDb(page), BiFunction { t1, t2 ->
                if (t1.isNotEmpty()) return@BiFunction t1
                else return@BiFunction t2
            })

    private fun loadImagesDb(page: Int): Single<List<Image>> =
            imageDatabase.getImagesLessThanAndEqualPage(page)
                    .map {
                        return@map it.toImages()
                    }
                    .subscribeOn(schedulerProvider.io())

    override fun loadImagesApi(page: Int): Single<List<Image>> =
            api.loadTrendingImages(TOKEN, MAX_PER_PAGE, ORDER_BY, page)
                    .map {
                        val imageEntities = ArrayList<ImageEntity>()
                        imageEntities.addAll(imageDatabase.getImagesLessThanPage(page))
                        val newImages = it.toImageEntities(page)
                        imageEntities.addAll(newImages)
                        saveImages(newImages).subscribe()

                        return@map imageEntities.toList().toImages()
                    }
                    .onErrorReturn { error ->
                        Timber.e(error.toString())

                        return@onErrorReturn emptyList<Image>() }
                    .subscribeOn(schedulerProvider.io())

    override fun saveImages(images: List<ImageEntity>): Completable =
            Completable.create {
                imageDatabase.saveImageEntities(images)
                it.onComplete()
            }

    companion object {
        private const val TOKEN = "Client-ID 1b31050af4345f3d6aea1ed27560569d0ffa6e5a365989e15970939505a171c5"
        private const val MAX_PER_PAGE = 20
        private const val ORDER_BY = "latest"
    }
}