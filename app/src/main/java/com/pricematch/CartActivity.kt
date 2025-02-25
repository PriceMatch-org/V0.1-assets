package com.pricematch

import android.content.Intent
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

        // Check if ALL items have prices for Instamart, Blinkit, or Zepto
        val allHaveInstamart = cartItems.all { it.instamartPrice != null && it.instamartPrice != 0 }
        val allHaveBlinkit = cartItems.all { it.blinkitPrice != null && it.blinkitPrice != 0 }
        val allHaveZepto = cartItems.all { it.zeptoPrice != null && it.zeptoPrice != 0 }

//        val selectedPlatform = when (bind.radioGroup.checkedRadioButtonId) {
//            R.id.rbInstamart -> "instamart"
//            R.id.rbBlinkit -> "blinkit"
//            R.id.rbZepto -> "zepto"
//            else -> {
//                when {
//                    else -> null // No platform available
//                }
//            }
//        }

        // Set visibility and selectability of radio buttons based on availability
        if (allHaveInstamart) {
            // Only Instamart is available for all items
            bind.rbInstamart.isChecked = true
            bind.rbInstamart.isEnabled = true
            bind.rbBlinkit.isEnabled = false
            bind.rbZepto.isEnabled = false

            // Set text for other platforms
            val selectedPlatform = when (bind.radioGroup.checkedRadioButtonId) {
                R.id.rbInstamart -> "instamart"
                R.id.rbBlinkit -> "blinkit"
                R.id.rbZepto -> "zepto"
                else -> {
                    when {
                        else -> null // No platform available
                    }
                }
            }

            val totalPrice = CartManager.calculateTotalPrice(selectedPlatform)
            bind.rbInstamart.text = "Instamart ₹ ${totalPrice}"
            bind.rbZepto.text = "Not available for all items"
            bind.rbBlinkit.text = "Not available for all items"


        } else if (allHaveBlinkit) {
            // Only Blinkit is available for all items
            bind.rbBlinkit.isChecked = true
            bind.rbBlinkit.isEnabled = true
            bind.rbInstamart.isEnabled = false
            bind.rbZepto.isEnabled = false

            // Set text for other platforms
            val selectedPlatform = when (bind.radioGroup.checkedRadioButtonId) {
                R.id.rbInstamart -> "instamart"
                R.id.rbBlinkit -> "blinkit"
                R.id.rbZepto -> "zepto"
                else -> {
                    when {
                        else -> null // No platform available
                    }
                }
            }

            val totalPrice = CartManager.calculateTotalPrice(selectedPlatform)
            bind.rbInstamart.text = "Not available for all items"
            bind.rbZepto.text = "Not available for all items"
            bind.rbBlinkit.text = "Blinkit ₹ ${totalPrice}"
        } else if (allHaveZepto) {
            // Only Zepto is available for all items
            bind.rbZepto.isChecked = true
            bind.rbZepto.isEnabled = true
            bind.rbInstamart.isEnabled = false
            bind.rbBlinkit.isEnabled = false

            // Set text for other platforms
            val selectedPlatform = when (bind.radioGroup.checkedRadioButtonId) {
                R.id.rbInstamart -> "instamart"
                R.id.rbBlinkit -> "blinkit"
                R.id.rbZepto -> "zepto"
                else -> {
                    when {
                        else -> null // No platform available
                    }
                }
            }

            val totalPrice = CartManager.calculateTotalPrice(selectedPlatform)
            bind.rbInstamart.text = "Not available for all items"
            bind.rbZepto.text = "Zepto ₹ ${totalPrice}"
            bind.rbBlinkit.text = "Not available for all items"
        } else {
            // Mixed availability: Enable all available platforms
//            bind.rbInstamart.isEnabled = cartItems.any { it.instamartPrice != null && it.instamartPrice != 0 }
//            bind.rbBlinkit.isEnabled = cartItems.any { it.blinkitPrice != null && it.blinkitPrice != 0 }
//            bind.rbZepto.isEnabled = cartItems.any { it.zeptoPrice != null && it.zeptoPrice != 0 }
//
//            // Reset text for all radio buttons
//            bind.rbInstamart.text = "Instamart"
//            bind.rbBlinkit.text = "Blinkit"
//            bind.rbZepto.text = "Zepto"

            bind.rbInstamart.text = "Not available for all items"
            bind.rbZepto.text = "Not available for all items"
            bind.rbBlinkit.text = "Not available for all items"

            // Set default selection if any platform is available
//            when {
//                bind.rbInstamart.isEnabled -> bind.rbInstamart.isChecked = true
//                bind.rbBlinkit.isEnabled -> bind.rbBlinkit.isChecked = true
//                bind.rbZepto.isEnabled -> bind.rbZepto.isChecked = true
//                else -> bind.radioGroup.clearCheck() // Clear selection if no platform is available
//            }
        }
    }

    fun updateTotalPrice() {
        val selectedPlatform = when (bind.radioGroup.checkedRadioButtonId) {
            R.id.rbInstamart -> "instamart"
            R.id.rbBlinkit -> "blinkit"
            R.id.rbZepto -> "zepto"
            else -> {
                when {
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