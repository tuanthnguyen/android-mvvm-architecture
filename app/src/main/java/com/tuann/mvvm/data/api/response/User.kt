package com.tuann.mvvm.data.api.response

import com.squareup.moshi.Json
import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class User(
        val id: String,
        val username: String,
        val name: String,
        @Json(name = "profile_image") val profileImage: ProfileImage
)

@JsonSerializable
data class ProfileImage(
        val small: String,
        val medium: String,
        val large: String
)