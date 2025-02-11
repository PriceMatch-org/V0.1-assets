package com.pricematch

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.pricematch.databinding.ActivityFoodDeliveryBinding

class FoodDeliveryActivity : AppCompatActivity() {

    private lateinit var bind : ActivityFoodDeliveryBinding
    private lateinit var backBtn : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFoodDeliveryBinding.inflate(layoutInflater)
        setContentView(bind.root)

    }
}