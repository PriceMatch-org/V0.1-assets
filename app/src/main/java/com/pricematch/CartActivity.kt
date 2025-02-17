package com.pricematch

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pricematch.adapter.CartAdapter
import com.pricematch.databinding.ActivityCartBinding
import com.pricematch.manager.CartManager

class CartActivity : AppCompatActivity() {

    private lateinit var bind: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityCartBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setupRecyclerView()
        updateTotalPrice()

        bind.buyBtn.setOnClickListener {
            CartManager.clearCart()
            Toast.makeText(this, "Purchase successful!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(CartManager.getCartItems())
        bind.recyclerView2.layoutManager = LinearLayoutManager(this)
        bind.recyclerView2.adapter = cartAdapter
    }

    fun updateTotalPrice() {
        val totalPrice = CartManager.calculateTotalPrice()
        bind.textView3.text = "₹ $totalPrice"
    }
}