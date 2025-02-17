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

    fun getCartItems(): List<CartItem> {
        return cartItem
    }

    fun calculateTotalPrice(): Int {
        return cartItem.sumOf { it.productPrice * it.quantity }
    }

    fun clearCart() {
        cartItem.clear()
    }
}