package com.tuann.mvvm.data.api

import com.tuann.mvvm.data.api.response.Image
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ImageApi {

    @GET("photos")
    fun loadTrendingImages(@Header("Authorization") token: String,
                           @Query("per_page") picNumPerPage: Int,
                           @Query("order_by") orderBy: String,
                           @Query("page") page: Int): Single<List<Image>>
}