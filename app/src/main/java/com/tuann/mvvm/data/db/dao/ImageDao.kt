package com.tuann.mvvm.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.db.entity.ImageWithUser
import io.reactivex.Flowable

@Dao
abstract class ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(image: ImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(images: List<ImageEntity>)

    @Query("SELECT image.id as `id`, image.created_at as `createdAt`, " +
        "image.updated_at as `updatedAt`, image.width as `width`, image.height as `height`, " +
        "image.color as `color`, image.small as `small`, image.full as `full`, " +
        "u.id as `userId`, u.name as `authorName` " +
        "FROM image INNER JOIN user as u " +
        "ON image.user_id = u.id " +
        "ORDER BY image.updated_at DESC")
    abstract fun getAllImages(): Flowable<List<ImageWithUser>>

    @Query("SELECT * FROM image WHERE page <= :page ORDER BY updated_at DESC")
    abstract fun getImagesLessThanAndEqualPage(page: Int): List<ImageEntity>

    @Query("SELECT * FROM image WHERE page < :page ORDER BY updated_at DESC")
    abstract fun getImagesLessThanPage(page: Int): List<ImageEntity>

    @Query("DELETE FROM image WHERE page = :page")
    abstract fun deleteImagesByPage(page: Int)
}