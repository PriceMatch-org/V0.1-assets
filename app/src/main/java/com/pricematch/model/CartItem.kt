package com.pricematch.model

data class CartItem(
    val productName: String,
    val productPrice: Int,
    val productImage: String,
    val productRating: Double,
    var quantity: Int = 0,
    val zeptoPrice: Int? = null,
    val instamartPrice: Int? = null,
    val blinkitPrice: Int? = null
)