package com.tuann.mvvm.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tuann.mvvm.data.db.entity.ImageEntity
import io.reactivex.Flowable

@Dao
abstract class ImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(image: ImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(images: List<ImageEntity>)

    @Query("SELECT * FROM image ORDER BY updated_at DESC")
    abstract fun getAllImages(): Flowable<List<ImageEntity>>

    @Query("SELECT * FROM image WHERE page <= :page ORDER BY updated_at DESC")
    abstract fun getImagesLessThanAndEqualPage(page: Int): List<ImageEntity>

    @Query("SELECT * FROM image WHERE page < :page ORDER BY updated_at DESC")
    abstract fun getImagesLessThanPage(page: Int): List<ImageEntity>
}