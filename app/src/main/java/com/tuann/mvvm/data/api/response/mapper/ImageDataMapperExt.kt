package com.tuann.mvvm.data.api.response.mapper

import com.tuann.mvvm.data.api.response.Image
import com.tuann.mvvm.data.api.response.ImageUrlsList
import com.tuann.mvvm.data.api.response.ProfileImage
import com.tuann.mvvm.data.api.response.User
import com.tuann.mvvm.data.db.entity.ImageEntity
import com.tuann.mvvm.data.db.entity.ImageUrlsListEntity
import com.tuann.mvvm.data.db.entity.ProfileImageEntity
import com.tuann.mvvm.data.db.entity.UserEntity

fun List<Image>.toImageEntities(page: Int): List<ImageEntity> =
        map {
            it.toImageEntity(page)
        }

fun Image.toImageEntity(page: Int): ImageEntity {
    return ImageEntity(id, createdAt, updatedAt, width, height, color, urls.toImageUrlsListEntity(), user.id, page, user.toUserEntity())
}

fun ImageUrlsList.toImageUrlsListEntity(): ImageUrlsListEntity =
        ImageUrlsListEntity(raw, full, regular, small, thumb)

fun User.toUserEntity(): UserEntity =
        UserEntity(id, username, name, profileImage.toProfileImageEntity())

fun ProfileImage.toProfileImageEntity(): ProfileImageEntity =
        ProfileImageEntity(small, medium, large)
