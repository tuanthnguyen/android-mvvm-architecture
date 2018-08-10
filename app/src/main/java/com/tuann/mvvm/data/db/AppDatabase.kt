package com.tuann.mvvm.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.tuann.mvvm.data.db.dao.ImageDao
import com.tuann.mvvm.data.db.dao.UserDao
import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.db.entity.UserEntity
import com.tuann.mvvm.data.db.entity.mapper.Converters

@Database(
        entities = [
            ImageEntity::class,
            UserEntity::class
        ],
        version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun imageDao(): ImageDao
    abstract fun userDao(): UserDao
}