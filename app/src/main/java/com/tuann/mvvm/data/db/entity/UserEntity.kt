package com.tuann.mvvm.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
        @PrimaryKey var id: String = "",
        var username: String = "",
        var name: String = "",
        @Embedded var profileImageEntity: ProfileImageEntity = ProfileImageEntity()
)

data class ProfileImageEntity(
        @ColumnInfo(name = "profile_user_small") var small: String = "",
        @ColumnInfo(name = "profile_user_medium") var medium: String = "",
        @ColumnInfo(name = "profile_user_large") var large: String = ""
)