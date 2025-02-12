package com.pricematch.model

data class Product(
    val productName: String,
    val weight: String,
    val rating: Double,
    val productImg: List<String>,
    val prices: List<Price>
)