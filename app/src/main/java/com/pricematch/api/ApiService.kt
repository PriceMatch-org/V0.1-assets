package com.pricematch.api

import com.pricematch.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("products")
    fun getProducts(@Header("api-key") apiKey : String) : Call<List<Product>>
}