package com.pricematch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.pricematch.adapter.CategoryAdapter
import com.pricematch.databinding.ActivityFoodDashboardBinding
import com.pricematch.viewmodel.ProductViewModel

class FoodDashboardActivity : AppCompatActivity() {
    private lateinit var bind: ActivityFoodDashboardBinding

    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityFoodDashboardBinding.inflate(layoutInflater)
        setContentView(bind.root)

        try {
            // Show ProgressBar when API call starts
            bind.progressBar2.visibility = android.view.View.VISIBLE

            // Fetch products from the ViewModel
            productViewModel.fetchProducts()

            // Observe the category list LiveData
            productViewModel.categoryList.observe(this) { categoryList ->
                // Hide ProgressBar when data is received
                bind.progressBar2.visibility = android.view.View.GONE

                // Set up RecyclerView
                bind.recyclerView.layoutManager = LinearLayoutManager(this)
                bind.recyclerView.adapter = CategoryAdapter(categoryList)
            }

            // Observe error messages
            productViewModel.errorMessage.observe(this) { errorMessage ->
                // Hide ProgressBar if an error occurs
                bind.progressBar2.visibility = android.view.View.GONE
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }

            // Search Bar Functionality
            bind.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Implement search functionality here if needed
                    return true
                }
            })

            // Back button click listener
            bind.btnBack.setOnClickListener {
                startActivity(Intent(this, MainDashboardActivity::class.java))
                finish()
            }

            // Cart button click listener
            bind.idCartFactor.setOnClickListener {
                startActivity(Intent(this, CartActivity::class.java))
            }

        } catch (ex: Exception) {
            // Hide ProgressBar if an exception occurs
            bind.progressBar2.visibility = android.view.View.GONE
            Log.e("FoodDashboardActivity", ex.message.toString())
        }
    }
}