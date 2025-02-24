package com.pricematch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pricematch.CartActivity
import com.pricematch.model.CartItem
import com.pricematch.R
import com.pricematch.manager.CartManager

class CartAdapter(
    private val cartItems: List<CartItem>,
    private val cartActivity: CartActivity
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val CartProductImage: ImageView = itemView.findViewById(R.id.prodImg)
        private val CartProductName: TextView = itemView.findViewById(R.id.prodName)
        private val CartProductRating: TextView = itemView.findViewById(R.id.prodRate)
        private val CartProductQuantity: TextView = itemView.findViewById(R.id.tvQuantity)
        private val CartProductInstmart: TextView = itemView.findViewById(R.id.productPriceInstmart)
        private val CartProductZepto: TextView = itemView.findViewById(R.id.productZepto)
        private val CartProductBlinkit: TextView = itemView.findViewById(R.id.productBlinkit)
        private val btnInc: TextView = itemView.findViewById(R.id.btnIncrease)
        private val btnDec: TextView = itemView.findViewById(R.id.btnDecrease)

        private val zeptoLayout: LinearLayout = itemView.findViewById(R.id.layoutZepto)
        private val blinkitLayout: LinearLayout = itemView.findViewById(R.id.layoutBlinkit)
        private val instamartLayout: LinearLayout = itemView.findViewById(R.id.layoutInstamart)

        fun bind(cartItem: CartItem) {
            CartProductName.text = cartItem.productName
            CartProductRating.text = cartItem.productRating.toString()
            CartProductQuantity.text = cartItem.quantity.toString()

            Glide.with(itemView.context)
                .load(cartItem.productImage)
                .placeholder(R.drawable.noimage)
                .into(CartProductImage)

            if (cartItem.blinkitPrice != null && cartItem.blinkitPrice != 0) {
                CartProductBlinkit.text = "₹ ${cartItem.blinkitPrice}"
                CartProductBlinkit.isVisible = true
                blinkitLayout.isVisible = true
            }

            if (cartItem.zeptoPrice != null && cartItem.zeptoPrice != 0) {
                CartProductZepto.text = "₹ ${cartItem.zeptoPrice}"
                CartProductZepto.isVisible = true
                zeptoLayout.isVisible = true
            }

            if (cartItem.instamartPrice != null && cartItem.instamartPrice != 0) {
                CartProductInstmart.text = "₹ ${cartItem.instamartPrice}"
                CartProductInstmart.isVisible = true
                instamartLayout.isVisible = true
            }

            btnInc.setOnClickListener {
                cartItem.quantity++
                CartProductQuantity.text = cartItem.quantity.toString()
                CartManager.updateCartItem(cartItem)
                cartActivity.refreshRadioButtons()
            }

            btnDec.setOnClickListener {
                if (cartItem.quantity > 1) {
                    cartItem.quantity--
                    CartProductQuantity.text = cartItem.quantity.toString()
                    CartManager.updateCartItem(cartItem)
                    cartActivity.refreshRadioButtons()
                } else if (cartItem.quantity == 1) {
                    CartManager.removeFromCart(cartItem)
                    cartActivity.refreshRadioButtons()
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