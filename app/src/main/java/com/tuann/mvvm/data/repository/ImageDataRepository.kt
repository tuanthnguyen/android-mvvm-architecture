package com.tuann.mvvm.data.repository

import com.tuann.mvvm.BuildConfig
import com.tuann.mvvm.data.api.ImageApi
import com.tuann.mvvm.data.api.response.mapper.toImageEntities
import com.tuann.mvvm.data.db.ImageDatabase
import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.db.entity.mapper.toImages
import com.tuann.mvvm.data.db.entity.mapper.toImagesFromEntity
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
    private val token = BuildConfig.UNSPLASH_TOKEN
    override val images: Flowable<List<Image>>
        get() = imageDatabase.getAllImages()
            .toImages()

    override fun loadImages(page: Int): Single<List<Image>> =
        Single.zip(loadImagesApi(page), loadImagesDb(page), BiFunction { t1, t2 ->
            if (t1.isNotEmpty()) {
                val imageEntities = ArrayList<Image>()
                imageEntities.addAll(imageDatabase.getImagesLessThanPage(page - 1).toImagesFromEntity())
                imageEntities.addAll(t1)
                return@BiFunction imageEntities.toList()
            } else return@BiFunction t2
        })

    private fun loadImagesDb(page: Int): Single<List<Image>> =
        imageDatabase.getImagesLessThanAndEqualPage(page)
            .map {
                return@map it.toImagesFromEntity()
            }
            .subscribeOn(schedulerProvider.io())

    override fun loadImagesApi(page: Int): Single<List<Image>> =
        api.loadTrendingImages(token, MAX_PER_PAGE, ORDER_BY, page)
            .map {
                val newImages = it.toImageEntities(page)
                saveImages(newImages, page).subscribe()
                return@map newImages.toList().toImagesFromEntity()
            }
            .onErrorReturn { error ->
                Timber.e(error.toString())

                return@onErrorReturn emptyList<Image>()
            }
            .subscribeOn(schedulerProvider.io())

    override fun saveImages(images: List<ImageEntity>, page: Int): Completable =
        Completable.create {
            imageDatabase.saveImageEntities(images, page)
            it.onComplete()
        }

    companion object {
        private const val MAX_PER_PAGE = 20
        private const val ORDER_BY = "latest"
    }
}