package com.pricematch.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "name") val name: String,
    @Json(name = "products") val products: List<Product>
)
