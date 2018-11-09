package com.tuann.mvvm.data.db.entity

data class ImageWithUser(
    var id: String,
    var createdAt: Long?,
    var updatedAt: Long?,
    var width: Int,
    var height: Int,
    var color: String,
    var small: String,
    var full: String,
    var userId: String,
    var authorName: String
)