package com.pricematch

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
        setupRadioButtons()
        updateTotalPrice()

        bind.idBack.setOnClickListener {
            startActivity(Intent(this, FoodDashboardActivity::class.java))
            finish()
        }

        bind.buyBtn.setOnClickListener {
            CartManager.clearCart()
            Toast.makeText(this, "Purchase successful!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Set up radio button listeners
        bind.radioGroup.setOnCheckedChangeListener { _, _ ->
            updateTotalPrice()
        }
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(CartManager.getCartItems(), this)
        bind.recyclerView2.layoutManager = LinearLayoutManager(this)
        bind.recyclerView2.adapter = cartAdapter
    }

    private fun setupRadioButtons() {
        val cartItems = CartManager.getCartItems()

        // Check if any item has prices for Instamart, Blinkit, or Zepto
        val hasInstamart = cartItems.any { it.instamartPrice != null && it.instamartPrice != 0 }
        val hasBlinkit = cartItems.any { it.blinkitPrice != null && it.blinkitPrice != 0 }
        val hasZepto = cartItems.any { it.zeptoPrice != null && it.zeptoPrice != 0 }

        // Set visibility of radio buttons based on availability
        bind.rbInstamart.visibility = if (hasInstamart) View.VISIBLE else View.GONE
        bind.rbBlinkit.visibility = if (hasBlinkit) View.VISIBLE else View.GONE
        bind.rbZepto.visibility = if (hasZepto) View.VISIBLE else View.GONE

        // Set text and drawable for Instamart radio button
//        if (hasInstamart && cartItems.isNotEmpty()) {
//            bind.rbInstamart.text = "Instamart ${cartItems[0].instamartPrice}"
//            val instamartDrawable = ContextCompat.getDrawable(this, R.drawable.instamart)
//            bind.rbInstamart.setCompoundDrawablesWithIntrinsicBounds(instamartDrawable, null, null, null)
//            bind.rbInstamart.compoundDrawablePadding = 5
//        }


        // Bind prices to radio button values
        if (hasInstamart) {
//            bind.rvInst.text = "₹ ${cartItems.sumOf { (it.instamartPrice ?: 0) * it.quantity }}"
        }
        if (hasBlinkit) {
//            bind.rvBlinkit.text = "₹ ${cartItems.sumOf { (it.blinkitPrice ?: 0) * it.quantity }}"
        }
        if (hasZepto) {
//            bind.rvzepto.text = "₹ ${cartItems.sumOf { (it.zeptoPrice ?: 0) * it.quantity }}"
        }

        // Set default selection if any platform is available
        when {
            hasInstamart -> bind.rbInstamart.isChecked = true
            hasBlinkit -> bind.rbBlinkit.isChecked = true
            hasZepto -> bind.rbZepto.isChecked = true
            else -> bind.radioGroup.clearCheck() // Clear selection if no platform is available
        }
    }

    fun updateTotalPrice() {
        val selectedPlatform = when (bind.radioGroup.checkedRadioButtonId) {
            R.id.rbInstamart -> "instamart"
            R.id.rbBlinkit -> "blinkit"
            R.id.rbZepto -> "zepto"
            else -> {
                // If no radio button is selected, default to the first available platform
                when {
//                    bind.constInstamartLayout.visibility == View.VISIBLE -> "instamart"
//                    bind.constBlintkitLayout.visibility == View.VISIBLE -> "blinkit"
//                    bind.constZeptoLayout.visibility == View.VISIBLE -> "zepto"
                    else -> null // No platform available
                }
            }
        }

        val totalPrice = CartManager.calculateTotalPrice(selectedPlatform)
        bind.textView3.text = "₹ $totalPrice"
    }

    fun refreshRadioButtons() {
        setupRadioButtons()
        updateTotalPrice()
    }
}