package com.pricematch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.pricematch.model.Product
import com.pricematch.R

class FoodAdapter(private var foodList: List<Product>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foodName: TextView = itemView.findViewById(R.id.productName)
        private val foodRating: RatingBar = itemView.findViewById(R.id.ratingNumber)
        private val foodWeight: TextView = itemView.findViewById(R.id.productWeight)
        private val foodImage: ImageView = itemView.findViewById(R.id.productImg)
        private val foodPriceInsta: TextView = itemView.findViewById(R.id.productPriceInstmart)
        private val foodPriceZepto: TextView = itemView.findViewById(R.id.productZepto)
        private val foodPriceBlinkit: TextView = itemView.findViewById(R.id.productBlinkit)

        fun bind(foodItem: Product) {
            foodName.text = foodItem.productName ?: "N/A"
            foodRating.rating = foodItem.rating?.toFloat() ?: 0.0f
            foodWeight.text = foodItem.weight ?: "N/A"

            // Load Image Using Glide
            if (!foodItem.productImg.isNullOrEmpty()) {
//                Glide.with(itemView.context)
//                    .load(foodItem.productImg[0])
//                    .into(foodImage)
            }

            // Display Prices
            val priceText = StringBuilder()
            foodItem.price?.forEach { price ->
               if(price.instmart != null){
                   foodPriceInsta.isVisible = true
                   foodPriceInsta.text = "Instamart ${price.instmart}"
               } else if(price.blinkit != null){
                   foodPriceBlinkit.isVisible = true
                   foodPriceBlinkit.text = "Blinkit ${price.blinkit}"
               } else {
                   foodPriceZepto.isVisible = true
                   foodPriceZepto.text = "Zepto ${price.zepto}"
               }
            }
            //foodPrice.text = priceText.toString().trim()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_product, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList[position])
    }

    override fun getItemCount(): Int = foodList.size

    fun updateList(newList: List<Product>) {
        foodList = newList
        notifyDataSetChanged()
    }
}
