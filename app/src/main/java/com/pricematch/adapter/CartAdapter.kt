package com.pricematch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pricematch.CartActivity
import com.pricematch.model.CartItem
import com.pricematch.R
import com.pricematch.manager.CartManager


class CartAdapter(private val cartItems: List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val CartProductImage: ImageView = itemView.findViewById(R.id.prodImg)


        private val CartProductName: TextView = itemView.findViewById(R.id.prodName)
        private val CartProductRating: TextView = itemView.findViewById(R.id.prodRate)
        private val CartProductPrice: TextView = itemView.findViewById(R.id.prodPrice)
        private val CartProductQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        private val btnInc: TextView = itemView.findViewById(R.id.btnIncrease)
        private val btnDec: TextView = itemView.findViewById(R.id.btnDecrease)

        fun bind(cartItem: CartItem) {
            CartProductName.text = cartItem.productName
            CartProductRating.text = cartItem.productRating.toString()
            CartProductPrice.text = "₹ ${cartItem.productPrice}"
            CartProductQuantity.text = cartItem.quantity.toString()

            Glide.with(itemView.context)
                .load(cartItem.productImage)
                .placeholder(R.drawable.noimage)
                .into(CartProductImage)

            btnInc.setOnClickListener {
                cartItem.quantity++
                CartProductQuantity.text = cartItem.quantity.toString()
                CartManager.updateCartItem(cartItem)
                (itemView.context as CartActivity).updateTotalPrice()
            }

            btnDec.setOnClickListener {
                if (cartItem.quantity > 1) {
                    cartItem.quantity--
                    CartProductQuantity.text = cartItem.quantity.toString()
                    CartManager.updateCartItem(cartItem)
                    (itemView.context as CartActivity).updateTotalPrice()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartItems[position])
    }

    override fun getItemCount(): Int = cartItems.size
}