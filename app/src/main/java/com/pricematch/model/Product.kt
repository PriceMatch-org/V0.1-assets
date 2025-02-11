package com.pricematch.model

data class Product(
    val productName : String?,
    val category : String?,
    val weight : String?,
    val rating : Double?,
    val productImg : List<String>?,
    val price: List<Price>?
) {
}