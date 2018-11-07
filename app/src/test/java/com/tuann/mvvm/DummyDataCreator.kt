package com.tuann.mvvm

import com.tuann.mvvm.data.DUMMY_PAGE
import com.tuann.mvvm.data.api.response.Image
import com.tuann.mvvm.data.api.response.ImageUrlsList
import com.tuann.mvvm.data.api.response.ProfileImage
import com.tuann.mvvm.data.api.response.User
import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.db.entity.ImageUrlsListEntity
import org.threeten.bp.OffsetDateTime

const val DUMMY_USER_ID = "1"
const val DUMMY_IMAGE_ID1 = "1"
const val DUMMY_IMAGE_ID2 = "2"
val DUMMY_TIME = OffsetDateTime.parse("2018-11-04T11:01:34-05:00").toInstant()!!
const val DUMMY_WIDTH = 800
const val DUMMY_HEIGHT = 1200
const val DUMMY_COLOR = "#DC8930"
const val DUMMY_SMALL_IMAGE_URL = "small image url"
const val DUMMY_FULL_IMAGE_URL = "small image url"
const val DUMMY_USER_NAME = "Dan"
const val DUMMY_AUTHOR_NAME = "Dan Nguyen"

object DummyDataCreator {

    fun createDummyImageEntities(): List<ImageEntity> {
        return listOf(
                ImageEntity(DUMMY_IMAGE_ID1,
                        DUMMY_TIME,
                        DUMMY_TIME,
                        DUMMY_WIDTH,
                        DUMMY_HEIGHT,
                        DUMMY_COLOR,
                        ImageUrlsListEntity(),
                        DUMMY_USER_ID,
                        DUMMY_PAGE),
                ImageEntity(DUMMY_IMAGE_ID2,
                        DUMMY_TIME,
                        DUMMY_TIME,
                        DUMMY_WIDTH,
                        DUMMY_HEIGHT,
                        DUMMY_COLOR,
                        ImageUrlsListEntity(),
                        DUMMY_USER_ID,
                        DUMMY_PAGE)
        )
    }

    fun createDummyResponseImage(): List<Image> {
        return listOf(
                Image(DUMMY_IMAGE_ID1,
                        DUMMY_TIME,
                        DUMMY_TIME,
                        DUMMY_WIDTH,
                        DUMMY_HEIGHT,
                        DUMMY_COLOR,
                        ImageUrlsList("", "", "", "", ""),
                        User(DUMMY_USER_ID, DUMMY_USER_NAME, DUMMY_AUTHOR_NAME, ProfileImage("", "", "")))
        )
    }
}