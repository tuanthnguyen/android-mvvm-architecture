package com.tuann.mvvm.data.api.response

import com.squareup.moshi.Json
import org.threeten.bp.Instant
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class Image(
        val id: String,
        @Json(name = "created_at") val createdAt: Instant?,
        @Json(name = "updated_at") val updatedAt: Instant?,
        val width: Int,
        val height: Int,
        val color: String,
        val urls: ImageUrlsList,
        val user: User
)
