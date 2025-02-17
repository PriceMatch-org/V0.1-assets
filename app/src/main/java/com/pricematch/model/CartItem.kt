package com.pricematch.model

data class CartItem(
    val productName: String,
    val productPrice: Int,
    val productImage: String,
    val productRating: Double,
    var quantity: Int = 1
)