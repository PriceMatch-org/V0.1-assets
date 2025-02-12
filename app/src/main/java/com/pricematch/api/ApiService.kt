package com.pricematch.api

import com.pricematch.model.ApiResponse
import retrofit2.*
import retrofit2.http.*

interface ApiService {
    @GET("products")
    fun getProducts(@Header("api-key") apiKey : String) : Call<ApiResponse>
}