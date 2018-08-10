package com.tuann.mvvm.data.api.response

import se.ansman.kotshi.JsonSerializable

@JsonSerializable
data class ImageUrlsList(
        val raw: String,
        val full: String,
        val regular: String,
        val small: String,
        val thumb: String
)