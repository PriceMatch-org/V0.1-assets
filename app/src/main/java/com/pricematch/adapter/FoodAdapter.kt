package com.pricematch.adapter

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
    inner class FoodViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(itemView) {
        private val foodName: TextView = itemView.findViewById(R.id.productName)
        private val foodRating: TextView = itemView.findViewById(R.id.ratingNumber)
        private val foodWeight: TextView = itemView.findViewById(R.id.productWeight)
        private val foodImage: ImageView = itemView.findViewById(R.id.productImg)
        private val foodPriceInsta: TextView = itemView.findViewById(R.id.productPriceInstmart)
        private val foodPriceZepto: TextView = itemView.findViewById(R.id.productZepto)
        private val foodPriceBlinkit: TextView = itemView.findViewById(R.id.productBlinkit)
        private val addButton: AppCompatButton = itemView.findViewById(R.id.addbutton)
        //layout Content
        private val layout1 : LinearLayout = itemView.findViewById(R.id.layoutInstamart)
        private val layout2 : LinearLayout = itemView.findViewById(R.id.layoutBlinkit)
        private val layout3 : LinearLayout = itemView.findViewById(R.id.layoutZepto)

        fun bind(foodItem: Product) {
            foodName.text = foodItem.productName ?: "N/A"
            foodRating.text = foodItem.rating?.toString() ?: "0.0"
            foodWeight.text = foodItem.weight ?: "N/A"
            // Load Image Using Glide
                if (!foodItem.productImg.isNullOrEmpty()) {
                    Glide.with(itemView.context)
                        .load(foodItem.productImg[1])
                        .placeholder(R.drawable.noimage)
                        .into(foodImage)
                }
            // Display Prices
            val priceText = StringBuilder()
            foodItem.prices.forEach { price ->
                if(price.instmart != null){
                    foodPriceInsta.isVisible = true
                    layout1.isVisible = true
                    foodPriceInsta.text = "₹ ${price.instmart}"
                } else if(price.blinkit != null){
                    foodPriceBlinkit.isVisible = true
                    layout2.isVisible = true
                    foodPriceBlinkit.text = "₹ ${price.blinkit}"
                } else {
                    layout3.isVisible = true
                    foodPriceZepto.isVisible = true
                    foodPriceZepto.text = "₹ ${price.zepto}"
                }
            }
            //foodPrice.text = priceText.toString().trim()
            // Add to Cart Button Click Listener
            addButton.setOnClickListener {
                val cartItem = CartItem(
                    productName = foodItem.productName ?: "N/A",
                    productPrice = foodItem.prices.firstOrNull()?.instmart ?: 0,
                    productImage = foodItem.productImg.firstOrNull() ?: "",
                    productRating = foodItem.rating ?: 0.0,
                    quantity = 1
                )
                CartManager.addToCart(cartItem)
                Toast.makeText(context, "${foodItem.productName} added to cart", Toast.LENGTH_SHORT).show()
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
