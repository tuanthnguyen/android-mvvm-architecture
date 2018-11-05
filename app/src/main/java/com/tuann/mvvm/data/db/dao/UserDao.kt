package com.tuann.mvvm.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tuann.mvvm.data.db.entity.UserEntity

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(user: UserEntity)

    @Query("SELECT * FROM user WHERE id == :id LIMIT 1")
    abstract fun getUser(id: String): UserEntity?
}