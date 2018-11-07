package com.tuann.mvvm.data.db

import androidx.room.RoomDatabase
import com.tuann.mvvm.data.db.dao.ImageDao
import com.tuann.mvvm.data.db.dao.UserDao
import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.db.entity.ImageWithUser
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ImageRoomDatabase @Inject constructor(
        private val database: RoomDatabase,
        private val imageDao: ImageDao,
        private val userDao: UserDao
) : ImageDatabase {

    override fun saveImageEntities(images: List<ImageEntity>, page: Int) {
        database.runInTransaction {
            imageDao.deleteImagesByPage(page)
            for (image in images) {
                userDao.insert(image.userEntity!!)
                imageDao.insert(image)
            }
        }
    }

    override fun getAllImages(): Flowable<List<ImageWithUser>> =
            imageDao.getAllImages()

    override fun getImagesLessThanAndEqualPage(page: Int): Single<List<ImageEntity>> =
            Flowable.just(imageDao.getImagesLessThanAndEqualPage(page))
                    .flatMapIterable {
                        return@flatMapIterable it
                    }
                    .map {
                        it.userEntity = userDao.getUser(it.userId)
                        return@map it
                    }
                    .toList()

    override fun getImagesLessThanPage(page: Int): List<ImageEntity> {
        val data = imageDao.getImagesLessThanPage(page)
        data.forEach {
            it.userEntity = userDao.getUser(it.userId)
        }
        return data
    }
}