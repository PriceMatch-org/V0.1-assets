package com.pricematch.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Price(
    @Json(name = "instmart") val instmart: Int?,
    @Json(name = "blinkit") val blinkit: Int?,
    @Json(name = "zepto") val zepto: Int?
)
