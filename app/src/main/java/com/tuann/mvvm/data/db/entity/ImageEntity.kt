package com.tuann.mvvm.data.db.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import org.threeten.bp.Instant

@Entity(tableName = "image",
    foreignKeys = [(ForeignKey(entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = CASCADE))
    ]
)
data class ImageEntity(
    @PrimaryKey var id: String,
    @ColumnInfo(name = "created_at") var createdAt: Instant?,
    @ColumnInfo(name = "updated_at") var updatedAt: Instant?,
    var width: Int,
    var height: Int,
    var color: String,
    @Embedded var urls: ImageUrlsListEntity,
    @ColumnInfo(name = "user_id") var userId: String,
    var page: Int,
    @Ignore var userEntity: UserEntity? = UserEntity()
) {
    constructor() : this("", null, null, 0, 0, "", ImageUrlsListEntity(), "", 0, UserEntity())
}

data class ImageUrlsListEntity(
    var raw: String = "",
    var full: String = "",
    var regular: String = "",
    var small: String = "",
    var thumb: String = ""
)