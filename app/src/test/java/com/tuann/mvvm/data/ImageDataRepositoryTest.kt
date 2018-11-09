package com.tuann.mvvm.data

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.tuann.mvvm.*
import com.tuann.mvvm.DummyDataCreator.createDummyImageEntities
import com.tuann.mvvm.DummyDataCreator.createDummyResponseImage
import com.tuann.mvvm.data.api.ImageApi
import com.tuann.mvvm.data.api.response.Image
import com.tuann.mvvm.data.api.response.mapper.toImageEntities
import com.tuann.mvvm.data.db.ImageDatabase
import com.tuann.mvvm.data.db.entity.ImageWithUser
import com.tuann.mvvm.data.db.entity.mapper.toImages
import com.tuann.mvvm.data.db.entity.mapper.toImagesFromEntity
import com.tuann.mvvm.data.repository.ImageDataRepository
import com.tuann.mvvm.util.rx.TestSchedulerProvider
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

const val DUMMY_PAGE = 1
const val DUMMY_TOKEN = BuildConfig.UNSPLASH_TOKEN
const val DUMMY_PER_PAGE = 20
const val DUMMY_ORDER_BY = "latest"

@RunWith(RobolectricTestRunner::class)
class ImageDataRepositoryTest {

    private val imageDatabase: ImageDatabase = mock()
    private val imageApi: ImageApi = mock()

    @Before
    fun init() {
        whenever(imageDatabase.getAllImages()).doReturn(Flowable.just(mock()))
        whenever(imageApi.loadTrendingImages(DUMMY_TOKEN, DUMMY_PER_PAGE, DUMMY_ORDER_BY, DUMMY_PAGE)).doReturn(Single.just(mock()))
    }

    @Test
    fun images() {
        val images = listOf(
            ImageWithUser(DUMMY_IMAGE_ID1,
                DUMMY_TIME.toEpochMilli(),
                DUMMY_TIME.toEpochMilli(),
                DUMMY_WIDTH,
                DUMMY_HEIGHT,
                DUMMY_COLOR,
                DUMMY_SMALL_IMAGE_URL,
                DUMMY_FULL_IMAGE_URL,
                DUMMY_USER_ID,
                DUMMY_USER_NAME),
            ImageWithUser(DUMMY_IMAGE_ID2,
                DUMMY_TIME.toEpochMilli(),
                DUMMY_TIME.toEpochMilli(),
                DUMMY_WIDTH,
                DUMMY_HEIGHT,
                DUMMY_COLOR,
                DUMMY_SMALL_IMAGE_URL,
                DUMMY_FULL_IMAGE_URL,
                DUMMY_USER_ID,
                DUMMY_USER_NAME)
        )
        whenever(imageDatabase.getAllImages()).doReturn(Flowable.just(images))

        val imageDataRepository = ImageDataRepository(mock(),
            imageDatabase,
            TestSchedulerProvider())

        imageDataRepository
            .images
            .test()
            .assertValue(images.toImages())

        verify(imageDatabase).getAllImages()
    }

    @Test
    fun loadImagesWhenImagesFromApiSuccessfully() {
        val imagesLessThanAndEqualPage = createDummyImageEntities()
        val imagesFromApi = createDummyResponseImage()
        val imagesLessThan = createDummyImageEntities()
        whenever(imageDatabase.getImagesLessThanPage(DUMMY_PAGE - 1)).doReturn(imagesLessThan)
        whenever(imageDatabase.getImagesLessThanAndEqualPage(DUMMY_PAGE)).doReturn(Single.just(imagesLessThanAndEqualPage))
        whenever(imageApi.loadTrendingImages(DUMMY_TOKEN, DUMMY_PER_PAGE, DUMMY_ORDER_BY, DUMMY_PAGE))
            .doReturn(Single.just(imagesFromApi))

        val imageDataRepository = ImageDataRepository(imageApi,
            imageDatabase,
            TestSchedulerProvider())

        imageDataRepository
            .loadImages(DUMMY_PAGE)
            .test()
            .assertValue(
                imagesFromApi.toImageEntities(DUMMY_PAGE).toImagesFromEntity().let {
                    if (it.isNotEmpty()) {
                        imagesLessThan.toImagesFromEntity() + it
                    } else {
                        imagesLessThanAndEqualPage.toImagesFromEntity()
                    }
                }
            )
    }

    @Test
    fun loadImagesWhenImagesFromApiError() {
        val imagesLessThanAndEqualPage = createDummyImageEntities()
        val imagesFromApi = emptyList<Image>()
        val imagesLessThan = createDummyImageEntities()
        whenever(imageDatabase.getImagesLessThanPage(DUMMY_PAGE - 1)).doReturn(imagesLessThan)
        whenever(imageDatabase.getImagesLessThanAndEqualPage(DUMMY_PAGE)).doReturn(Single.just(imagesLessThanAndEqualPage))
        whenever(imageApi.loadTrendingImages("", DUMMY_PER_PAGE, DUMMY_ORDER_BY, DUMMY_PAGE))
            .thenReturn(Single.just(imagesFromApi))

        val imageDataRepository = ImageDataRepository(imageApi,
            imageDatabase,
            TestSchedulerProvider())

        imageDataRepository
            .loadImages(DUMMY_PAGE)
            .test()
            .assertValue(
                imagesFromApi.toImageEntities(DUMMY_PAGE).toImagesFromEntity().let {
                    if (it.isNotEmpty()) {
                        imagesLessThan.toImagesFromEntity() + it
                    } else {
                        imagesLessThanAndEqualPage.toImagesFromEntity()
                    }
                }
            )
    }
}