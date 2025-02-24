package com.pricematch.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.*
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pricematch.CartActivity
import com.pricematch.model.Product
import com.pricematch.R
import com.pricematch.manager.CartManager
import com.pricematch.model.CartItem

class FoodAdapter(private var foodList: List<Product>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    inner class FoodViewHolder(itemView: View, var context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val foodName: TextView = itemView.findViewById(R.id.productName)
        private val foodRating: TextView = itemView.findViewById(R.id.ratingNumber)
        private val foodWeight: TextView = itemView.findViewById(R.id.productWeight)
        private val foodImage: ImageView = itemView.findViewById(R.id.productImg)
        private val foodPriceInst: TextView = itemView.findViewById(R.id.productPriceInstmart)
        private val foodPriceZepto: TextView = itemView.findViewById(R.id.productZepto)
        private val foodPriceBlink: TextView = itemView.findViewById(R.id.productBlinkit)
        private val addButton: AppCompatButton = itemView.findViewById(R.id.addbutton)
        private val layout1: LinearLayout = itemView.findViewById(R.id.layoutInstamart)
        private val layout2: LinearLayout = itemView.findViewById(R.id.layoutBlinkit)
        private val layout3: LinearLayout = itemView.findViewById(R.id.layoutZepto)

        private lateinit var cartItem: CartItem

        @SuppressLint("SetTextI18n", "SuspiciousIndentation")
        fun bind(foodItem: Product) {
            foodName.text = foodItem.productName
            foodRating.text = foodItem.rating.toString()
            foodWeight.text = foodItem.weight
            if (foodItem.productImg.isNotEmpty()) {
                Glide.with(itemView.context)
                    .load(foodItem.productImg[1])
                    .placeholder(R.drawable.noimage)
                    .into(foodImage)
            }

            if (foodItem.prices[0].zepto != null) {
                foodPriceZepto.isVisible = true
                layout3.isVisible = true
                foodPriceZepto.text = "₹ ${foodItem.prices[0].zepto}" //245 // null.
            } else {
                foodPriceZepto.isVisible = false
                layout3.isVisible = false
            }

            if (foodItem.prices[0].blinkit != null) {
                foodPriceBlink.isVisible = true
                layout2.isVisible = true
                foodPriceBlink.text = "₹ ${foodItem.prices[0].blinkit}" //null //525
            } else {
                foodPriceBlink.isVisible = false
                layout2.isVisible = false
            }

            if (foodItem.prices[0].instmart != null) {
                foodPriceInst.isVisible = true
                layout1.isVisible = true
                foodPriceInst.text = "₹ ${foodItem.prices[0].instmart}" //256 //544
            } else {
                foodPriceInst.isVisible = false
                layout1.isVisible = false
            }
            addButton.setOnClickListener {
                var instmartPrice: Int? = null
                var zeptoPrice: Int? = null
                var blinkitPrice: Int? = null

                if (foodItem.prices[0].zepto != null) {
                    zeptoPrice = foodItem.prices[0].zepto
                }
                if (foodItem.prices[0].instmart != null) {
                    instmartPrice = foodItem.prices[0].instmart
                }
                if (foodItem.prices[0].blinkit != null) {
                    blinkitPrice = foodItem.prices[0].blinkit
                }


                cartItem = CartItem(
                    productName = foodItem.productName ?: "N/A",
                    productPrice = 0,
                    productImage = foodItem.productImg.firstOrNull() ?: "",
                    productRating = foodItem.rating ?: 0.0,
                    quantity = 1,
                    zeptoPrice = zeptoPrice ?: 0,
                    instamartPrice = instmartPrice ?: 0,
                    blinkitPrice = blinkitPrice ?: 0,
                )
                CartManager.addToCart(cartItem)
                Toast.makeText(context, "${foodItem.productName} added to cart", Toast.LENGTH_SHORT)
                    .show()
                context.startActivity(Intent(context, CartActivity::class.java))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_product, parent, false)
        return FoodViewHolder(view, parent.context)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int = foodList.size
}