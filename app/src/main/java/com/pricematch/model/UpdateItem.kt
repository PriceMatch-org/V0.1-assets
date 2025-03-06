package com.pricematch.model

import com.squareup.moshi.Json

data class UpdateItem (
    @Json(name = "imageResId") val imageResId: Int,
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String
)