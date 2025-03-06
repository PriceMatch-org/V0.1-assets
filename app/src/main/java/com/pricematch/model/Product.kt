package com.pricematch.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "productName") val productName: String,
    @Json(name = "weight") val weight: String,
    @Json(name = "rating") val rating: Double,
    @Json(name = "productImg") val productImg: List<String>,
    @Json(name = "prices") val prices: List<Price>
)
