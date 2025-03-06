package com.pricematch.api

import com.pricematch.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(
        @Header("api-key") apiKey: String,
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 10
    ): ApiResponse
}
