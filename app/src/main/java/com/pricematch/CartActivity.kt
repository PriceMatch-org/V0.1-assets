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
        val allHaveInstamart = cartItems.all { it.instamartPrice != null && it.instamartPrice != 0 }
        val allHaveBlinkit = cartItems.all { it.blinkitPrice != null && it.blinkitPrice != 0 }
        val allHaveZepto = cartItems.all { it.zeptoPrice != null && it.zeptoPrice != 0 }
        if(allHaveInstamart || allHaveZepto || allHaveBlinkit){
            if (allHaveInstamart) {
                bind.rbInstamart.isChecked = true
                bind.rbInstamart.isEnabled = true
                val selectedPlatform = when (bind.radioGroup.checkedRadioButtonId) {
                    R.id.rbInstamart -> "instamart"
                    R.id.rbBlinkit -> "blinkit"
                    R.id.rbZepto -> "zepto"
                    else -> {
                        when {
                            else -> null
                        }
                    }
                }
                val totalPrice = CartManager.calculateTotalPrice(selectedPlatform)
                bind.rbInstamart.text = "Instamart (₹ ${totalPrice})"
            }
            else {
                bind.rbInstamart.text = "Not available for all items"
            }
            if (allHaveBlinkit) {
                bind.rbBlinkit.isChecked = true
                bind.rbBlinkit.isEnabled = true
                val selectedPlatform = when (bind.radioGroup.checkedRadioButtonId) {
                    R.id.rbInstamart -> "instamart"
                    R.id.rbBlinkit -> "blinkit"
                    R.id.rbZepto -> "zepto"
                    else -> {
                        when {
                            else -> null
                        }
                    }
                }
                val totalPrice = CartManager.calculateTotalPrice(selectedPlatform)
                bind.rbBlinkit.text = "Blinkit (₹ ${totalPrice})"
            }
            else {
                bind.rbBlinkit.text = "Not available for all items"
            }
            if (allHaveZepto) {
                bind.rbZepto.isChecked = true
                bind.rbZepto.isEnabled = true
                val selectedPlatform = when (bind.radioGroup.checkedRadioButtonId) {
                    R.id.rbInstamart -> "instamart"
                    R.id.rbBlinkit -> "blinkit"
                    R.id.rbZepto -> "zepto"
                    else -> {
                        when {
                            else -> null
                        }
                    }
                }
                val totalPrice = CartManager.calculateTotalPrice(selectedPlatform)
                bind.rbZepto.text = "Zepto (₹ ${totalPrice})"
            }
            else {
                bind.rbZepto.text = "Not available for all items"
            }
        }
        else {
            bind.rbInstamart.text = "Not available for all items"
            bind.rbZepto.text = "Not available for all items"
            bind.rbBlinkit.text = "Not available for all items"
        }
    }

    fun updateTotalPrice() {
        val selectedPlatform = when (bind.radioGroup.checkedRadioButtonId) {
            R.id.rbInstamart -> "instamart"
            R.id.rbBlinkit -> "blinkit"
            R.id.rbZepto -> "zepto"
            else -> {
                when {
                    else -> null
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