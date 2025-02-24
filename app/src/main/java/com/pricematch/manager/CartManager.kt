package com.pricematch.manager

import com.pricematch.model.CartItem

object CartManager {

    private val cartItem = mutableListOf<CartItem>()

    fun addToCart(item: CartItem) {
        val existingItem = cartItem.find { it.productName == item.productName }
        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            cartItem.add(item)
        }
    }

    fun updateCartItem(item: CartItem) {
        val existingItem = cartItem.find { it.productName == item.productName }
        existingItem?.quantity = item.quantity
    }

    fun removeFromCart(item: CartItem) {
        cartItem.remove(item)
    }

    fun getCartItems(): List<CartItem> {
        return cartItem
    }

    fun calculateTotalPrice(selectedPlatform: String?): Int {
        return cartItem.sumOf { item ->
            when (selectedPlatform) {
                "instamart" -> (item.instamartPrice ?: 0) * item.quantity
                "blinkit" -> (item.blinkitPrice ?: 0) * item.quantity
                "zepto" -> (item.zeptoPrice ?: 0) * item.quantity
                else -> 0
            }
        }
    }

    fun clearCart() {
        cartItem.clear()
    }
}