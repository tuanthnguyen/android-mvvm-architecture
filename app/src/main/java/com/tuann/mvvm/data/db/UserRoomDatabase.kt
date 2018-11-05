package com.tuann.mvvm.data.db

import androidx.room.RoomDatabase
import com.tuann.mvvm.data.db.dao.UserDao
import javax.inject.Inject

class UserRoomDatabase @Inject constructor(
        private val database: RoomDatabase,
        private val userDao: UserDao
): UserDatabase